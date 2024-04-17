package it.unibo.towerdefense.controllers.defenses;

import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.models.engine.Position;

/**
 * Interface for the controller of the game defenses.
 */
public interface DefensesController extends Controller {
    /**
     * Builds a tower of the given type.
     * @param type the type of defense to build.
     * @param position the position of the defense.
     */
    void BuildDefense(DefenseType type,Position position);

    /**
     * Removes a defense.
     * @param position where the defense is located at.
     * @return the money you get from disassembling the build.
     * @throws IllegalArgumentException if there is no Defense at given position.
     */
    int DisassembleDefense(Position position);
} 
