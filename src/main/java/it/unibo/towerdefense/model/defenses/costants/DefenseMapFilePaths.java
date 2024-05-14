package it.unibo.towerdefense.model.defenses.costants;

import it.unibo.towerdefense.model.defenses.DefenseType;

/**file paths used by factories.*/
public final class DefenseMapFilePaths {
    /**base archer tower file path.*/
    public static final String ARCHER_TOWER_LV1 =
    "src/main/resources/it/unibo/towerdefense/models/defenses/lv1Builds/Archer.json";
    /**upgraded versions of archer tower file paths.*/
    public static final String ARCHER_TOWER_UPGRADES =
    "src/main/resources/it/unibo/towerdefense/models/defenses/upgradeBuilds/Archer.json";
    /**base bomb tower file path.*/
    public static final String BOMB_TOWER_LV1 =
    "src/main/resources/it/unibo/towerdefense/models/defenses/lv1Builds/Bomb.json";
    /**upgraded versions of bomb tower file paths.*/
    public static final String BOMB_TOWER_UPGRADES =
    "src/main/resources/it/unibo/towerdefense/models/defenses/upgradeBuilds/Bomb.json";
    /**base bomb tower file path.*/
    public static final String WIZARD_TOWER_LV1 =
    "src/main/resources/it/unibo/towerdefense/models/defenses/lv1Builds/Wizard.json";
    /**upgraded versions of bomb tower file paths.*/
    public static final String WIZARD_TOWER_UPGRADES =
    "src/main/resources/it/unibo/towerdefense/models/defenses/upgradeBuilds/Wizard.json";
    /**base bomb tower file path.*/
    public static final String THUNDER_INVOKER_LV1 =
    "src/main/resources/it/unibo/towerdefense/models/defenses/lv1Builds/Invoker.json";
    /**upgraded versions of bomb tower file paths.*/
    public static final String THUNDER_INVOKER_UPGRADES =
    "src/main/resources/it/unibo/towerdefense/models/defenses/upgradeBuilds/Invoker.json";

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
