package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.utils.patterns.Observer;

/**
 * Class which extends Enemy with details known only to the package.
 */
interface RichEnemy extends Enemy {

    /**
     * Sets the enemy's position and facing direction to be the same of those passed
     * as an argument.
     *
     * @param pos the new position
     */
    void move(LogicalPosition pos, EnemyInfo.Direction facingDirection);

    /**
     * Kills the enemy.
     *
     * Performs all the associated tasks and notifies all observers.
     */
    void die();

    /**
     * Returns wheter the enemy is dead or not.
     *
     * @return wheter the enemy is dead or not
     */
    boolean isDead();

    /**
     * Adds the given Observer to those
     * that will be notified on the enemy's death.
     *
     * @param observer the Observer to add to the collection
     */
    void addDeathObserver(Observer<? super RichEnemy> observer);

}
