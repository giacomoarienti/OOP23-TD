package it.unibo.towerdefense.models.defenses;
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
    public Defense archerTower(final String fileName) {
        return null;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Defense archerTowerFromPosition(final String fileName, final LogicalPosition defensePosition) {
        return null;
    }
}
