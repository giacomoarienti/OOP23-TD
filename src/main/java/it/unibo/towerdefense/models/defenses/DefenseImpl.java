package it.unibo.towerdefense.models.defenses;

import java.util.Set;
import java.io.IOException;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.base.Optional;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.defenses.costants.DefenseMapKeys;
import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.utils.file.FileUtils;

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
    private EnemyChoiceStrategy strategy;
    private Set<Defense> upgrades;
    private LogicalPosition position;

    /**A private constructor that copies another defense.
     *@param copy the defense to copy.
    */
    private DefenseImpl(Defense copy) {
        this.damage = copy.getDamage();
        this.level = copy.getLevel();
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
     * @param attackSpeed
     * @param cost
     * @param sellValue
     * @param strat
     * @param upgrades
     * @param level
     * @param position
     */
    public DefenseImpl(final int damage, final int level, final LogicalPosition position,
    final int attackSpeed, final int cost, final int sellValue, final EnemyChoiceStrategy strat, final Set<Defense> upgrades) {
        this.damage = damage;
        this.level = level;
        this.attackSpeed = attackSpeed;
        this.buildingCost = cost;
        this.sellingValue = sellValue;
        this.strategy = strat;
        this.upgrades = upgrades;
        this.position = position;
    }

    /**
     * This constructor builds all the elementary stats from a json file.
     * Non available fields will be replaced with placeholders.
     * WARNING! this will give a placeholder as strategy.
     * @param filePath the path of the json file.
     * @param upgrades the available updates,if the optional is empty it means that the upgrades are already in the file.
     * @param position in case the file does not have a position,this can be used instead.
     * @throws IOException 
     * @throws IOexception if it fails to read the file.
     */
    public DefenseImpl(final String filePath, final Optional<Set<Defense>> upgrades,
    final Optional<LogicalPosition> position) throws IOException {
        this(fromJson(FileUtils.readFile(filePath)));

        /**Giving optional values.*/
        if (upgrades.isPresent()) {
            this.upgrades = upgrades.get();
        }

        if (position.isPresent()) {
            this.position = position.get();
        }
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
        return upgrades;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public LogicalPosition getPosition() {
        return position;
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
        this.position = newPos;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String toJSON() {
        JSONObject parser = new JSONObject();
        /**Add basic data.*/
        parser.put(DefenseMapKeys.LEVEL, this.level);
        parser.put(DefenseMapKeys.DAMAGE, this.damage);
        parser.put(DefenseMapKeys.SPEED, this.attackSpeed);
        parser.put(DefenseMapKeys.BUILDING_COST, this.buildingCost);
        parser.put(DefenseMapKeys.SELLING_COST, this.sellingValue);
        parser.put(DefenseMapKeys.POSITION, this.position.toJSON());

        /**Handle updates.*/
        JSONArray upgrades = new JSONArray();
        this.upgrades.forEach(u -> upgrades.put(u.toJSON()));
        parser.put(DefenseMapKeys.UPGRADES, upgrades);
        return parser.toString();
    }

    public static Defense fromJson(final String jsonData) {
        JSONObject json = new JSONObject(jsonData);
        /**Obtain upgrades.*/
        Set<Defense> upgrades = new HashSet<>();
        json.getJSONArray(DefenseMapKeys.UPGRADES).forEach(x ->
            upgrades.add(fromJson(x.toString()))
        );

        /**Obtain  position,if it exists*/
        Optional<Position> position =
        json.has(DefenseMapKeys.POSITION) ? Optional.of(Position.fromJson(json.getString(DefenseMapKeys.POSITION)))
        : Optional.absent();

        return new DefenseImpl(
        json.getInt(DefenseMapKeys.DAMAGE),
        json.getInt(DefenseMapKeys.LEVEL),
        position.isPresent() ? new LogicalPosition(position.get().getX(), position.get().getY())
        : null,
        json.getInt(DefenseMapKeys.SPEED),
        json.getInt(DefenseMapKeys.BUILDING_COST),
        json.getInt(DefenseMapKeys.SELLING_COST),
        null,
        upgrades);
    }
}
