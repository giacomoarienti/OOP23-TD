package it.unibo.towerdefense.commons.dtos.enemies;

import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**
 * Data about an Enemy to be passed around (DTO).
 */
public interface EnemyInfo {

    /**
     * Simple enum describing the four directions an enemy can be facing.
     */
    public static enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST;
        /**
         * Returns the direction faced after having gone from point A to B, assuming A
         * and B share one and only one coordinate, if not, returned Direction is
         * meaningless.
         *
         * @param A the starting point
         * @param B the arrival point
         * @return the direction facing after movement
         */
        public static Direction fromAToB(LogicalPosition A, LogicalPosition B) {
            if (A.getX() == B.getY()) {
                return A.getY() > B.getY() ? SOUTH : NORTH;
            } else {
                return A.getX() > B.getX() ? WEST : EAST;
            }
        }
    }

    /**
     * Getter for the LogicalPosition of the Enemy.
     *
     * @return the position of the Enemy
     */
    LogicalPosition pos();

    /**
     * Getter for the direction the enemy is currently facing.
     *
     * @return the direction faced by the enemy
     */
    Direction direction();

    /**
     * Getter for the Enemy's current hp.
     *
     * @return the hp
     */
    Integer hp();

    /**
     * Getter for the Enemy's EnemyType.
     *
     * @return the EnemyType of the enemy.
     */
    EnemyType type();
}
