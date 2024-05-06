package it.unibo.towerdefense.controllers.defenses;

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

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.defenses.Defense;
import it.unibo.towerdefense.models.defenses.DefenseFactory;
import it.unibo.towerdefense.models.defenses.DefenseFactoryImpl;
import it.unibo.towerdefense.models.defenses.costants.DefenseFormulas;
import it.unibo.towerdefense.models.defenses.costants.DefenseMapFilePaths;
import it.unibo.towerdefense.views.defenses.DefenseDescription;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**Implementation of DefenseController.*/
public class DefensesControllerImpl implements DefensesController {

    /**Defense builder.*/
    private DefenseFactory factory = new DefenseFactoryImpl();
    /**All current existing defenses with their respective cooldown.*/
    private List<Pair<Defense, Integer>> defenses = new LinkedList<>();
    /**The current custom position to be used by defenses.*/
    private LogicalPosition endOfMap;

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
         def.getBuildingCost());
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
                factory.levelOneDefenseWithCustomPosition(DefenseMapFilePaths.WIZARD_TOWER_LV1,
                buildPosition, endOfMap, Optional.empty())
            );
        }
        return currentDef.get().getValue().getPossibleUpgrades().stream().toList();
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public void update() {
        /**update momentum.*/
        for (Pair<Defense, Integer> def : this.defenses) {
            int speed = def.getKey().getAttackSpeed();
            def.setValue(Math.max(def.getValue() + speed, DefenseFormulas.MOMENTUM_REQUIRED));
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
            new ImmutablePair<>(factory.upgrade(buildables.get(choice), choice, Optional.empty()), 0));
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
}
