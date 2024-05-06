package it.unibo.towerdefense.models.defenses.costants;

import it.unibo.towerdefense.controllers.defenses.DefenseType;

/**file paths used by factories.*/
public final class DefenseMapFilePaths {
    /**base archer tower file path.*/
    public static final String ARCHER_TOWER_LV1 =
    "src/main/resources/defenses/base_defenses/archer_tower_lv1.json";
    /**upgraded versions of archer tower file paths.*/
    public static final String ARCHER_TOWER_UPGRADES =
    "src/main/resources/defenses/upgrades/archer_tower_upgr.json";
    /**base bomb tower file path.*/
    public static final String BOMB_TOWER_LV1 =
    "src/main/resources/defenses/base_defenses/bomb_tower_lv1.json";
    /**upgraded versions of bomb tower file paths.*/
    public static final String BOMB_TOWER_UPGRADES =
    "src/main/resources/defenses/upgrades/bomb_tower_upgr.json";
    /**base bomb tower file path.*/
    public static final String WIZARD_TOWER_LV1 =
    "src/main/resources/defenses/base_defenses/wizard_tower_lv1.json";
    /**upgraded versions of bomb tower file paths.*/
    public static final String WIZARD_TOWER_UPGRADES =
    "src/main/resources/defenses/upgrades/wizard_tower_upgr.json";
    /**base bomb tower file path.*/
    public static final String THUNDER_INVOKER_LV1 =
    "src/main/resources/defenses/base_defenses/thunder_invoker_level1.json";
    /**upgraded versions of bomb tower file paths.*/
    public static final String THUNDER_INVOKER_UPGRADES =
    "src/main/resources/defenses/upgrades/thunder_invoker__upgr.json";

    /**
     * @return a file path based on a type.
     * @param type
     */
    public static String pathFromType(final DefenseType type) {
        switch (type) {
            case ARCHERTOWER:
                return ARCHER_TOWER_UPGRADES;
            case BOMBTOWER:
                return ARCHER_TOWER_UPGRADES;
            case WIZARDTOWER:
                return WIZARD_TOWER_UPGRADES;
            case THUNDERINVOKER:
                return THUNDER_INVOKER_UPGRADES;
            default:
                return null;
        }
    }

    /**Private constructor.*/
    private DefenseMapFilePaths() {

    }
}
