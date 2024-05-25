package it.unibo.towerdefense.model.defenses;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**class for testing defense controller.*/
public class TestDefenseManager {

    DefenseManager manager;

    /**sets up the controller for each test.*/
    @BeforeEach
    public void setUp() {
        manager = new DefenseManagerImpl();
    }

    @Test
    /**Test for "buildDefense" method.*/
    public void testBuildDefense() throws IOException {
        manager.buildDefense(0, new LogicalPosition(0, 0));
        manager.buildDefense(1, new LogicalPosition(5, 5));
    }

    @Test
    /**Test for "toJson" method.*/
    public void testToJson() throws IOException {
        /**Nothing must throw exceptions.*/
        manager.buildDefense(0, new LogicalPosition(0, 0));
        manager.buildDefense(1, new LogicalPosition(5, 5));
        manager.buildDefense(2, new LogicalPosition(10, 10));
        manager.toJSON();
    }

    @Test
    /**Test for "disassembleDefense" method.*/
    public void testDisassembleDefense() throws IOException {
        /**expected sell value and position.*/
        final LogicalPosition pos = new LogicalPosition(0, 0);
        /**Build and scrap.*/
        manager.buildDefense(0, pos);
        Assertions.assertEquals(manager.getDefenses().size(), 1);
        Assertions.assertTrue(manager.disassembleDefense(pos) > 0 );
        Assertions.assertEquals(manager.getDefenses().size(), 0);
    }
}
