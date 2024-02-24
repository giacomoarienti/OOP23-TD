package it.unibo.towerdefense.models.saving;

import java.util.List;

import it.unibo.towerdefense.models.JsonSerializable;
import it.unibo.towerdefense.models.game.Game;

/**
 * Interface that models a game saving.
 * The saving is made up of:
 * - the game state and statistics
 * - the game map
 * - the game entities (defenses).
 */
public interface Saving extends JsonSerializable<Saving> {

    /**
     * Returns the game state.
     * @return the game state
     */
    Game getGame();

    /**
     * Returns the game map.
     * @return the game map
     */
    Object getMap();

    /**
     * Returns the list of game defenses.
     * @return the list of game defenses
     */
    List<Object> getDefenses();

    /**
     * Returns the saving object from JSON string.
     * @param json the JSON representation of the saving
     * @return the saving object
     */
    static Saving fromJson(final String jsonData) {
        return new SavingImpl().fromJSON(jsonData);
    }

}
