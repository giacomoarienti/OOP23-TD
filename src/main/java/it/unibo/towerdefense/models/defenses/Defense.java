package it.unibo.towerdefense.models.defenses;

import java.util.Set;

/*The actual physical structure that attacks and defeats the enemies*/
public interface Defense {

    int getDamage();

    int getAttackSpeed();

    int getBuildingCost();

    int getSellingValue();

    /*WARNING: depending on the choice strategy,the range might be use to include or exclude targets! */
    int getRange();

    EnemyChoiceStrategy getStrategy();

    /*the possible upgrades for a defense,the pairs are formed by the new Defense and its cost,an empty set will simply mean that no upgrades are available */
    Set<Defense> getPossibleUpgrades();
}
