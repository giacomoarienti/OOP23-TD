package it.unibo.towerdefense.view.defenses;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**A class for rendering attacks */
public class AttackAnimationImpl implements AttackAnimation {

    /**private fields.*/
    int timeToLive;
    boolean isAreaBased;
    LogicalPosition attacker,attacked;
    DefenseType type;

    /**Constructor.
     * @param isAreaBased
     * @param attacker
     * @param Attacked
    */
    public AttackAnimationImpl(boolean isAreaBased,LogicalPosition attacker, LogicalPosition attacked,
    DefenseType type) {
        this.timeToLive = 50;
        this.isAreaBased = isAreaBased;
        this.attacked = attacked;
        this.attacker = attacker;
        this.type = type;
    }

    @Override
    public boolean isAlive() {
        return this.timeToLive > 0;
    }

    @Override
    public boolean isAreaBased() {
        return this.isAreaBased;
    }

    @Override
    public LogicalPosition getAttacker() {
        return this.attacker;
    }

    @Override
    public LogicalPosition getAttacked() {
        return this.attacked;
    }

    @Override
    public void decreaseTimeToLive() {
        this.timeToLive--;
    }

    @Override
    public DefenseType bulletToRender() {
        return this.type;
    }

}
