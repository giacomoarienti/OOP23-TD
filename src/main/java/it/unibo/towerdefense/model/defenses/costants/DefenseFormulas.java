package it.unibo.towerdefense.model.defenses.costants;

import it.unibo.towerdefense.model.defenses.Defense;

/**A collection of formulas to calculate some parameters for the defense strategies.*/
public final class DefenseFormulas {

    /**momentum is the mechanic that allows the game to understand if a defense is ready to attack.*/
    public static final int MOMENTUM_REQUIRED = 600;

    /**formula for how many targets a wizard tower can hit.
     * @return the number of targets the tower must attack.
     * @param tower the defense used for the formula.
     * the formula is towerLevel +1.
    */
    public static int wizaredTowerTargetsFormula(final Defense tower) {
        return tower.getLevel() + 1;
    }

    /**formula for the area covered by a bomb tower attack.
     * @return the area afflicted by the tower attack.
     * @param tower the defense used for the formula.
     * the formula is range/2 -1 + level.
    */
    public static int bombTowerDamageAreaFormula(final Defense tower) {
        return tower.getRange() / 2 + tower.getLevel() - 1;
    }

    /**Private constructor.*/
    private DefenseFormulas() {

    }
}
