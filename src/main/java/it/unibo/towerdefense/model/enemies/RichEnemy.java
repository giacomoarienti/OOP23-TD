package it.unibo.towerdefense.model.enemies;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * Class which extends Enemy with implementation-related details known only to
 * this package.
 */
interface RichEnemy extends Enemy {

    /**
     * Returns the powerlevel of the enemy (roughly hp * speed).
     *
     * @return the powerlevel of the enemy.
     */
    int getPowerLevel();

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
     * Adds the given Observer to those
     * that will be notified on the enemy's death.
     *
     * @param observer the Observer to add to the collection
     */
    void addDeathObserver(Observer<? super RichEnemy> observer);
}
