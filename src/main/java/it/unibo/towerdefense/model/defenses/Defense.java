package it.unibo.towerdefense.model.defenses;

import java.util.Set;

import it.unibo.towerdefense.commons.api.JsonSerializable;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**
 * The actual physical structure that attacks and defeats the enemies.
 */
public interface Defense extends JsonSerializable {

    /**
     * @return the level of the defense.
     */
    int getLevel();

    /**@return the type of the defense.*/
    DefenseType getType();

    /**
     * @return the value used calculate the damage to deal to enemies.
     */
    int getDamage();

    /**
     * @return the range used by the defense strategy.
     */
    int getRange();

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
     * @return the strategy used for selecting and damaging enemies
     */
    EnemyChoiceStrategy getStrategy();

    /**
     * @return the available defenses that can be built as upgrade fo the current defense
     */
    Set<Defense> getPossibleUpgrades();

    /**
     * @return the position of the Defense.
     */
    LogicalPosition getPosition();

    /**Sets the strategy for the defense.
     * @param strat the strategy to set.
    */
    void setStrategy(EnemyChoiceStrategy strat);

    /**
     * Sets the position of the Defense.
     * @param newPos the position to set.
     */
    void setPosition(LogicalPosition newPos);

    /**Adds new upgrades to the defense.
     * @param newUpgrades the upgrades to Add.
    */
    void addUpgrades(Set<Defense> newUpgrades);

    /**Gets from json.
     * @return a defense from the json like string.
     * @param jsonData the json string.
    */
    static Defense fromJson(String jsonData) {
        return DefenseImpl.fromJson(jsonData);
    }
}
