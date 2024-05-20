package it.unibo.towerdefense.model.enemies;

import java.util.HashSet;
import java.util.Set;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * {@inheritDoc}.
 */
class SimpleEnemyFactory implements EnemyFactory {

    public SimpleEnemyFactory(){
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public RichEnemy spawn(final RichEnemyType t, final EnemyPosition spawnPos) {
        return new MinimalEnemy(t, spawnPos);
    }

    /**
     * Private class which implements Enemy storing the least possible information.
     *
     * Used a flyweight pattern saving all common information in a single EnemyType
     * object common
     * to all instances of Enemy entities of that type
     */
    private class MinimalEnemy implements RichEnemy {

        /**
         * A record to keep track of the information about an Enemy.
         *
         * @param hp   the current hp of the enemy
         * @param pos  the current position of the enemy
         * @param type the EnemyType of the enemy
         */
        private record EnemyInfoImpl(EnemyPosition pos, Integer hp, EnemyType type)
                implements EnemyInfo {
        };

        private final EnemyPosition pos;
        private final Set<Observer<? super RichEnemy>> deathObservers;
        private final RichEnemyType t;
        private int hp;

        /**
         * Constructor for the class.
         *
         * @param t the type of the Enemy from which to retrieve hp and speed
         */
        MinimalEnemy(final RichEnemyType t, final EnemyPosition startingPos) {
            deathObservers = new HashSet<>();
            this.pos = startingPos.clone();
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
            return (hp/t.getMaxHP()) * 100;
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public void move(final EnemyPosition newPos) {
            if (isDead()) {
                throw new IllegalStateException("Tried to move a dead enemy");
            } else if (newPos == null) {
                throw new NullPointerException("newPos can't be null");
            } else {
                pos.setTo(newPos);
            }
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public EnemyPosition getPosition() {
            return this.pos.clone();
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public EnemyInfo info() {
            return new EnemyInfoImpl(this.getPosition(), this.getHp(), t);
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
        public void addDeathObserver(final Observer<? super RichEnemy> observer) {
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
