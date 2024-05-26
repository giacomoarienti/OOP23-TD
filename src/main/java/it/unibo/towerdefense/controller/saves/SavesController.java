package it.unibo.towerdefense.controller.saves;

import java.util.List;

import it.unibo.towerdefense.model.saves.Save;

/**
 * Interface that models the controller of the saves.
 */
public interface SavesController {

    /**
     * Runs the controller and display the SaveView.
     */
    void run();

    /**
     * Returns the saves to display.
     * @return the list of saves to display
     */
    List<Save> getSaves();

    /**
     * Starts a new game with the given save.
     * @param save the save to load
     */
    void loadSave(Save save);
}
