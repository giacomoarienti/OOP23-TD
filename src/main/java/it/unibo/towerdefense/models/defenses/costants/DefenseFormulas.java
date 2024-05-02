package it.unibo.towerdefense.models.defenses.costants;

import it.unibo.towerdefense.models.defenses.Defense;

/**A collection of formulas to calculate some parameters for the defense strategies.*/
public final class DefenseFormulas {
    /**formula for how many targets a wizard tower can hit.
     * @param tower the defense used for the formula.
     * the formula is towerLevel +1.
    */
    final static int WIZARD_TOWER_TARGET_FORMULA(Defense tower) {
        return tower.getLevel() + 1;
    }

    /**formula for the area covered by a bomb tower attack.
     * @param tower the defense used for the formula.
     * the formula is range/2 -1 + level.
    */
    final static int BOMB_TOWER_DAMAGEAREA_FORMULA(Defense tower) {
        return tower.getRange()/2 + tower.getLevel() -1;
    }
}
