package it.unibo.towerdefense.model.defenses;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    /**gets the attacks that occured in one loop.*/
    private Map<Defense,List<LogicalPosition>> attacksOnLoop = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(DefenseManagerImpl.class);
    /**A constructor that recovers defense state from a json file.
     * @param jsonString the json content.
    */
    public DefenseManagerImpl(final String jsonString) {
        JSONArray serializedDefenses = new JSONArray(jsonString);
        for(Object def: serializedDefenses) {
            this.defenses.add(MutablePair.of
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
    private Optional<MutablePair<Integer, Defense>> find(final LogicalPosition pos) {
        for (int i = 0; i < defenses.size(); i++) {
            if (defenses.get(i).getKey().getPosition().equals(pos)) {
                return Optional.of(MutablePair.of(i, defenses.get(i).getKey()));
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
        return new DefenseDescription(
         def.getBuildingCost(),
         def.getSellingValue(),
         def.getLevel(),
         def.getType(),
         def.getPosition(),
         attacksOnLoop.computeIfAbsent(def, x-> List.of()));
    }

    /**gets the models of buildable defenses for given defense
     * THIS ARE NOT TO BE PASSED TO THE VIEW.
     * @return a list of defenses.
     * @param buildPosition the current model to check.
    */
    private List<Defense> getModelsOfBuildables(final LogicalPosition buildPosition) throws IOException {
        Optional<MutablePair<Integer, Defense>> currentDef = find(buildPosition);
        if (currentDef.isEmpty()) {
            return List.of(
                factory.levelOneDefense(DefenseMapFilePaths.ARCHER_TOWER_LV1, buildPosition,
                Optional.of(DefenseMapFilePaths.ARCHER_TOWER_UPGRADES)),
                factory.levelOneDefense(DefenseMapFilePaths.BOMB_TOWER_LV1, buildPosition,
                Optional.of(DefenseMapFilePaths.BOMB_TOWER_UPGRADES)),
                factory.levelOneDefense(DefenseMapFilePaths.WIZARD_TOWER_LV1, buildPosition,
                Optional.of(DefenseMapFilePaths.WIZARD_TOWER_UPGRADES)),
                factory.levelOneDefense(DefenseMapFilePaths.THUNDER_INVOKER_LV1, buildPosition,
                Optional.of(DefenseMapFilePaths.THUNDER_INVOKER_UPGRADES))
            );
        }
        return currentDef.get().getValue().getPossibleUpgrades().stream().toList();
    }

    /**updates momentum on every defense.*/
    private void updateMomentum() {
        for (Pair<Defense, Integer> def : this.defenses) {
            int speed = def.getKey().getAttackSpeed();
            def.setValue(Math.min(def.getValue() + speed, DefenseFormulas.MOMENTUM_REQUIRED));
        }
    }

    @Override
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
        Optional<MutablePair<Integer, Defense>> def = find(at);
        return def.isEmpty() ? Optional.empty() : Optional.of(def.get().getValue());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void buildDefense(final int choice, final LogicalPosition position) throws IOException {
        List<Defense> buildables = getModelsOfBuildables(position);
        Optional<MutablePair<Integer, Defense>> upgradable = find(position);

        if (upgradable.isEmpty()) {
            defenses.add(MutablePair.of(buildables.get(choice), 0));
        } else {
            defenses.set(upgradable.get().getKey(),
            MutablePair.of(factory.upgrade(upgradable.get().getValue(), choice,
            Optional.of(DefenseMapFilePaths.pathFromType(buildables.get(choice).getType()))), 0));
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int disassembleDefense(final LogicalPosition position) {
        Optional<MutablePair<Integer, Defense>> toDelete = find(position);
        int returnValue = toDelete.get().getValue().getSellingValue();
        defenses.remove(toDelete.get().getKey().intValue());
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
    public Map<Integer, Integer> attackEnemies(final List<? extends Enemy> availableTargets) {
        Map<Integer, Integer> result = new HashMap<>();
        for (Pair<Defense,Integer> def:defenses) {
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
                    /**add attacks */
                    List<LogicalPosition> hitEnemies = new LinkedList<>();
                    attackResult.entrySet().forEach(x->hitEnemies.add(availableTargets.get(x.getKey()).getPosition()));
                    attacksOnLoop.put(def.getKey(),hitEnemies);
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
    /**
     * {@inheritDoc}
     */
    public List<DefenseDescription> getDefenses() {
        List<DefenseDescription> descs = new LinkedList<>();
        for(int i = 0; i < this.defenses.size(); i++) {
            descs.add(getDescriptionFrom(this.defenses.get(i).getKey()));
        }
        attacksOnLoop.clear();
        return descs;
    }
}
