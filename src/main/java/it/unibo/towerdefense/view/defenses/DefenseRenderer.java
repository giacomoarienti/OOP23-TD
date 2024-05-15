package it.unibo.towerdefense.view.defenses;

import java.util.List;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;


/**Interface for the Defenses view.*/
public interface DefenseRenderer {
    /**Renders the given defenses based on their descriptions.
     * @param  gameRendere the renderer to wich drawables are submitted.
     * @param defenses the defenses to submit.
    */
    void render(List<DefenseDescription> defenses);
}
