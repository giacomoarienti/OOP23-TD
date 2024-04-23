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
     * @return the defense representing the archer tower.
     * @param fileName the json file with the attribute values for this defense.
     * @param reference the position of the defense (to use for calculations).
     * @TODO create upgrades.
     */
    @Override
    public Defense archerTower(final String fileName, final LogicalPosition reference) {
        return new DefenseImpl(fileName, Set.of());
    }
}
