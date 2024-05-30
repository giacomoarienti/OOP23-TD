package it.unibo.towerdefense.model.defenses.costants;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;

/**file paths used by factories.*/
public final class DefenseMapFilePaths {

    private static final String ROOT = "it/unibo/towerdefense/models/defenses/";
    /**base archer tower file path.*/
    public static final String ARCHER_TOWER_LV1 = ROOT + "lv1Builds/Archer.json";
    /**upgraded versions of archer tower file paths.*/
    public static final String ARCHER_TOWER_UPGRADES = ROOT + "upgradeBuilds/Archer.json";
    /**base bomb tower file path.*/
    public static final String BOMB_TOWER_LV1 = ROOT + "lv1Builds/Bomb.json";
    /**upgraded versions of bomb tower file paths.*/
    public static final String BOMB_TOWER_UPGRADES = ROOT + "upgradeBuilds/Bomb.json";
    /**base bomb tower file path.*/
    public static final String WIZARD_TOWER_LV1 = ROOT + "lv1Builds/Wizard.json";
    /**upgraded versions of bomb tower file paths.*/
    public static final String WIZARD_TOWER_UPGRADES = ROOT + "upgradeBuilds/Wizard.json";
    /**base bomb tower file path.*/
    public static final String THUNDER_INVOKER_LV1 = ROOT + "lv1Builds/Invoker.json";
    /**upgraded versions of bomb tower file paths.*/
    public static final String THUNDER_INVOKER_UPGRADES = ROOT + "upgradeBuilds/Invoker.json";

    /**
     * @return a file path based on a type.
     * @param type
     */
    public static String upgradePathFromType(final DefenseType type) {
        switch (type) {
            case ARCHERTOWER:
                return ARCHER_TOWER_UPGRADES;
            case BOMBTOWER:
                return BOMB_TOWER_UPGRADES;
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
