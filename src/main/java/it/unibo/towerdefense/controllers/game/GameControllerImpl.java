package it.unibo.towerdefense.controllers.game;

import it.unibo.towerdefense.models.game.Game;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.models.game.GameDTO;
import it.unibo.towerdefense.models.game.GameDTOImpl;
import it.unibo.towerdefense.models.game.GameImpl;
import it.unibo.towerdefense.views.game.GameInfoView;
import it.unibo.towerdefense.views.game.GameInfoViewImpl;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Game info controller implementation.
 */
public class GameControllerImpl implements GameController {

    private static final int DEFAULT_VALUE = 0;

    private final Game game;
    private final GameInfoView view;
    private GameDTO prevState;
    private boolean shouldRender;

    private GameControllerImpl(final Game game) {
        this.game = game;
        // instantiate the view and set the previous state
        this.view = new GameInfoViewImpl();
        this.prevState = new GameDTOImpl(DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE);
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
    public void addMoney(final int amount) {
        this.game.addMoney(amount);
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
        final GameDTO newState = this.game.toDTO();
        if (!this.prevState.equals(newState)) {
            this.prevState = newState;
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
        this.view.setGameInfo(this.prevState);
        // build the view and render it
        renderer.renderInfo(this.view.build());
    }

}
