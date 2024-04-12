package it.unibo.towerdefense.controllers.enemies;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Data about a concrete Enemy to be passed around.
 */
public interface EnemyInfo {

    /**
     * Gives back position as pair of integers.
     *
     * @return position as pair of integers
     */
    Pair<Integer, Integer> getPos();

    /**
     * Gets the enemy's hp.
     *
     * @return the hp
     */
    Integer getHp();
}
