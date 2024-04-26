package it.unibo.towerdefense.models.enemies;

import java.util.Collection;
import java.util.Set;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;

/**
 * Defines the interface to the enemies.
 */
public interface EnemyCollection {

    /**
     * Moves the enemies.
     */
    void move();

    /**
     * Adds an Enemy to the collection.
     *
     * @param e the new Enemy.
     */
    void add(Enemy e);

    /**
     * Method called by enemies when they die.
     * @param e the dead enemy
     */
    void signalDeath(Enemy e);

    /**
     * Gets the enemies.
     * @return the enemies
     */
    Set<Enemy> getEnemies();

    /**
     * Gets the EnemiesInfo primarily to be passed to view.
     *
     * @return a Collection with all the enemies' EnemyInfo
     */
    Collection<EnemyInfo> getEnemiesInfo();

    /**
     * Method to know wheter any Enemy is still alive.
     * @return True if no Enemy is currently alive, False otherwise
     */
    boolean areDead();

}
