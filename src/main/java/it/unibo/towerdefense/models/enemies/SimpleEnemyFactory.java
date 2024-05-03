package it.unibo.towerdefense.models.enemies;

import java.util.HashSet;
import java.util.Set;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.commons.Observer;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.enemies.EnemyType;

/**
 * {@inheritDoc}
 */
public class SimpleEnemyFactory implements EnemyFactory {

    private final LogicalPosition startingPos;

    /**
     * Constructor for the class.
     *
     * @param startingPos the starting position for all enemies produced by the
     *                    factory
     * @param enemies     the class which holds information about the enemies
     */
    SimpleEnemyFactory(final LogicalPosition startingPos) {
        this.startingPos = startingPos;
    }

    /**
     * {@inheritDoc}
     */
    public Enemy spawn(RichEnemyType t) {
        return new MinimalEnemy(t);
    }

    /**
     * Private class which implements Enemy storing the least possible information.
     *
     * Used a flyweight pattern saving all common information in a single EnemyType
     * object common
     * to all instances of Enemy entities of that type
     */
    private class MinimalEnemy implements Enemy {

        private record MinimalEnemyInfo(LogicalPosition pos, Integer hp, EnemyType type) implements EnemyInfo {
        };

        private final LogicalPosition pos = startingPos.clone();
        private final Set<Observer<Enemy>> deathObservers;
        private final RichEnemyType t;
        private int hp;

        /**
         * Constructor for the class.
         *
         * @param t the type of the Enemy from which to retrieve hp and speed
         */
        MinimalEnemy(RichEnemyType t) {
            deathObservers = new HashSet<>();
            this.t = t;
            this.hp = t.getMaxHP();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void hurt(final int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Tried to hurt an enemy by " + String.valueOf(amount));
            } else if (hp <= 0) {
                throw new IllegalStateException("Tried to hurt a dead enemy");
            } else {
                if ((hp -= amount) <= 0) {
                    die();
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getHp() {
            return hp;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void move(final LogicalPosition newPos) {
            if (hp <= 0) {
                throw new IllegalStateException("Tried to move a dead enemy");
            }else{
                pos.set(newPos.getX(), newPos.getY());
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LogicalPosition getPosition() {
            return pos.clone();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public EnemyInfo info() {
            return new MinimalEnemyInfo(this.getPosition(), this.getHp(), t);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getSpeed() {
            return t.getSpeed();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getValue() {
            return t.getValue();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void addDeathObserver(Observer<Enemy> observer) {
            deathObservers.add(observer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void die() {
            deathObservers.forEach(o -> o.notify(this));
        }
    }
}
