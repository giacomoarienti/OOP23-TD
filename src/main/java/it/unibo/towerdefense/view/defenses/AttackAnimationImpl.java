package it.unibo.towerdefense.view.defenses;

import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**A class for rendering attacks */
public class AttackAnimationImpl implements AttackAnimation {

    /**private fields.*/
    int timeToLive;
    boolean isAreaBased;
    LogicalPosition attacker,attacked;

    /**Constructor.
     * @param isAreaBased
     * @param attacker
     * @param Attacked
    */
    public AttackAnimationImpl(boolean isAreaBased,LogicalPosition attacker, LogicalPosition attacked) {
        this.timeToLive = 20;
        this.isAreaBased = isAreaBased;
        this.attacked = attacked;
        this.attacker = attacker;
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

}
