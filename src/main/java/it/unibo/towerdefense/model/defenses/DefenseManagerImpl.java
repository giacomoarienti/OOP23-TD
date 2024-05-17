package it.unibo.towerdefense.model.defenses;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.model.defenses.costants.DefenseFormulas;
import it.unibo.towerdefense.model.defenses.costants.DefenseMapFilePaths;
import it.unibo.towerdefense.model.enemies.Enemy;

/**Implementation of DefenseController.*/
public class DefenseManagerImpl implements DefenseManager {

    /**Defense builder.*/
    private DefenseFactory factory = new DefenseFactoryImpl();
    /**All current existing defenses with their respective cooldown.*/
    private List<Pair<Defense, Integer>> defenses;
    /**for getting end of map and entities.*/
    private ModelManager manager;

    /**A constructor that recovers defense state from a json file.
     * @param jsonString the json content.
    */
    public DefenseManagerImpl(final String jsonString) {
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
         def.getLevel(),
         def.getType(),
         def.getPosition());
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
                factory.levelOneDefense(DefenseMapFilePaths.THUNDER_INVOKER_LV1, buildPosition, Optional.empty())
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

    /**Update method to hurt enemies.*/
    public void update() {
        updateMomentum();
        Set<? extends Enemy> enemies = manager.getEnemies().getEnemies();
        List<? extends Enemy> enumeratedEnemies = enemies.stream().toList();
        Map<Integer,Integer> damage = attackEnemies(enumeratedEnemies);
        if (damage.size() != 0) {
            for(Map.Entry<Integer,Integer> dam: damage.entrySet()) {
                enumeratedEnemies.get(dam.getKey()).hurt(dam.getValue());
            }
        }
    }

    @Override
    public Optional<Defense> getDefenseAt(LogicalPosition at) {
        Optional<Pair<Integer, Defense>> def = find(at);
        return def.isEmpty() ? Optional.empty() : Optional.of(def.get().getValue());
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
    public List<Defense> getBuildables(final LogicalPosition position) throws IOException {
        return getModelsOfBuildables(position).stream().toList();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Map<Integer, Integer> attackEnemies(final List<? extends Enemy> availableTargets) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(ModelManager mm) {
        this.manager = mm;
    }

    @Override
    public List<DefenseDescription> getDefenses() {
        List<DefenseDescription> descs = new LinkedList<>();
        for(int i = 0; i < this.defenses.size(); i++) {
            descs.add(getDescriptionFrom(this.defenses.get(i).getKey()));
        }
        return descs;
    }
}
