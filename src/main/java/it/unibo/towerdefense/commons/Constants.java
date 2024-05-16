package it.unibo.towerdefense.commons;

import java.io.File;
import java.util.List;

import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.commons.engine.SizeImpl;

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

    /**
     * The list of available resolutions.
     */
    public static final List<Size> RESOLUTIONS = List.of(
        new SizeImpl(3440, 1440),
        new SizeImpl(2560, 1440),
        new SizeImpl(1920, 1080),
        new SizeImpl(1280, 720)
    );

    /**
     * The size of Map in cells.
     */
    public static final Size MAP_SIZE = new SizeImpl(30, 20);

    private Constants() {
    }
}
