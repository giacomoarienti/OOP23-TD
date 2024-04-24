package it.unibo.towerdefense.models.defenses;

import java.util.Set;

import it.unibo.towerdefense.commons.LogicalPosition;

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
     * WARNING! this will give a placeholder as strategy.
     * @param filePath the path of the json file.
     * @param upgrades the available updates.
     * @TODO implement constructor.
     */
    public DefenseImpl(final String filePath, final Set<Defense> upgrades) {

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
}
