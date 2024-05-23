package it.unibo.towerdefense.view.defenses;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**A class for rendering attacks */
public interface AttackAnimation {
    /**@return True if animation is still to render.*/
    boolean isAlive();
    /**@return true if the attack is area based (e.g bomb tower)*/
    boolean isAreaBased();
    /**@return position of attacking tower*/
    LogicalPosition getAttacker();
    /**@return position of attacked entity.*/
    LogicalPosition getAttacked();
    /**decreases life of animation.*/
    void decreaseTimeToLive();
    /**@return type of bullet to render.*/
    DefenseType bulletToRender();
}
