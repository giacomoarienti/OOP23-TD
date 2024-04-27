package it.unibo.towerdefense.controllers.game;

import it.unibo.towerdefense.views.game.GameInfoView;
import it.unibo.towerdefense.views.game.GameInfoViewImpl;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Game info controller implementation.
 */
public class GameInfoControllerImpl implements GameInfoController {

    private final GameController controller;
    private final GameInfoView view;
    private GameInfoState prevState;
    private boolean shouldRender;

    public GameInfoControllerImpl(final GameController gameController) {
        this.controller = gameController;
        // instantiate the view and set the previous state to 0
        this.view = new GameInfoViewImpl();
        this.prevState = new GameInfoState(0, 0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        // if any of the game info has changed update it
        if (
            this.controller.getWave() != this.prevState.wave()
            || this.controller.getMoney() != this.prevState.money()
            || this.controller.getLives() != this.prevState.lives()
        ) {
            this.prevState = new GameInfoState(
                this.controller.getWave(),
                this.controller.getMoney(),
                this.controller.getLives()
            );
            // set the flag to render
            this.shouldRender = true;
            return;
        }
        // if nothing has changed don't render
        this.shouldRender = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final GameRenderer renderer) {
        // if nothing has changed don't render
        if (!this.shouldRender) {
            return;
        }
        // update the view
        this.view.setWave(this.prevState.wave());
        this.view.setMoney(this.prevState.money());
        this.view.setLives(this.prevState.lives());
        // build the view and render it
        renderer.renderInfo(this.view.build());
    }

    private record GameInfoState(int wave, int money, int lives) { }
}
