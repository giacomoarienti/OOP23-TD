package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;

/**
 * @inheritDoc .
 */
public class SimpleEnemyFactory implements EnemyFactory{

    private final LogicalPosition startingPos;
    private final Enemies controller;

    /**
     * Constructor for the class.
     *
     * @param startingPos the starting position for all enemies produced by the factory
     * @param controller the controller to which every enemy should signal an eventual death
     */
    SimpleEnemyFactory(LogicalPosition startingPos, Enemies controller){
        this.startingPos = startingPos;
        this.controller = controller;
    }

    /**
     * @inheritDoc .
     */
    public Enemy build(EnemyType t){
        return new MinimalEnemy(t);
    }

    /**
     * Private class which implements Enemy storing the least possible information.
     *
     * Used a flyweight pattern saving all common information in a single EnemyType object common
     * to all instances of Enemy entities of that type
     */
    private class MinimalEnemy implements Enemy{
        private final EnemyType t;
        private final LogicalPosition pos = startingPos.clone();
        private int hp;

        /**
         * Constructor for the class.
         *
         * @param t the type of the Enemy from which to retrieve hp and speed
         */
        MinimalEnemy(EnemyType t){
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
                    controller.signalDeath(this);
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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'info'");
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


