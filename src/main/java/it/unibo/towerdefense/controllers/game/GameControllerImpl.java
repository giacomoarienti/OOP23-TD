package it.unibo.towerdefense.controllers.game;

import it.unibo.towerdefense.models.game.Game;
import it.unibo.towerdefense.models.game.GameDTO;
import it.unibo.towerdefense.models.game.GameImpl;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.views.game.GameInfoView;
import it.unibo.towerdefense.views.game.GameInfoViewImpl;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Game info controller implementation.
 */
public class GameControllerImpl implements GameController {

    private final Game game;
    private final GameInfoView view;
    private GameInfoState prevState;
    private boolean shouldRender;

    private GameControllerImpl(final Game game) {
        this.game = game;
        // instantiate the view and set the previous state to 0
        this.view = new GameInfoViewImpl();
        this.prevState = new GameInfoState(0, 0, 0);
    }

    /**
     * Constructor with GameDTO.
     * @param gameDTO the game DTO
     */
    public GameControllerImpl(final GameDTO gameDTO) {
        this(Game.fromDTO(gameDTO));
    }

    /**
     * Zero-argument constructor, creates a new game instance.
     */
    public GameControllerImpl() {
        this(new GameImpl());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        this.game.setGameState(GameState.PLAYING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.game.setGameState(GameState.PAUSE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save() {
        // TODO pass the game, map and defenses to the SavingImpl constructor
        /*// create saving instance
        final SavingImpl saving = new SavingImpl();
        // write saving
        try {
            final SavingLoader savingLoader = new SavingLoaderImpl();
            savingLoader.writeSaving(saving);
            return true;
        } catch (final IOException e) {
            logger.error("Error saving game", e);
            return false;
        }*/
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void advanceWave() {
        this.game.advanceWave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWave() {
        return this.game.getWave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMoney() {
        return this.game.getMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLives() {
        return this.game.getLives();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return this.game.getGameState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        // if any of the game info has changed update it
        if (
            this.game.getWave() != this.prevState.wave()
            || this.game.getMoney() != this.prevState.money()
            || this.game.getLives() != this.prevState.lives()
        ) {
            this.prevState = new GameInfoState(
                this.game.getWave(),
                this.game.getMoney(),
                this.game.getLives()
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
