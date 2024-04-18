package it.unibo.towerdefense.models.defenses;

/**
 * A factory for the strategies used by the defenses for selection of enemies and damage calculation.
 */
public interface EnemyChoiceStrategyFactory {
    /**
     * @return a strategy that selects the (at most) maxTarget closest enemies in a given range.
     * @param maxTargets the max possible number of enemies that can be selected by this strategy in one execution (at least 1).
     * @param range the maximum allowed distance from the defense to be an eligible target.
     * All enemies receive the base damage of the tower
     */
    EnemyChoiceStrategy closestTargets(int maxTargets, int range);

    /**
     * @return a strategy that selects the closest enemy within a range, then deals the base damage of the defense to that enemy 
     * and to all the enemies within a range from the starting target.
     * @param damageRange all the enemies with a distance equal or lower than this from the starting enemy target will be hit
     * @param range the max distance allowed from the defense to be eligible as a first target.
     */
    EnemyChoiceStrategy closestTargetWithAreaDamage(int damageRange, int range);
}
