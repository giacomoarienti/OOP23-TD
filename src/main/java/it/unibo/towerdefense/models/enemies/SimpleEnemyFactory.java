package it.unibo.towerdefense.models.enemies;

import java.util.HashSet;
import java.util.Set;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.enemies.EnemyType;
import it.unibo.towerdefense.utils.patterns.Observer;

/**
 * {@inheritDoc}.
 */
public class SimpleEnemyFactory implements EnemyFactory {

    private final LogicalPosition startingPos;
    private final EnemyInfo.Direction startingDir;

    /**
     * Constructor for the class.
     *
     * @param startingPos the starting position for all enemies produced by the
     *                    factory
     */
    SimpleEnemyFactory(final LogicalPosition startingPos, final EnemyInfo.Direction startingDir) {
        this.startingPos = startingPos.clone();
        this.startingDir = startingDir;
    }

    /**
     * {@inheritDoc}.
     */
    public Enemy spawn(final RichEnemyType t) {
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

        /**
         * A record to keep track of the information about an Enemy.
         *
         * @param hp   the current hp of the enemy
         * @param pos  the current position of the enemy
         * @param type the EnemyType of the enemy
         */
        private record EnemyInfoImpl(LogicalPosition pos, EnemyInfo.Direction direction, Integer hp, EnemyType type)
                implements EnemyInfo {
        };

        private final LogicalPosition pos = startingPos.clone();
        private EnemyInfo.Direction dir = startingDir;
        private final Set<Observer<Enemy>> deathObservers;
        private final RichEnemyType t;
        private int hp;

        /**
         * Constructor for the class.
         *
         * @param t the type of the Enemy from which to retrieve hp and speed
         */
        MinimalEnemy(final RichEnemyType t) {
            deathObservers = new HashSet<>();
            this.t = t;
            this.hp = t.getMaxHP();
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public void hurt(final int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Tried to hurt an enemy by " + String.valueOf(amount));
            } else if (isDead()) {
                throw new IllegalStateException("Tried to hurt a dead enemy");
            } else {
                hp -= amount;
                if (isDead()) {
                    die();
                }
            }
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public int getHp() {
            return hp;
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public void move(final LogicalPosition newPos, final EnemyInfo.Direction newDir) {
            if (isDead()) {
                throw new IllegalStateException("Tried to move a dead enemy");
            } else if (newPos == null) {
                throw new NullPointerException("newPos can't be null");
            } else if (newDir == null) {
                throw new NullPointerException("newDir can't be null");
            } else {
                pos.set(newPos.getX(), newPos.getY());
                dir = newDir;
            }
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public LogicalPosition getPosition() {
            return LogicalPosition.copyOf(pos);
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public EnemyInfo info() {
            return new EnemyInfoImpl(this.getPosition(), this.dir, this.getHp(), t);
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public int getSpeed() {
            return t.getSpeed();
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public int getValue() {
            return t.getValue();
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public void addDeathObserver(final Observer<Enemy> observer) {
            deathObservers.add(observer);
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public void die() {
            deathObservers.forEach(o -> o.notify(this));
        }

        /**
         * An enemy is dead when its hp is <= 0.
         *
         * @return whether the enemy is dead or not
         */
        @Override
        public boolean isDead() {
            return hp <= 0;
        }
    }
}
