package it.unibo.towerdefense.model.enemies;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * Class which extends Enemy with details known only to the package.
 */
interface RichEnemy extends Enemy {

    /**
     * Sets the enemy's position and facing direction to be the same of those of the
     * position passe as an argument.
     *
     * @param pos the position to copy
     */
    void move(EnemyPosition pos);

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
