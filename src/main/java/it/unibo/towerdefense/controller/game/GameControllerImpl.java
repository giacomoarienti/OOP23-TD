package it.unibo.towerdefense.controller.game;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.view.game.GameInfoView;
import it.unibo.towerdefense.view.game.GameInfoViewImpl;
import it.unibo.towerdefense.view.graphics.GameRenderer;

/**
 * Game info controller implementation.
 */
public class GameControllerImpl implements GameController {

    private final ModelManager manager;
    private final GameInfoView view;

    private GameControllerImpl(final ModelManager manager) {
        this.manager = manager;
        // instantiate the view and set it as observer
        this.view = new GameInfoViewImpl();
        this.manager.getGame().addObserver(view);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final GameRenderer renderer) {
        // if nothing has changed don't render
        if (!this.view.shouldRender()) {
            return;
        }
        // build the view and render it
        renderer.renderInfo(this.view.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObserver(final Observer<GameDTO> observer) {
        this.manager.getGame().addObserver(observer);
    }

}
