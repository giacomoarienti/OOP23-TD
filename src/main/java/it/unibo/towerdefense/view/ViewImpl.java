package it.unibo.towerdefense.view;

import java.util.List;
import java.util.Objects;

import it.unibo.towerdefense.commons.dtos.game.ControlAction;
import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.map.BuildingOption;
import it.unibo.towerdefense.commons.dtos.score.ScoreDTO;
import it.unibo.towerdefense.commons.dtos.GameState;
import it.unibo.towerdefense.commons.dtos.scoreboard.ScoreboardDTO;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherController;
import it.unibo.towerdefense.controller.menu.StartMenuController;
import it.unibo.towerdefense.controller.savings.SavingsController;
import it.unibo.towerdefense.model.game.GameStatus;
import it.unibo.towerdefense.view.defenses.DefenseRenderer;
import it.unibo.towerdefense.view.defenses.DefenseRendererImpl;
import it.unibo.towerdefense.view.enemies.EnemyRenderer;
import it.unibo.towerdefense.view.enemies.EnemyRendererImpl;
import it.unibo.towerdefense.view.game.GameRenderer;
import it.unibo.towerdefense.view.game.GameRendererImpl;
import it.unibo.towerdefense.view.gamelauncher.GameLauncherViewImpl;
import it.unibo.towerdefense.view.gameover.GameOverViewImpl;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.RendererImpl;
import it.unibo.towerdefense.view.map.BuyMenu;
import it.unibo.towerdefense.view.map.BuyMenuImpl;
import it.unibo.towerdefense.view.map.MapRenderer;
import it.unibo.towerdefense.view.map.MapRendererImpl;
import it.unibo.towerdefense.view.menus.StartMenuViewImpl;
import it.unibo.towerdefense.view.savings.SavingsViewImpl;
import it.unibo.towerdefense.view.scoreboard.ScoreboardViewImpl;
import it.unibo.towerdefense.view.window.Window;
import it.unibo.towerdefense.view.window.WindowImpl;

/**
 * Implementation of the View interface.
 */
public class ViewImpl implements View {

    private Window window;
    private Renderer renderer;
    private GameRenderer gameRenderer;
    private MapRenderer mapRenderer;
    private DefenseRenderer defenseRenderer;
    private EnemyRenderer enemyRenderer;
    private BuyMenu buyMenu;

    /**
     * Empty constructor.
     */
    public ViewImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayLauncher(final GameLauncherController controller) {
        // instantiate the game launcher view
        final var gameLauncherView = new GameLauncherViewImpl(controller);
        gameLauncherView.display();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayWindow(final Size size) {
        this.window = new WindowImpl(size.copy());
        this.window.display();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySavings(final SavingsController controller) {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        final var savingsView = new SavingsViewImpl(controller);
        this.window.displayModal("Savings", savingsView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayStartMenu(final StartMenuController controller) {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        final var startMenu = new StartMenuViewImpl(controller);
        this.window.displayModal("Start Menu", startMenu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        this.window.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayScoreboard(final ScoreboardDTO dto) {
        if (Objects.isNull(this.window)) {
            throw new IllegalStateException("Window not created yet");
        }
        this.window.displayModal("Scoreboard", new ScoreboardViewImpl(dto));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMapSize(final Size mapSize) {
        this.initRenderers(mapSize);
        this.window.setMapSize(mapSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderGame(final GameDTO dto) {
        if (Objects.isNull(this.gameRenderer)) {
            throw new IllegalStateException("GameRenderer not created yet");
        }
        this.gameRenderer.render(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderControls(GameStatus status) {
        if (Objects.isNull(this.gameRenderer)) {
            throw new IllegalStateException("GameRenderer not created yet");
        }
        this.gameRenderer.render(status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderBuyMenu(List<BuildingOption> options) {
        window.setBuyMenuContent(buyMenu.getJPanel(options));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final GameState state) {
        // on first call, init the game renderer and it's renderers
        if (Objects.isNull(this.renderer)) {
            throw new IllegalStateException("GameRenderer not created yet");
        }
        // clear the canvas
        this.renderer.clearCanvasQueue();
        // render state
        this.mapRenderer.render(this.renderer, state.getMap());
        this.defenseRenderer.render(state.getDefenses());
        this.enemyRenderer.render(state.getEnemies());
        // repaint canvas
        this.renderer.renderCanvas();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMapCellSelectionObserver(final Observer<Position> observer) {
        this.window.addCanvasClickObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuyMenuObserver(final Observer<Integer> observer) {
        this.buyMenu = new BuyMenuImpl(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addControlsObserver(Observer<ControlAction> observer) {
        this.gameRenderer.addControlsObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearBuyMenu() {
        this.window.setBuyMenuContent(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayGameOver(final ScoreDTO dto) {
        this.window.displayModal("Game Over", new GameOverViewImpl(dto));
    }

    private void initRenderers(final Size mapSize) {
        this.renderer = new RendererImpl(mapSize, this.window);
        // init renderers
        this.mapRenderer = new MapRendererImpl(this.renderer.getImageLoader());
        this.gameRenderer = new GameRendererImpl(this.renderer);
        this.defenseRenderer = new DefenseRendererImpl(this.renderer);
        this.enemyRenderer = new EnemyRendererImpl(this.renderer);
    }
}
