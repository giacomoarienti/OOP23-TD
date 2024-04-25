package it.unibo.towerdefense.commons;

import java.io.File;

/**
 * Class containing common constants values.
 */
public final class Constants {
    /**
     * The name of the game.
     */
    public static final String GAME_NAME = "Tower Defense";

    /**
     * The default game folder.
     */
    public static final String GAME_FOLDER = System.getProperty("user.home")
            + File.separator
            + ".towerdefense";

    /**
     * The maximum length of player's name.
     */
    public static final int MAX_NAME_LENGTH = 15;

    private Constants() {
    }
}
