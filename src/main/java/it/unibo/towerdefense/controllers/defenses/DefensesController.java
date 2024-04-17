package it.unibo.towerdefense.controllers.defenses;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.Controller;

import java.util.Map;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;


/**
 * Interface for the controller of the game defenses.
 */
public interface DefensesController extends Controller {
    /**
     * Builds a tower of the given type.
     * @param type the type of defense to build.
     * @param position the position of the defense.
     */
    void buildDefense(DefenseType type, LogicalPosition position);

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
    Map<DefenseType, Integer> getBuildables(LogicalPosition position);

    /**
     * makes the current ready defenses attack the available enemies.
     * @param availableTargets a list of the targets position and health.
     * @return the targets index and the amount of damage to deal (key=index and damage=value).
     */
    Map<Integer, Integer> attackEnemies(List<Pair<LogicalPosition, Integer>> availableTargets);
} 
