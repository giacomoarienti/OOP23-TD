package it.unibo.towerdefense.model.defenses;

import java.util.Set;
import java.io.IOException;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Optional;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.utils.file.FileUtils;
import it.unibo.towerdefense.model.defenses.costants.DefenseMapKeys;

/**
 * Implementation of the defense interface.
 */

public class DefenseImpl implements Defense {

    //Variables , the purpose of every variable is explained by their get methods in the interface file.
    private int damage;
    private int attackSpeed;
    private int buildingCost;
    private int sellingValue;
    private int level;
    private int range;
    private DefenseType type;
    private EnemyChoiceStrategy strategy;
    private Set<Defense> upgrades;
    private Optional<LogicalPosition> position;

    /**A constructor that copies another defense.
     *@param copy the defense to copy.
    */
    public DefenseImpl(final Defense copy) {
        this.type = copy.getType();
        this.level = copy.getLevel();
        this.damage = copy.getDamage();
        this.range = copy.getRange();
        this.attackSpeed = copy.getAttackSpeed();
        this.buildingCost = copy.getBuildingCost();
        this.sellingValue = copy.getSellingValue();
        this.strategy = copy.getStrategy();
        this.upgrades = copy.getPossibleUpgrades();
        this.position = copy.getPosition();
    }
    /**
     * This constructor builds the defense from scratch,passing all the required fields from the interface.
     * @param damage
     * @param type
     * @param range
     * @param attackSpeed
     * @param cost
     * @param sellValue
     * @param strat
     * @param upgrades
     * @param level
     * @param position
     */
    public DefenseImpl(final DefenseType type, final int level, final int damage,
    final int range, final int attackSpeed, final int cost, final int sellValue,
    final Optional<LogicalPosition> position, final EnemyChoiceStrategy strat, final Set<Defense> upgrades) {
        this.type = type;
        this.level = level;
        this.damage = damage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.buildingCost = cost;
        this.sellingValue = sellValue;
        this.strategy = strat;
        this.upgrades = new HashSet<>(upgrades);
        this.position = position;
    }

    /**
     * This constructor builds all the elementary stats from a json file.
     * Non available fields will be replaced with placeholders.
     * WARNING! this will give a placeholder as strategy.
     * @param filePath the path of the json file.
     * @throws IOexception if it fails to read the file.
     */
    public DefenseImpl(final String filePath) throws IOException {
        this(fromJson(FileUtils.readResource(filePath)));
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public DefenseType getType() {
        return type;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int getRange() {
        return range;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int getDamage() {
        return damage;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int getAttackSpeed() {
        return attackSpeed;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int getBuildingCost() {
        return buildingCost;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int getSellingValue() {
        return sellingValue;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public EnemyChoiceStrategy getStrategy() {
        return strategy;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Set<Defense> getPossibleUpgrades() {
        return new HashSet<>(upgrades);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<LogicalPosition> getPosition() {
        return this.position;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void setStrategy(final EnemyChoiceStrategy strat) {
        this.strategy = strat;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void setPosition(final LogicalPosition newPos) {
        this.position = Optional.of(newPos);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void addUpgrades(final Set<Defense> newUpgrades) {
        this.upgrades.addAll(newUpgrades);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String toJSON() {
        final JSONObject parser = new JSONObject();
        /**Add basic data.*/
        parser.put(DefenseMapKeys.LEVEL, this.level);
        parser.put(DefenseMapKeys.DAMAGE, this.damage);
        parser.put(DefenseMapKeys.SPEED, this.attackSpeed);
        parser.put(DefenseMapKeys.BUILDING_COST, this.buildingCost);
        parser.put(DefenseMapKeys.SELLING_COST, this.sellingValue);
        /**upgrades won't have a position.*/
        if (this.position.isPresent()) {
            parser.put(DefenseMapKeys.POSITION, this.position.get().toJSON());
        }
        parser.put(DefenseMapKeys.RANGE, this.range);
        parser.put(DefenseMapKeys.TYPE, this.type);

        /**Handle updates.*/
        final JSONArray upgrades = new JSONArray();
        this.upgrades.forEach(u -> upgrades.put(u.toJSON()));
        parser.put(DefenseMapKeys.UPGRADES, upgrades);
        return parser.toString();
    }

    /**
     * @param jsonData the json string.
     * @return a defense from a json string, strategy is to be set.
     */
    public static Defense fromJson(final String jsonData) {
        final JSONObject json = new JSONObject(jsonData);
        /**Obtain upgrades.*/
        final Set<Defense> upgrades = new HashSet<>();

        /**add upgrades if they exist.*/
        if (json.has(DefenseMapKeys.UPGRADES)) {
                json.getJSONArray(DefenseMapKeys.UPGRADES).forEach(x ->
                upgrades.add(fromJson(x.toString()))
            );
        }

        /**Obtain  position,if it exists*/
        final Optional<LogicalPosition> position =
        json.has(DefenseMapKeys.POSITION) ? Optional.of(LogicalPosition.fromJson(json.getString(DefenseMapKeys.POSITION)))
        : Optional.empty();

        return new DefenseImpl(
        DefenseType.valueOf(json.get(DefenseMapKeys.TYPE).toString()),
        json.getInt(DefenseMapKeys.LEVEL),
        json.getInt(DefenseMapKeys.DAMAGE),
        json.getInt(DefenseMapKeys.RANGE),
        json.getInt(DefenseMapKeys.SPEED),
        json.getInt(DefenseMapKeys.BUILDING_COST),
        json.getInt(DefenseMapKeys.SELLING_COST),
        position,
        null,
        upgrades);
    }
}
