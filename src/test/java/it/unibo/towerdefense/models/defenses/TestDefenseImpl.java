package it.unibo.towerdefense.models.defenses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;

import java.util.Set;

/**Tests for the implementation of Defense.*/
public class TestDefenseImpl {
    
    /**Test of constructors and get methods*/
    @Test
    void testConstructorsAndGetters() {
        /**Test values.*/
        final int testDamage = 10;
        final int testLevel = 1;
        final int testSpeed = 5;
        final int testBuildCost = 12;
        final int testSellCost = 5;
        final LogicalPosition testPosition = new LogicalPosition(10, 10);

        /**Test full constructor*/
        Defense result = new DefenseImpl(testDamage, testLevel, testPosition, testSpeed,
        testBuildCost, testSellCost, null, Set.of());
        Assertions.assertEquals(testLevel, result.getLevel());
        Assertions.assertEquals(testDamage, result.getDamage());
        Assertions.assertEquals(testSpeed, result.getAttackSpeed());
        Assertions.assertEquals(testBuildCost, result.getBuildingCost());
        Assertions.assertEquals(testSellCost, result.getSellingValue());
        Assertions.assertEquals(testPosition, result.getPosition());
    }

    /**Test for setters functions.*/
    @Test
    void testSetters() {
        /**Test defense (we don't care about not settable values).*/
        final Defense result = new DefenseImpl(0, 0, null, 0, 0, 0, null, null);
        /**Test setters */
        final LogicalPosition testPosition = new LogicalPosition(20, 20);
        result.setPosition(testPosition);
        Assertions.assertEquals(testPosition, result.getPosition());
    }
}
