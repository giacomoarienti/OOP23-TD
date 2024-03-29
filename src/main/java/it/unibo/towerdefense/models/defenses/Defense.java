package it.unibo.towerdefense.models.defenses;

import java.util.Set;

/**
 * The actual physical structure that attacks and defeats the enemies.
 */
public interface Defense {

    /**
     * @return the value used calculate the damage to deal to enemies
     */
    int getDamage();

    /**
     * @return the frequency with wich the defense executes its attack
     */
    int getAttackSpeed();

    /**
     * @return the cost for building the defense,it might be built on a empty cell or on an already existing defense as an upgrade
     */
    int getBuildingCost();

    /**
     * @return the amount of money recovered from destroying the defense
     */
    int getSellingValue();

    /**
    * @return the value used to calculate wich enemies to target in the strategy.
    */
    int getRange();

    /** 
     * @return the strategy used for selecting and damaging enemies
     */
    EnemyChoiceStrategy getStrategy();

    /**
     * @return the available defenses that can be built as upgrade fo the current defense
     */
    Set<Defense> getPossibleUpgrades();
}
