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

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.model.defenses.costants.DefenseFormulas;
import it.unibo.towerdefense.model.defenses.costants.DefenseMapFilePaths;
import it.unibo.towerdefense.model.enemies.Enemy;

/**Implementation of DefenseController.*/
public class DefenseManagerImpl implements DefenseManager {

    /**Defense builder.*/
    private final DefenseFactory factory = new DefenseFactoryImpl();
    /**All current existing defenses with their respective cooldown.*/
    private List<Pair<Defense, Integer>> defenses;
    /**for getting end of map and entities.*/
    private ModelManager manager;
    /**gets the attacks that occured in one loop.*/
    private final Map<Defense, List<LogicalPosition>> attacksOnLoop = new HashMap<>();
    /**gets wich defense is being focused.*/
    private Optional<Defense> focusedDef = Optional.empty();

    /**A constructor that recovers defense state from a json file.
     * @param jsonString the json content.
    */
    public DefenseManagerImpl(final String jsonString) {
        this();
        final JSONArray serializedDefenses = new JSONArray(jsonString);
        for (final Object def: serializedDefenses) {
            this.defenses.add(MutablePair.of(
                factory.defenseFromJsonSave(def.toString()), 0)
            );
        }
    }

    /**Empty default constructor.*/
    public DefenseManagerImpl() {
        this.defenses = new LinkedList<>();
    }

    /**finds a defense based on its position.
     * @return a defense if there is something on given position,a empty Optional otherwise.
     * @param pos the position to check.
    */
    private Optional<MutablePair<Integer, Defense>> find(final LogicalPosition pos) {
        for (int i = 0; i < defenses.size(); i++) {
            if (defenses.get(i).getKey().getPosition().get().equals(pos)) {
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
         def.getDamage(),
         def.getAttackSpeed(),
         def.getBuildingCost(),
         def.getSellingValue(),
         def.getLevel(),
         def.getRange(),
         focusedDef.isPresent() && focusedDef.get().equals(def),
         def.getType(),
         def.getPosition(), 
         List.copyOf(attacksOnLoop.computeIfAbsent(def, x -> List.of())));
    }

    /**gets the models of buildable defenses for given defense
     * THIS ARE NOT TO BE PASSED TO THE VIEW.
     * @return a list of defenses.
     * @param buildPosition the current model to check.
    */
    private List<Defense> getModelsOfBuildables(final LogicalPosition buildPosition) throws IOException {
       final Optional<MutablePair<Integer, Defense>> currentDef = find(buildPosition);
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
        for (final Pair<Defense, Integer> def : this.defenses) {
            final int speed = def.getKey().getAttackSpeed();
            def.setValue(Math.min(def.getValue() + speed, DefenseFormulas.MOMENTUM_REQUIRED));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        updateMomentum();
        final Set<? extends Enemy> enemies = manager.getEnemies().getEnemies();
        final List<? extends Enemy> enumeratedEnemies = enemies.stream().toList();
        final Map<Integer, Integer> damage = attackEnemies(enumeratedEnemies);
        if (!damage.isEmpty()) {
            for (final Map.Entry<Integer, Integer> dam: damage.entrySet()) {
                enumeratedEnemies.get(dam.getKey()).hurt(dam.getValue());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Defense> getDefenseAt(final LogicalPosition at) {
        final Optional<MutablePair<Integer, Defense>> def = find(at);
        return def.isEmpty() ? Optional.empty() : Optional.of(def.get().getValue());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void buildDefense(final int choice, final LogicalPosition position) throws IOException {
        final List<Defense> buildables = getModelsOfBuildables(position);
        final Optional<MutablePair<Integer, Defense>> upgradable = find(position);

        if (upgradable.isEmpty()) {
            defenses.add(MutablePair.of(buildables.get(choice), 0));
        } else {
            defenses.set(upgradable.get().getKey(),
            MutablePair.of(new DefenseImpl(factory.upgrade(upgradable.get().getValue(), choice,
            Optional.of(DefenseMapFilePaths.upgradePathFromType(buildables.get(choice).getType())))), 0));
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int disassembleDefense(final LogicalPosition position) {
        final Optional<MutablePair<Integer, Defense>> toDelete = find(position);
        final int returnValue = toDelete.get().getValue().getSellingValue();
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

    /**Update method to hurt enemies.
     * @param availableTargets the enemies to choose from.
     * @return a map of index : damage attacks to execute.
    */
    private Map<Integer, Integer> attackEnemies(final List<? extends Enemy> availableTargets) {
        final Map<Integer, Integer> result = new HashMap<>();
        for (final Pair<Defense, Integer> def:defenses) {
            /**execute only if momentum is reached.*/
            if (def.getValue() >= DefenseFormulas.MOMENTUM_REQUIRED) {
                final Map<Integer, Integer> attackResult = def.getKey().getStrategy()
                .execute(availableTargets, def.getKey().getDamage());
                /**merge map with result.*/
                if (!attackResult.isEmpty()) {
                    def.setValue(0); /**reset only if there was an actual action.*/
                    attackResult.entrySet().stream().forEach(x ->
                        result.merge(x.getKey(), x.getValue(), Integer::sum)
                    );
                    /**add attacks */
                    final List<LogicalPosition> hitEnemies = new LinkedList<>();
                    attackResult.entrySet().forEach(x -> hitEnemies.add(availableTargets.get(x.getKey()).getPosition()));
                    attacksOnLoop.put(def.getKey(), hitEnemies);
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
        final JSONArray result = new JSONArray();
        for (final Pair<Defense, Integer> def : this.defenses) {
            final Defense d = def.getLeft();
            result.put(new JSONObject(d.toJSON()));
        }
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(final ModelManager mm) {
        this.manager = mm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DefenseDescription> getDefenses() {
        final List<DefenseDescription> descs = new LinkedList<>();
        for (int i = 0; i < this.defenses.size(); i++) {
            descs.add(getDescriptionFrom(this.defenses.get(i).getKey()));
        }
        attacksOnLoop.clear();
        return descs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedDefense(final LogicalPosition pos, final boolean toSelect) {
        final Optional<MutablePair<Integer, Defense>> def = find(pos);
        if (!def.isEmpty()) {
            this.focusedDef = toSelect ? Optional.of(def.get().getRight()) : Optional.empty();
        } else {
            this.focusedDef = Optional.empty();
        }
    }
}
