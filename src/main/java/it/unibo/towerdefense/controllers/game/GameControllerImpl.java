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

    private final Game game;
    private final GameInfoView view;
    private GameDTO prevState;
    private boolean shouldRender;

    private GameControllerImpl(final Game game) {
        this.game = game;
        // instantiate the view and set the previous state
        this.view = new GameInfoViewImpl();
        this.prevState = new GameDTOImpl(game.getPlayerName());
    }

    /**
     * Constructor with GameDTO.
     * @param gameDTO the game DTO
     */
    public GameControllerImpl(final GameDTO gameDTO) {
        this(Game.fromDTO(gameDTO));
    }

    /**
     * Constructor with playerName, it initializes a new game with default values.
     * @param playerName the player name
     */
    public GameControllerImpl(final String playerName) {
        this(new GameImpl(playerName));
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
    public void advanceWave() {
        this.game.advanceWave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerName() {
        return this.game.getPlayerName();
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
    public boolean isPurchasable(final int amount) {
        return this.game.getMoney() >= amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlaying() {
        return this.game.getGameState().equals(GameState.PLAYING);
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
    public boolean purchase(final int amount) {
        return this.game.purchase(amount);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return this.game.toDTO().toJSON();
    }

}
