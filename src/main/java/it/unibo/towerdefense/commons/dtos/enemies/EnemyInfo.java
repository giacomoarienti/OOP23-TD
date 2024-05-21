package it.unibo.towerdefense.commons.dtos.enemies;

/**
 * Data about an Enemy to be passed around (DTO).
 */
public interface EnemyInfo {

    static final int HP_SCALE = 100;

    /**
     * Getter for the LogicalPosition of the Enemy.
     *
     * @return the position of the Enemy
     */
    EnemyPosition pos();

    /**
     * Getter for the Enemy's current hp/HP_SCALE rateo.
     *
     * @return the hp rateo
     */
    Integer hp();

    /**
     * Getter for the Enemy's EnemyType.
     *
     * @return the EnemyType of the enemy.
     */
    EnemyType type();
}
