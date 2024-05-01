package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.enemies.EnemyType;

/**
 * @inheritDoc .
 */
public class SimpleEnemySpawner implements EnemySpawner{

    private final LogicalPosition startingPos;
    private final EnemyCollection enemies;

    /**
     * Constructor for the class.
     *
     * @param startingPos the starting position for all enemies produced by the factory
     * @param enemies the class which holds information about the enemies
     */
    SimpleEnemySpawner(final LogicalPosition startingPos, final EnemyCollection enemies){
        this.startingPos = startingPos;
        this.enemies = enemies;
    }

    /**
     * @inheritDoc .
     */
    public void spawn(RichEnemyType t){
        enemies.add(new MinimalEnemy(t));
    }

    /**
     * Private class which implements Enemy storing the least possible information.
     *
     * Used a flyweight pattern saving all common information in a single EnemyType object common
     * to all instances of Enemy entities of that type
     */
    private class MinimalEnemy implements Enemy{

        private record MinimalEnemyInfo(LogicalPosition getPos, Integer getHp, EnemyType getType) implements EnemyInfo{};
        private final RichEnemyType t;
        private final LogicalPosition pos = startingPos.clone();
        private int hp;

        /**
         * Constructor for the class.
         *
         * @param t the type of the Enemy from which to retrieve hp and speed
         */
        MinimalEnemy(RichEnemyType t){
            this.t = t;
            this.hp = t.getMaxHP();
        }

        /**
         * @inheritDoc .
         */
        @Override
        public void hurt(final int amount) {
            if (amount < 0) {
                throw new IllegalArgumentException("Tried to hurt an enemy by " + String.valueOf(amount));
            }else{
                if ((hp-=amount) < 0) {
                    enemies.signalDeath(this);
                }
            }
        }

        /**
         * @inheritDoc
         */
        @Override
        public int getHp() {
            return hp;
        }

        /**
         * @inheritDoc .
         */
        @Override
        public void move(final int x, final int y) {
            pos.set(x, y);
        }

        /**
         * @inheritDoc .
         */
        @Override
        public void move(final LogicalPosition pos) {
            this.move(pos.getX(), pos.getY());
        }

        /**
         * @inheritDoc .
         */
        @Override
        public LogicalPosition getPosition() {
            return pos.clone();
        }

        /**
         * @inheritDoc .
         */
        @Override
        public EnemyInfo info() {
            return new MinimalEnemyInfo(this.getPosition(), this.getHp(), t);
        }

        /**
         * @inheritDoc .
         */
        @Override
        public int getSpeed(){
            return t.getSpeed();
        }
    }
}


