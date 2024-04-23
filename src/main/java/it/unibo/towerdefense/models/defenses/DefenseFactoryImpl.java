package it.unibo.towerdefense.models.defenses;

import java.util.Set;

import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * Implementation of the defenseFactory interface.
 */
public class DefenseFactoryImpl implements DefenseFactory {

    /**
     * an internal factory for the strategies.
     */
    //private EnemyChoiceStrategyFactory strategyFactory;

    /**
     *{@inheritDoc}
     */
    @Override
    public Defense archerTower(String fileName) {
        Defense result = new DefenseImpl(fileName, Set.of());
        return result;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Defense archerTowerFromPosition(String fileName, LogicalPosition reference) {
        Defense result = new DefenseImpl(fileName, Set.of());
        return result;
    }
}
