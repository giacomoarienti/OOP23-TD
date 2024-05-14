package it.unibo.towerdefense.controller.mediator;

import it.unibo.towerdefense.commons.utils.images.ImageLoader;
import it.unibo.towerdefense.controller.enemies.EnemyController;
import it.unibo.towerdefense.controller.game.GameController;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.map.MapManager;

/**
 * Interface that models a mediator between controllers.
 */
public interface ControllerMediator {

    /**
     * Saves the game.
     */
    void save();

    /**
     * Returns the singleton instance of {@link ImageLoader}.
     * @return the imageLoader.
     */
    ImageLoader getImageLoader();

    /**
     * Returns the instance of {@link GameController}.
     */
    GameController getGameController();

    /**
     * Returns the instance of {@link MapManager}.
     */
    MapManager getMapController();

    /**
     * Returns the instance of {@link DefenseManager}.
     */
    DefenseManager getDefensesController();

    /**
     * Returns the instance of {@link EnemyController}.
     */
    EnemyController getEnemyController();

    /**
     * alls update() on each controller.
     */
    void update();

    /**
     * Calls render() on each controller.
     */
    void render();
}
