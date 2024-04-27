package it.unibo.towerdefense.models.game;

import it.unibo.towerdefense.models.Copyable;

public record GameInfo(int wave, int money, int lives) implements Copyable<GameInfo> {

    /**
     * {@inheritDoc}
     */
    @Override
    public GameInfo copy() {
        return new GameInfo(wave, money, lives);
    }
}
