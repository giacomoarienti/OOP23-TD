package it.unibo.towerdefense.views.defenses;

import java.util.List;

import it.unibo.towerdefense.commons.dtos.DefenseDescription;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.graphics.GameRenderer;
import it.unibo.towerdefense.controllers.defenses.DefenseType;

/**Interface for the Defenses view.*/
public interface DefenseView {

    /**Renders the given defenses based on their descriptions.
     * @param  gameRendere the renderer to wich drawables are submitted.
     * @param defenses the defenses to submit.
    */
    void render(GameRenderer gamrRenderer, List<DefenseDescription> defenses);

    /**Renders attacks on enemies
     * @param gameRenderer the renderer to wich drawables are submitted.
     * @param towerPosition the position of the attacking defense.
     * @param entityPosition the defense of the entityPosition.
     * @param type to understand how to render the bullet.
    */
    void renderAttacks(GameRenderer renderer, LogicalPosition towerPosition,
    LogicalPosition entityPosition, DefenseType type);
}
