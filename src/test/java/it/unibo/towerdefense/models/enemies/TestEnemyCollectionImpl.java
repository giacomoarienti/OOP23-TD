package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;
import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.game.GameState;
import it.unibo.towerdefense.views.graphics.GameRenderer;

public class TestEnemyCollectionImpl {

    private EnemyCollectionImpl tested;

    @BeforeEach
    void init() {
        tested = new EnemyCollectionImpl(new GameController() {

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
            public boolean save() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'save'");
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
        }, new MapController() {

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
                return new LogicalPosition(0, 0);
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
            public List<Pair<String, Integer>> getBuildingOptions() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getBuildingOptions'");
            }

            @Override
            public String getMapJSON() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getMapJSON'");
            }
        });
    }

    @Test
    void testAdd() {
    }

    @Test
    void testAreDead() {

    }

    @Test
    void testGetEnemies() {

    }

    @Test
    void testGetEnemiesInfo() {

    }

    @Test
    void testMove() {

    }

    @Test
    void testSignalDeath() {

    }
}
