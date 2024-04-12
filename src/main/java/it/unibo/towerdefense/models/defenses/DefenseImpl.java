package it.unibo.towerdefense.models.defenses;

import java.util.Set;

/**
 * Implementation of the defense interface.
 */

public class DefenseImpl implements Defense {

    //Variables , the purpose of every variable is explained by their get methods in the interface file.
    private int damage;
    private int attackSpeed;
    private int buildingCost;
    private int sellingValue;
    private EnemyChoiceStrategy strategy;
    private Set<Defense> upgrades;

    /**
     * This constructor builds the defense from scratch,passing all the required fields from the interface.
     * @param damage 
     * @param attackSpeed
     * @param cost
     * @param sellValue
     * @param strat
     * @param upgrades
     */
    public DefenseImpl(final int damage, 
    final int attackSpeed, final int cost, final int sellValue, final EnemyChoiceStrategy strat, final Set<Defense> upgrades) {
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.buildingCost = cost;
        this.sellingValue = sellValue;
        this.strategy = strat;
        this.upgrades = upgrades;
    }

    /**
     * This constructor builds all the elementary stats from a json file.
     * @param filePath the path of the json file.
     * @param strat the choice strategy.
     * @param upgrades the available updates.
     * @TODO implement constructor.
     */
    public DefenseImpl(final String filePath, final EnemyChoiceStrategy strat, final Set<Defense> upgrades) {

    }
    /**
     * @return damage.
     */
    @Override
    public int getDamage() {
        return damage;
    }

    /**
     * @return the attack speed.
     */
    @Override
    public int getAttackSpeed() {
        return attackSpeed;
    }

    /**
     * @return the cost of building.
     */
    @Override
    public int getBuildingCost() {
        return buildingCost;
    }

    /**
     * @return the value for selling.
     */
    @Override
    public int getSellingValue() {
        return sellingValue;
    }

    /**
     * @return the strategy.
     */
    @Override
    public EnemyChoiceStrategy getStrategy() {
        return strategy;
    }

    /**
     * @return the possible upgrades.
     */
    @Override
    public Set<Defense> getPossibleUpgrades() {
        return upgrades;
    }
}
