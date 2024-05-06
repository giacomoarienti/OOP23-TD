package it.unibo.towerdefense.models.defenses;

import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * A factory for the strategies used by the defenses for selection of enemies and damage calculation.
 */
public interface EnemyChoiceStrategyFactory {
    /**
     * @return a strategy that selects the (at most) maxTarget closest enemies in a given range around a position.
     * @param maxTargets the max possible number of enemies that can be selected by this strategy in one execution (at least 1).
     * @param range the maximum allowed distance from the defense to be an eligible target.
     * @param position the position from wich the tower checks the range.
     * All enemies receive the base damage of the tower
     */
    EnemyChoiceStrategy closestTargets(int maxTargets, int range, LogicalPosition position);

    /**
     * @return a strategy that selects the closest enemy within a range, then deals the base damage of the defense to that enemy
     * and to all the enemies within a range from the starting target.
     * @param damageRange all the enemies with a distance equal or lower than this from the starting enemy target will be hit
     * @param range the max distance allowed from the defense to be eligible as a first target.
     * @param position the position from wich the tower checks the range.
     */
    EnemyChoiceStrategy closestTargetWithAreaDamage(int damageRange, int range, LogicalPosition position);

    /**
     * @return a strategy that selects the closest enemy to a custom point, then deals the base damage
     * of the defense to that enemy.
     * @param range the range to check.
     * @param customPoint the custom point for distance check.
     * @param position entities inside the range relative to this position cannot be selected.
     */
    EnemyChoiceStrategy closestToCustomPointNotInRange(int range, LogicalPosition customPoint, LogicalPosition position);
}
