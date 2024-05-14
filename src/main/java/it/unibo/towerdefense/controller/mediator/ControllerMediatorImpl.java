package it.unibo.towerdefense.controller.mediator;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.towerdefense.commons.dtos.game.GameDTO;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;
import it.unibo.towerdefense.controller.Controller;
import it.unibo.towerdefense.controller.SerializableModel;
import it.unibo.towerdefense.controller.enemies.EnemyController;
import it.unibo.towerdefense.controller.enemies.EnemyControllerImpl;
import it.unibo.towerdefense.controller.game.GameController;
import it.unibo.towerdefense.controller.game.GameControllerImpl;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.defenses.DefenseManagerImpl;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.map.MapManagerImpl;
import it.unibo.towerdefense.model.savingloader.SavingLoaderImpl;
import it.unibo.towerdefense.model.savingloader.saving.Saving;
import it.unibo.towerdefense.model.savingloader.saving.SavingFieldsEnum;
import it.unibo.towerdefense.model.savingloader.saving.SavingImpl;
import it.unibo.towerdefense.view.graphics.GameRenderer;

/**
 * Class that implements the ControllerMediator interface.
 */
public class ControllerMediatorImpl implements ControllerMediator {

    private final static Logger logger =
        LoggerFactory.getLogger(ControllerMediatorImpl.class);

    private final GameRenderer gameRenderer;
    private final GameController gameController;
    private final MapManager mapController;
    private final DefenseManager defensesController;
    private final EnemyController enemyController;
    private List<Controller> controllers;
    private Map<SavingFieldsEnum, SerializableModel> serializableControllers;

    /**
     * Initializes the different controllers and binds itself to them.
     * @param playerName the player name
     * @param mapSize the size of the map
     * @param renderer the game renderer
     */
    public ControllerMediatorImpl(
        final String playerName,
        final Size mapSize,
        final GameRenderer renderer
    ) {
        this.gameRenderer = renderer;
        // initialize controllers
        this.gameController = new GameControllerImpl(playerName);
        this.mapController = new MapManagerImpl(mapSize, this);
        this.defensesController = new DefenseManagerImpl(this);
        this.enemyController = new EnemyControllerImpl(this);
        // add controllers to the list
        this.addControllersToLists();
    }

    /**
     * Constructor from Saving.
     * Initializes the controllers based on the state provided by the Saving.
     * @param saving the saving object
     * @param renderer the game renderer
     */
    public ControllerMediatorImpl(final Saving saving, final GameRenderer renderer) {
        this.gameRenderer = renderer;
        // initialize controllers from saving
        this.gameController = new GameControllerImpl(
            GameDTO.fromJson(saving.getGameJson())
        );
        this.mapController = new MapManagerImpl(saving.getMapJson(), this);
        this.defensesController = new DefenseManagerImpl(
            this,
            saving.getDefensesJson()
        );
        this.enemyController = new EnemyControllerImpl(this);
        // add controllers to the list
        this.addControllersToLists();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        // calls update on each controller
        this.controllers.forEach(Controller::update);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        // calls render on each controller
        this.controllers.forEach(
            controller -> controller.render(this.gameRenderer)
        );
        // force repaint
        this.gameRenderer.renderCanvas();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        // create saving instance
        final var json = this.toJSON();
        final SavingImpl saving = new SavingImpl(json);
        // write saving
        try {
            final SavingLoaderImpl savingLoader = new SavingLoaderImpl(
                this.gameController.getPlayerName()
            );

            if(!savingLoader.writeSaving(saving)) {
                throw new IOException("Failed to write saving");
            }
        } catch (final IOException e) {
            // throw runtime exception
            logger.error("Error while saving the game", e);
            throw new RuntimeException("Error while saving the game");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageLoader getImageLoader() {
        return this.gameRenderer.getImageLoader();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameController getGameController() {
        if (Objects.isNull(this.gameController)) {
            throw new IllegalStateException("GameController not initialized");
        }
        return this.gameController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapManager getMapController() {
        if (Objects.isNull(this.mapController)) {
            throw new IllegalStateException("MapController not initialized");
        }
        return this.mapController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DefenseManager getDefensesController() {
        if (Objects.isNull(this.defensesController)) {
            throw new IllegalStateException("DefensesController not initialized");
        }
        return this.defensesController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnemyController getEnemyController() {
        if (Objects.isNull(this.enemyController)) {
            throw new IllegalStateException("EnemyController not initialized");
        }
        return this.enemyController;
    }

    private Map<SavingFieldsEnum, String> toJSON() {
        return this.serializableControllers.entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    Entry::getKey,
                    entry -> entry.getValue().toJSON()
                )
            );
    }

    private void addControllersToLists() {
        // add controllers to the list
        this.controllers = List.of(
            this.gameController,
            this.mapController,
            this.defensesController,
            this.enemyController
        );
        // save serializable controllers
        this.serializableControllers = Map.of(
            SavingFieldsEnum.GAME, gameController,
            SavingFieldsEnum.MAP, mapController,
            SavingFieldsEnum.DEFENSES, defensesController
        );
    }
}
