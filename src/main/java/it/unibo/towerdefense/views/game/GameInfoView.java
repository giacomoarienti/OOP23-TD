package it.unibo.towerdefense.views.game;

import it.unibo.towerdefense.views.View;

/**
 * Interface that models the game info view.
 */
public interface GameInfoView extends View {

    /**
     * Sets the wave number.
     */
    void setWave(int wave);

    /**
     * Sets the money amount.
     */
    void setMoney(int money);

    /**
     * Sets the lives amount.
     */
    void setLives(int lives);
}
