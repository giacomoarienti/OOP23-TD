package it.unibo.towerdefense.models.defenses;

import java.io.IOException;
import java.util.Optional;

import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * A factory for building the defenses tha will be available in game.
 */

public interface DefenseFactory {
    /**
     * @return a defense from a previous save file.
     * @param saveFile the save file with all the informations.
     * @throws IOException if errors occur during file reading.
    */
    Defense defenseFromSaveFile(String saveFile) throws IOException;
    /**
     * @return a defense from pre-built stat file.
     * @param statFile the file with the statistics of the defense.
     * @param buildPosition this is the first time we build the defense,so we need to know where to build it.
     * @param upgradesFileName will be used for getting the upgrades if the defense has any.
     * @throws IOException if errors occur during file reading.
    */
    Defense levelOneDefense(String statFile, LogicalPosition buildPosition, Optional<String> upgradesFileName) throws IOException;
    /**
     * @return a defense from a previous save file that uses a custom position for its strategy.
     * @param saveFile the save file with all the informations.
     * @param customPosition the custom position.
     * @throws IOException if errors occur during file reading.
    */
    Defense defenseFromSaveFileWithCustomPoint(String saveFile, LogicalPosition customPosition) throws IOException;

    /**
     * @return a defense from pre-built stat file that uses a customPosition in its strategy.
     * @param statFile the file with the statistics of the defense.
     * @param buildPosition this is the first time we build the defense,so we need to know where to build it.
     * @param upgradesFileName will be used for getting the upgrades if the defense has any.
     * @param customPosition the custom position.
     * @throws IOException if errors occur during file reading.
    */
    Defense levelOneDefenseWithCustomPosition(String statFile, LogicalPosition buildPosition,
    LogicalPosition customPosition, Optional<String> upgradesFileName) throws IOException;

    /**
     * @return an upgraded version of a defense.
     * @param current the defense to upgrade.
     * @param upgradeIndex indicates wich upgrade to use.
     * @param upgradesFileName used for getting the upgrades of the upgrade.
     * @throws IOException if errors occur during file reading.
     */
    Defense upgrade(Defense current, int upgradeIndex, Optional<String> upgradesFileName) throws IOException;
}
