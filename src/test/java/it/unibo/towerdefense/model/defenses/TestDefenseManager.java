package it.unibo.towerdefense.model.defenses;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**class for testing defense controller.*/
class TestDefenseManager {

    private DefenseManager manager;

    /**sets up the controller for each test.*/
    @BeforeEach
    public void setUp() {
        manager = new DefenseManagerImpl();
    }

    /**Test for "buildDefense" method.*/
    @Test
    void testBuildDefense() throws IOException {
        final LogicalPosition testPos1 = new LogicalPosition(0, 0);
        final LogicalPosition testPos2 = new LogicalPosition(2, 2);
        manager.buildDefense(0, testPos1);
        manager.buildDefense(1, testPos2);
    }
    /**Test for "toJson" method.*/
    @Test
    void testToJson() throws IOException {
        final LogicalPosition testPos1 = new LogicalPosition(0, 0);
        final LogicalPosition testPos2 = new LogicalPosition(2, 2);
        final LogicalPosition testPos3 = new LogicalPosition(10, 10);
        /**Nothing must throw exceptions.*/
        manager.buildDefense(0, testPos1);
        manager.buildDefense(1, testPos2);
        manager.buildDefense(2, testPos3);
        manager.toJSON();
    }

    /**Test for "toJson" method.*/
    @Test
    void testDisassembleDefense() throws IOException {
        /**expected sell value and position.*/
        final LogicalPosition pos = new LogicalPosition(0, 0);
        /**Build and scrap.*/
        manager.buildDefense(0, pos);
        Assertions.assertEquals(manager.getDefenses().size(), 1);
        Assertions.assertTrue(manager.disassembleDefense(pos) > 0);
        Assertions.assertEquals(manager.getDefenses().size(), 0);
    }
}
