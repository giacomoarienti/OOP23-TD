package it.unibo.towerdefense.controllers.mediator;

import it.unibo.towerdefense.controllers.defenses.DefensesController;
import it.unibo.towerdefense.controllers.enemies.EnemyController;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.utils.images.ImageLoader;

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
     * Returns the instance of {@link MapController}.
     */
    MapController getMapController();

    /**
     * Returns the instance of {@link DefensesController}.
     */
    DefensesController getDefensesController();

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
