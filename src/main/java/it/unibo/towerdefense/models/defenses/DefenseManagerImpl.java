package it.unibo.towerdefense.models.defenses;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.towerdefense.commons.dtos.DefenseDescription;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.graphics.GameRenderer;
import it.unibo.towerdefense.controllers.mediator.ControllerMediator;
import it.unibo.towerdefense.models.defenses.costants.DefenseFormulas;
import it.unibo.towerdefense.models.defenses.costants.DefenseMapFilePaths;

/**Implementation of DefenseController.*/
public class DefenseManagerImpl implements DefenseManager {

    /**Defense builder.*/
    private DefenseFactory factory = new DefenseFactoryImpl();
    /**All current existing defenses with their respective cooldown.*/
    private List<Pair<Defense, Integer>> defenses;
    /**for getting end of map and entities.*/
    private ControllerMediator master;

    /**Constructor that gives this controller access to other necessary controller methods.
    *
    */
    public DefenseManagerImpl(final ControllerMediator master) {
        this();
        this.master = master;
    }

    /**A constructor that recovers defense state from a json file.
     * @param master the mediator.
     * @param jsonString the json content.
    */
    public DefenseManagerImpl(final ControllerMediator master, final String jsonString) {
        this(master);
        JSONArray serializedDefenses = new JSONArray(jsonString);
        for(Object def: serializedDefenses) {
            this.defenses.add(new ImmutablePair<Defense,Integer>
            (Defense.fromJson(def.toString()), 0)
            );
        }
    }

    /**Empty default constructor.*/
    public DefenseManagerImpl () {
        this.defenses = new LinkedList<>();
    }

    /**finds a defense based on its position.
     * @return a defense if there is something on given position,a empty Optional otherwise.
     * @param pos the position to check.
    */
    private Optional<Pair<Integer, Defense>> find(final LogicalPosition pos) {
        for (int i = 0; i < defenses.size(); i++) {
            if (defenses.get(i).getKey().getPosition() == pos) {
                return Optional.of(new ImmutablePair<>(i, defenses.get(i).getKey()));
            }
        }
        return Optional.empty();
    }

    /**
     * gives a defenseDescription for a given defense.
     * @return the defenseDescription of
     * @param def the defense to get description for.
     */
    private DefenseDescription getDescriptionFrom(final Defense def) {
        return new DefenseDescription(def.getType().toString() + " lv. " + def.getLevel(),
         def.getType().toString(),
         def.getBuildingCost(),
         def.getSellingValue(),
         def.getType());
    }

    /**gets the models of buildable defenses for given defense
     * THIS ARE NOT TO BE PASSED TO THE VIEW.
     * @return a list of defenses.
     * @param buildPosition the current model to check.
    */
    private List<Defense> getModelsOfBuildables(final LogicalPosition buildPosition) throws IOException {
        Optional<Pair<Integer, Defense>> currentDef = find(buildPosition);
        if (currentDef.isEmpty()) {
            return List.of(
                factory.levelOneDefense(DefenseMapFilePaths.ARCHER_TOWER_LV1, buildPosition, Optional.empty()),
                factory.levelOneDefense(DefenseMapFilePaths.BOMB_TOWER_LV1, buildPosition, Optional.empty()),
                factory.levelOneDefense(DefenseMapFilePaths.WIZARD_TOWER_LV1, buildPosition, Optional.empty()),
                factory.levelOneDefenseWithCustomPosition(DefenseMapFilePaths.THUNDER_INVOKER_LV1,
                buildPosition, master.getMapController().getEndPosition(), Optional.empty())
            );
        }
        return currentDef.get().getValue().getPossibleUpgrades().stream().toList();
    }

    /**updates momentum on every defense.*/
    private void updateMomentum() {
        for (Pair<Defense, Integer> def : this.defenses) {
            int speed = def.getKey().getAttackSpeed();
            def.setValue(Math.max(def.getValue() + speed, DefenseFormulas.MOMENTUM_REQUIRED));
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void update() {
        updateMomentum();
        Map<Integer,Integer> damage = attackEnemies(master.getEnemyController().getEnemies());
        if (damage.size() != 0) {
            master.getEnemyController().hurtEnemies(damage);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void render(final GameRenderer renderer) {

    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void buildDefense(final int choice, final LogicalPosition position) throws IOException {
        List<Defense> buildables = getModelsOfBuildables(position);
        Optional<Pair<Integer, Defense>> upgradable = find(position);

        if (upgradable.isEmpty()) {
            defenses.add(new ImmutablePair<>(buildables.get(choice), 0));
        } else {
            /**@TODO: create paths for upgrades.*/
            defenses.set(upgradable.get().getKey(),
            new ImmutablePair<>(factory.upgrade(buildables.get(choice), choice,
            Optional.of(DefenseMapFilePaths.pathFromType(buildables.get(choice).getType()))), 0));
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int disassembleDefense(final LogicalPosition position) {
        Optional<Pair<Integer, Defense>> toDelete = find(position);
        int returnValue = toDelete.get().getValue().getSellingValue();
        defenses.remove(toDelete.get().getKey());
        return returnValue;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<DefenseDescription> getBuildables(final LogicalPosition position) throws IOException {
        return getModelsOfBuildables(position).stream().map(x -> getDescriptionFrom(x)).toList();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Map<Integer, Integer> attackEnemies(final List<Pair<LogicalPosition, Integer>> availableTargets) {
        Map<Integer, Integer> result = new HashMap<>();
        for (Pair<Defense, Integer> def : this.defenses) {
            /**execute only if momentum is reached.*/
            if (def.getValue() >= DefenseFormulas.MOMENTUM_REQUIRED) {
                Map<Integer, Integer> attackResult = def.getKey().getStrategy()
                .execute(availableTargets, def.getKey().getDamage());

                /**merge map with result.*/
                if (attackResult.size() > 0) {
                    def.setValue(0); /**reset only if there was an actual action.*/
                    attackResult.entrySet().stream().forEach(x ->
                        result.merge(x.getKey(), x.getValue(), Integer::sum)
                    );
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        JSONArray result = new JSONArray();
        for (Pair<Defense, Integer> def : this.defenses) {
            result.put(new JSONObject(def.getKey().toJSON()));
        }
        return result.toString();
    }

    @Override
    public DefenseDescription getDescriptionFor(LogicalPosition at) {
        Optional<Pair<Integer,Defense>> def = find(at);
        if(def.isPresent()) {
            return getDescriptionFrom(def.get().getValue());
        }
        return DefenseDescription.nonBuiltDefense();
    }
}
