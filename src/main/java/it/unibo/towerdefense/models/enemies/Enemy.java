package it.unibo.towerdefense.models.enemies;
/**
 * Interface of Enemy.
 */
public interface Enemy {
    /**
     * Hurts the enemy.
     * @param amount how much
     */
    void hurt(int amount);

    /**
     * Returns the HP.
     * @return the hp
     */
    int getHp();
}
