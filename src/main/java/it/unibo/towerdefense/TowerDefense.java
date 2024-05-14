package it.unibo.towerdefense;

import java.io.IOException;

import it.unibo.towerdefense.commons.utils.file.FileUtils;
import it.unibo.towerdefense.controller.Controller;
import it.unibo.towerdefense.controller.ControllerImpl;
import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.model.ModelManagerImpl;
import it.unibo.towerdefense.view.ViewImpl;
import it.unibo.towerdefense.view.View;

/**
 * The main class for the Tower Defense game.
 * This class contains the entry point of the game and initializes the game components.
 */
public final class TowerDefense {

    /**
     * Zero-argument constructor.
     */
    private TowerDefense() {
    }

    /**
     * The main entry point of the Tower Defense application.
     * @param args the command line arguments
     * @throws IOException if the game folder cannot be created
     */
    public static void main(final String[] args) throws IOException {
        // create game folder
        FileUtils.createGameFolder();
        // instantiate the model
        final ModelManager model = new ModelManagerImpl();
        // instantiate the view
        final View view = new ViewImpl();
        // instantiate the controller and launch
        final Controller controller = new ControllerImpl(model, view);
        controller.launch();
    }
}
