package it.unibo.towerdefense.view.defenses;

import java.util.Map;
import it.unibo.towerdefense.model.defenses.DefenseType;

/**creates paths for the images of defenses.*/
public final class DefenseImagePaths {

    private static final String BASE_PATH = "it/unibo/towerdefense/view/defenses/";
    private static final String EXTENSION = ".png";
    private static Map<DefenseType, String> ImagesBaseNames =Map.of
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
    public static String buildPath(DefenseType type, int level) {
        return BASE_PATH + ImagesBaseNames.get(type) + level + EXTENSION;
    }

    /**Private constructor.*/
    private DefenseImagePaths() {

    }
}
