package it.unibo.towerdefense.models.defenses;

import java.io.IOException;
import java.util.Optional;

import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * A factory for building the defenses tha will be available in game.
 */

public interface DefenseFactory {
    /**
     * An archer tower is one of the most basics and common defenses in a tower defense.
     * It shoots at the closest enemy in its range.
     * @return a defense with a closestTargets strategy and the stats defined by the corresponding json save file.
     * @param fileName the json file with the attribute values for this defense.
     * @throws IOexception if file related errors occur.
     */
    Defense archerTowerFromSaveFile(String fileName) throws IOException;

    /**
     * An archer tower is one of the most basics and common defenses in a tower defense.
     * It shoots at the closest enemy in its range.
     * If the defense was not recovered from a previous save,then this method will be used instead of ArcherTower.
     * @return a defense with a closestTargets strategy and the stats defined by the corresponding json file.
     * @param fileName the json file with the attribute values for this defense.
     * @param buildPosition the position of the tower.
     * @param upgradesFileName if necessary,it passes a json file with an array of upgraded towers to put as upgrades.
     * @throws IOexception if file related errors occur.
     */
    Defense newArcherTowerFrom(String fileName, Optional<String> upgradesFileName,
    LogicalPosition buildPosition) throws IOException;

    /**
     * Builds a bomber tower from scratch.
     * It uses an area based damage strategy,based on the closest target.
     * @return a defense with a closestTargetWithAreaDamage strategy and the stats defined by the corresponding json save file.
     * @param fileName the json file with the attribute values for this defense.
     * @throws IOexception if file related errors occur.
     */
    Defense bomberTowerFromSaveFile(String fileName) throws IOException;

    /**
     * An archer tower is one of the most basics and common defenses in a tower defense.
     * It shoots at the closest enemy in its range.
     * If the defense was not recovered from a previous save,then this method will be used instead of ArcherTower.
     * @return a defense with a closestTargets strategy and the stats defined by the corresponding json file.
     * @param fileName the json file with the attribute values for this defense.
     * @param buildPosition the position of the tower.
     * @param upgradesFileName if necessary,it passes a json file with an array of upgraded towers to put as upgrades.
     * @throws IOexception if file related errors occur.
     */
    Defense newBomberTower(String fileName, Optional<String> upgradesFileName,
    LogicalPosition buildPosition) throws IOException;

    /**
     * Builds a wizard tower from scratch.
     * It attacks multiple enemies at once.
     * @return a defense with a closestTargetWithAreaDamage strategy and the stats defined by the corresponding json save file.
     * @param fileName the json file with the attribute values for this defense.
     * @throws IOexception if file related errors occur.
     */
    Defense wizardTowerToSaveFile(String fileName) throws IOException;

    /**
     * Builds a wizard tower from scratch.
     * It attacks multiple enemies at once.
     * If the defense was not recovered from a previous save,then this method will be used instead of wizardTowerFromSaveFile.
     * @return a defense with a closestTargets strategy and the stats defined by the corresponding json file.
     * @param fileName the json file with the attribute values for this defense.
     * @param buildPosition the position of the tower.
     * @param upgradesFileName if necessary,it passes a json file with an array of upgraded towers to put as upgrades.
     * @throws IOexception if file related errors occur.
     */
    Defense newWizardTower(String fileName, Optional<String> upgradesFileName,
    LogicalPosition buildPosition) throws IOException;

    /**The thunder invoker attacks enemies that are the closest to reach the end of map.
     * It will not be able to attack targets too close to it. 
     * @return a defense with a defense with a closestToCustomPointNotInRange strategy.
     * @param fileName the json file with the attribute values for this defense.
     * @param customPoint the custom position for the strategy.
     * @throws IOexception if file related errors occur.
    */
    Defense thunderInvokerFromSaveFile(String fileName, LogicalPosition customPoint) throws IOException;

    /**The thunder invoker attacks enemies that are the closest to reach the end of map.
     * It will not be able to attack targets too close to it. 
     * @return a defense with a defense with a closestToCustomPointNotInRange strategy.
     * @param fileName the json file with the attribute values for this defense.
     * @param buildPosition the position where to build.
     * @param customPoint the position for the strategy.
     * @param upgradesFileName if necessary,it passes a json file with an array of upgraded towers to put as upgrades.
    */
    Defense newThunderInvoker(String fileName, LogicalPosition buildPosition,
    LogicalPosition customPoint, Optional<String> upgradesFileName) throws IOException;
}
