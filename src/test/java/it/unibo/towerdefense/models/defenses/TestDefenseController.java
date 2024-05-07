package it.unibo.towerdefense.models.defenses;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.defenses.DefensesController;
import it.unibo.towerdefense.controllers.defenses.DefensesControllerImpl;

/**class for testing defense controller.*/
public class TestDefenseController {

    DefensesController controller;

    /**sets up the controller for each test.*/
    @BeforeEach
    public void setUp() {
        controller = new DefensesControllerImpl();
    }

    @Test
    /**Test for "buildDefense" method.*/
    public void testBuildDefense() throws IOException {
        controller.buildDefense(0, new LogicalPosition(0, 0));
        controller.buildDefense(1, new LogicalPosition(5, 5));
    }

    @Test
    /**Test for "toJson" method.*/
    public void testToJson() throws IOException {
        /**Nothing must throw exceptions.*/
        controller.buildDefense(0, new LogicalPosition(0, 0));
        controller.buildDefense(1, new LogicalPosition(5, 5));
        controller.buildDefense(2, new LogicalPosition(10, 10));
    }

    @Test
    /**Test for "attackEnemies" method.*/
    public void testAttackEnemies() {

    }

    @Test
    /**Test for "getBuildables" method.*/
    public void testGetBuildables() {

    }

    @Test
    /**Test for "disassembleDefense" method.*/
    public void testDisassembleDefense() throws IOException {
        /**expected sell value and position.*/
        final int expectedSellValue = 25;
        final LogicalPosition pos = new LogicalPosition(0, 0);
        /**Build and scrap.*/
        controller.buildDefense(0, pos);
        Assertions.assertEquals(controller.disassembleDefense(pos), expectedSellValue);
    }

    @Test
    /**Test for "toJson" method.*/
    public void testRender() {

    }

    @Test
    /**Test for "fromJson".*/
    public void testFromJson() {

    }
}
