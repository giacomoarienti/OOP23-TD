package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.views.defenses.DefenseDescription;
import it.unibo.towerdefense.views.graphics.GameRenderer;

public class TestEnemyCollectionImpl {

    private static final LogicalPosition STARTING_POSITION = new LogicalPosition(0, 0);
    private EnemyCollectionImpl tested;
    private SimpleEnemyFactory helper;
    private GameController gc;

    @BeforeEach
    void init() {
        helper = new SimpleEnemyFactory(STARTING_POSITION);
        gc = new GameController() {

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
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'advanceWave'");
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
        tested = new EnemyCollectionImpl(gc, new MapController() {

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
        });
    }

    @Test
    void testAdd() {
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Enemy spawned = helper.spawn(t);
        tested.add(spawned);
        Assertions.assertTrue(tested.getEnemies().contains(spawned));
        Assertions.assertTrue(tested.getEnemiesInfo().contains(spawned.info()));
    }

    @Test
    void testAreDead() {
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Enemy spawned = helper.spawn(t);
        tested.add(spawned);
        spawned.die();
        Assertions.assertTrue(tested.areDead());
        Assertions.assertTrue(tested.getEnemies().isEmpty());
    }

    @Test
    void testMultipleEnemies() {
        int number = 100;
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Set<Enemy> spawned = IntStream.range(0, number).mapToObj( i -> {
            Enemy e = helper.spawn(t);
            tested.add(e);
            return e;
        }).collect(Collectors.toSet());
        Assertions.assertTrue(tested.getEnemies().containsAll(spawned));
        spawned.forEach( e -> e.die());
        Assertions.assertTrue(tested.areDead());
        Assertions.assertTrue(tested.getEnemies().isEmpty());
        Assertions.assertTrue(gc.getMoney() == t.getValue() * number);
    }

    @Test
    void testMove() {
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Set<Enemy> spawned = IntStream.range(0, 100).mapToObj( i -> {
            Enemy e = helper.spawn(t);
            tested.add(e);
            return e;
        }).collect(Collectors.toSet());
        Assertions.assertTrue(tested.getEnemies().containsAll(spawned));
        tested.move();
        Assertions.assertTrue(tested.areDead());
        Assertions.assertTrue(tested.getEnemies().isEmpty());
    }
}
