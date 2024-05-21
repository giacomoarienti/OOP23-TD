package it.unibo.towerdefense.controller;

import java.util.Objects;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.dtos.GameState;
import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.game.ControlAction;
import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.controller.gamelauncher.GameLauncherControllerImpl;
import it.unibo.towerdefense.controller.gameloop.GameLoop;
import it.unibo.towerdefense.controller.menu.StartMenuControllerImpl;
import it.unibo.towerdefense.controller.savings.SavingsControllerImpl;
import it.unibo.towerdefense.controller.scoreboard.ScoreboardControllerImpl;
import it.unibo.towerdefense.model.Model;
import it.unibo.towerdefense.model.saving.Saving;
import it.unibo.towerdefense.view.View;

public class ControllerImpl implements Controller {

    private final static Logger logger =
        LoggerFactory.getLogger(ControllerImpl.class);
    private final static Size MAP_SIZE = Constants.MAP_SIZE; // might be a variable in the future

    private final View view;
    private final Model model;

    private String playerName;
    private boolean loopTerminated;

    /**
     * Constructor for the ControllerImpl class.
     * @param model the main model of the game
     * @param view the main view of the game
     */
    public ControllerImpl(final Model model, final View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void launch() {
        final var gameLauncherController = new GameLauncherControllerImpl(
            this.view,
            this::run
        );
        gameLauncherController.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(final String playerName, final Size resolution) {
        logger.info("run()");
        this.playerName = playerName;
        // display the game window
        this.view.displayWindow(resolution);
        // display the StartMenu
        final var menu = new StartMenuControllerImpl(this, this.view);
        menu.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        logger.info("start()");
        // init the model with the player name and the map size
        this.model.init(this.playerName, MAP_SIZE);
        this.afterStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Saving saving) {
        logger.info("start() with saving");
        // init the model with saving
        this.model.init(saving);
        this.afterStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAndExit() {
        logger.info("saveAndExit()");
        if (Objects.isNull(this.model)) {
            throw new IllegalStateException("Game not started");
        }
        // save the game and exit
        this.stop();
        this.save();
        // exit
        this.exit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {
        logger.info("exit()");
        this.view.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        this.loopTerminated = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTerminated() {
        return this.loopTerminated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.model.isPlaying();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displaySavings() {
        final var savingsController = new SavingsControllerImpl(
            this.playerName,
            this.view,
            this::start
        );
        savingsController.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayScoreboard() {
        final var scoreboardController = new ScoreboardControllerImpl(
            this.view
        );
        scoreboardController.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        model.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        view.render(new GameState() {
            @Override
            public Stream<EnemyInfo> getEnemies() {
                return model.getEnemiesDTOs();
            }
            @Override
            public Stream<CellInfo> getMap() {
                return model.getMapDTOs();
            }
            @Override
            public Stream<DefenseDescription> getDefenses() {
                return model.getDefensesDTOs();
            }
        });
    }

    private void startGameLoop() {
        // set game to playing
        this.model.resume();
        // initialize game loop and start it
        final GameLoop.Builder gameLoopBuilder = new GameLoop.Builder();
        final GameLoop gameLoop = gameLoopBuilder.build(this);
        gameLoop.start();
    }

    private void handleGameChange(final GameDTO dto) {
        // render game and controls
        this.view.renderGame(dto);
        this.view.renderControls(dto.getStatus());
        // update buy menu
        if (!model.isPlaying()) {
            this.view.clearBuyMenu();
        }
        // check if game is over
        if (model.isGameOver()) {
            this.handleGameOver();
        }
    }

    private void handleGameOver() {
        // stop game
        this.stop();
        // save score
        final var score = model.saveScore();
        // display game over
        this.view.displayGameOver(score.toDTO());
    }

    private void handleCellSelection(final Position position) {
        if (model.isPlaying()) {
            model.selectCell(position);
            updateBuyMenu();
        }
    }

    private void handleDefenseBuild(final int index) {
        model.build(index);
        updateBuyMenu();
    }

    private void updateBuyMenu() {
        if (model.isPlaying()) {
            view.renderBuyMenu(model.getBuildingOptions());
        }
    }

    private void afterStart() {
        // view initialization
        this.view.setMapSize(MAP_SIZE);
        this.view.addMapCellSelectionObserver((pos) -> this.handleCellSelection(pos));
        this.view.addBuyMenuObserver(i -> this.handleDefenseBuild(i));
        this.view.addControlsObserver((action) -> this.handleControls(action));
        // initialize model observers
        this.model.addGameObserver(this::handleGameChange);
        // start game loop
        this.startGameLoop();
        // start first wave
        this.model.startWave();
    }

    private void handleControls(final ControlAction action) {
        // handle quit
        if (action.equals(ControlAction.QUIT)) {
            // if is still playing save before exiting
            if (model.isPlaying()) {
                this.saveAndExit();
            } else {
                this.exit();
            }
        }
        // update model
        this.model.handleControls(action);
    }
}
