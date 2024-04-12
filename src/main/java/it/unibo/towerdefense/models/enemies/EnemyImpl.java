package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;

/**
 * Enemy implementation.
 */
public class EnemyImpl implements Enemy {

    private final EnemyType type;
    private final Enemies enemies;
    private final int speed;
    private int hp;
    private EnemyPosition pos;

    /**
     * Constructor for the class.
     *
     * @param type    type of the enemy
     * @param enemies handle to listener for death
     */
    EnemyImpl(final EnemyType type, final Enemies enemies) {
        this.type = type;
        this.hp = type.getMaxHP();
        this.speed = type.getSpeed();
        this.enemies = enemies;
        this.pos = new EnemyPosition(0, 0); //to pass spotbugs
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void hurt(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Tried to hurt an enemy by " + String.valueOf(amount));
        }
        hp -= amount;
        if (hp < 0) {
            enemies.signalDeath(this);
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
    public EnemyPosition getPosition() {
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
    public int getSpeed() {
        return speed;
    }
}
