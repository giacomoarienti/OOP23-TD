package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.views.defenses.DefenseDescription;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Tests for EnemiesImpl.
 */
public class TestEnemiesImpl {

    /**
     * Arbitrary starting position.
     */
    private static final LogicalPosition STARTING_POSITION = new LogicalPosition(0, 0);
    private GameController testing_gc;
    private MapController testing_mp;
    private WavePolicySupplierImpl testing_wp;
    private EnemiesImpl tested;
    private boolean advanced_wave = false;

    /**
     * Initializes the class to be tested along with some helper classes which we
     * presume already work as intended (see other tests).
     * Also instantiates some "fake" controllers which only implement the function
     * needed for testing EnemiesImpl.
     */
    @BeforeEach
    void init() {
        testing_wp = new WavePolicySupplierImpl(Filenames.ROOT + Filenames.WAVECONF);
        testing_gc = new GameController() {
            private int money = 0;

            @Override
            public void update() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'update'");
            }

            @Override
            public void render(GameRenderer renderer) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'render'");
            }

            @Override
            public void resume() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'resume'");
            }

            @Override
            public void pause() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'pause'");
            }

            @Override
            public void advanceWave() {
                TestEnemiesImpl.this.advanced_wave = true;
            }

            @Override
            public int getWave() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getWave'");
            }

            @Override
            public int getMoney() {
                return money;
            }

            @Override
            public void addMoney(int amount) {
                money += amount;
            }

            @Override
            public boolean purchase(int amount) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'purchase'");
            }

            @Override
            public int getLives() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getLives'");
            }

            @Override
            public GameState getGameState() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getGameState'");
            }

            @Override
            public String toJSON() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'toJSON'");
            }

            @Override
            public String getPlayerName() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getPlayerName'");
            }
        };
        testing_mp = new MapController() {

            @Override
            public void render(GameRenderer renderer) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'render'");
            }

            @Override
            public void update() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'update'");
            }

            @Override
            public LogicalPosition getSpawnPosition() {
                return STARTING_POSITION;
            }

            @Override
            public LogicalPosition getEndPosition() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getEndPosition'");
            }

            @Override
            public Optional<LogicalPosition> getNextPosition(LogicalPosition pos, int distanceToMove) {
                return Optional.empty();
            }

            @Override
            public void select(Position position) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'select'");
            }

            @Override
            public Optional<Position> getSelected() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getSelected'");
            }

            @Override
            public void build(int optionNumber) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'build'");
            }

            @Override
            public String toJSON() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'toJSON'");
            }

            @Override
            public List<DefenseDescription> getBuildingOptions() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getBuildingOptions'");
            }
        };
        tested = new EnemiesImpl(testing_mp, testing_gc);
    }

    /**
     * Tests the class behaves correctly right after initialization.
     */
    @Test
    void testEmpty() {
        Assertions.assertTrue(tested.getEnemies().isEmpty());
        Assertions.assertTrue(tested.getEnemiesInfo().isEmpty());
        Assertions.assertDoesNotThrow(() -> tested.update());
    }

    /**
     * Tests the class behaves correctly when asked to start a new wave.
     */
    @Test
    void testSpawn() {
        Assertions.assertThrows(RuntimeException.class, () -> tested.spawn(0));
        Assertions.assertDoesNotThrow(() -> tested.spawn(1));
        Assertions.assertThrows(RuntimeException.class, () -> tested.spawn(2));
    }

    /**
     * Tests the class behaves correctly on updates.
     */
    @Test
    void testUpdate() {
        tested.spawn(1);
        tested.update();
        Assertions.assertTrue(tested.getEnemies().size() == 1);
        Assertions.assertTrue(tested.getEnemiesInfo().size() == 1);
        tested.update();
        // Enemy should have died since no further position is available.
        Assertions.assertTrue(tested.getEnemies().size() == 0);
        Assertions.assertTrue(tested.getEnemiesInfo().size() == 0);
    }

    /**
     * Tests the collection is correctly updated after a contained enemy dies.
     */
    @Test
    void testDied() {
        tested.spawn(1);
        tested.update();
        Enemy onlyEnemy = tested.getEnemies().stream().findAny().get();
        onlyEnemy.hurt(onlyEnemy.getHp());
        Assertions.assertTrue(tested.getEnemies().size() == 0);
        Assertions.assertTrue(tested.getEnemiesInfo().size() == 0);
    }

    /**
     * Tests the wave behaves as it should and update() calls advanceWave the first
     * time it's called after no enemy is alive anymore and no wave is active.
     */
    @Test
    void testWaveEnd() {
        int wave = 1;
        int l = testing_wp.getLength(wave);
        int r = testing_wp.getCyclesPerSpawn(wave);
        tested.spawn(wave);
        for (int i = 0; i < l * r - (r - 1); i++) {
            tested.update();
            System.out.println(tested.getEnemies());
        }
        Assertions.assertTrue(tested.getEnemies().size() == 1);
        Assertions.assertFalse(advanced_wave); //An enemy is still alive
        Assertions.assertThrows(RuntimeException.class, () -> tested.spawn(2));
        tested.update();
        Assertions.assertTrue(tested.getEnemies().size() == 0);
        Assertions.assertTrue(advanced_wave);
        Assertions.assertDoesNotThrow(() -> tested.spawn(2));
    }
}
