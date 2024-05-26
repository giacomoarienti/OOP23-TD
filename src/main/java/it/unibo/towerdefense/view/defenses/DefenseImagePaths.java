package it.unibo.towerdefense.view.defenses;

import java.util.Map;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;

/**creates paths for the images of defenses.*/
public final class DefenseImagePaths {

    private static final String BASE_PATH = "it/unibo/towerdefense/view/";
    private static final String DEFENSE_FOLDER = "defenses/";
    private static final String BULLET_FOLDER = "bullets/";
    private static final String EXTENSION = ".png";
    private static final Map<DefenseType, String> imagesBaseNames =Map.of
    (DefenseType.ARCHERTOWER, "Archer",
    DefenseType.BOMBTOWER, "Bomb",
    DefenseType.WIZARDTOWER, "Wizard",
    DefenseType.THUNDERINVOKER, "Thunder"
    );

    /**public costant indicating imageSize.*/
    public final static int IMAGE_SIZE = 1;

    /**
     * @param type type of defense.
     * @param level level of defense.
     * @return path of the image for a given defense of type x level y.
     */
    public static String buildDefensePath(final DefenseType type, final int level) {
        return BASE_PATH + DEFENSE_FOLDER + imagesBaseNames.get(type) + level + EXTENSION;
    }

    /**
     * @param type type of defense.
     * @return path of the bullet image for a given defense of type x.
     */
    public static String buildBulletPath(final DefenseType type) {
        return BASE_PATH + BULLET_FOLDER + imagesBaseNames.get(type) + EXTENSION;
    }

    /**Private constructor.*/
    private DefenseImagePaths() {

    }
}
