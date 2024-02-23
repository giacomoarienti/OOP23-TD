package it.unibo.towerdefense.models.enemies;

public interface Enemy {
    void hurt(int amount);
    int getHp();
}
