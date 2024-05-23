package it.unibo.towerdefense.model.enemies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.map.MapManager;

/**
 * Class for testing the EnemiesManager does a good job at binding the Enemies
 * model to the other Managers.
 * Since all other functionality of this class are implemented elsewhere they
 * have already been tested along with the class which implements them.
 */
class TestEnemiesManagerImpl {

    /**
     * Simple testing ModelManager which always returns null.
     */
    private class TestingModelManager implements ModelManager {
        /**
         * {@inheritDoc}.
         */
        @Override
        public MapManager getMap() {
            return null;
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public DefenseManager getDefenses() {
            return null;
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public EnemiesManager getEnemies() {
            return null;
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public GameManager getGame() {
            return null;
        }
    }

    /**
     * Tests the manager throws if it has not been bound yet and any method is
     * called other than bind and that it throws if bind is called more than once.
     */
    @Test
    void testBind() {
        var tested = new EnemiesManagerImpl();
        Assertions.assertThrows(IllegalStateException.class, () -> tested.spawn(1));
        Assertions.assertThrows(IllegalStateException.class, () -> tested.getEnemies());
        Assertions.assertThrows(IllegalStateException.class, () -> tested.update());
        Assertions.assertDoesNotThrow(() -> tested.bind(new TestingModelManager()));
        Assertions.assertThrows(IllegalStateException.class, () -> tested.bind(new TestingModelManager()));
    }
}
