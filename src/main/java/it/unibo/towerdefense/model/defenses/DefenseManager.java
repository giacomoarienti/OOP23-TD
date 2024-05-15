package it.unibo.towerdefense.model.defenses;

import it.unibo.towerdefense.commons.api.JsonSerializable;
import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.Manager;
import it.unibo.towerdefense.model.enemies.Enemy;

import java.util.Map;
import java.io.IOException;
import java.util.List;


/**
 * Interface for the menager of the game defenses.
 */
public interface DefenseManager extends JsonSerializable, Manager {

    /**returns description for build at given position.
     * @param at position to check
     * @return DefenseDescription for given pos.
    */
    DefenseDescription getDescriptionFor(LogicalPosition at);
    /**
     * Builds a tower of the given type.
     * @param choice index of buildable.
     * @param position the position of the defense.
     * @throws IOException
     */
    void buildDefense(int choice, LogicalPosition position) throws IOException;

    /**
     * Removes a defense.
     * @param position where the defense is located at.
     * @return the money you get from disassembling the build.
     * @throws IllegalArgumentException if there is no Defense at given position.
     */
    int disassembleDefense(LogicalPosition position);

    /**
     * Gets the possible buildable defenses on given position.
     * @param position the position where to build.
     * @return the possibles types of buildable defenses and their mapped cost.
     */
    List<DefenseDescription> getBuildables(LogicalPosition position) throws IOException;

    /**
     * makes the current ready defenses attack the available enemies.
     * @param availableTargets a list of the targets position and health.
     * @return the targets index and the amount of damage to deal (key=index and damage=value).
     */
    Map<Integer, Integer> attackEnemies(List<? extends Enemy> availableTargets);
}
