package it.unibo.towerdefense.models.defenses;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.defenses.costants.DefenseMapKeys;

import java.util.Set;

/**Tests for the implementation of Defense.*/
public class TestDefenseImpl {
    /**Test of constructors and get methods.*/
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

    /**Test for json conversion (from defense to json).*/
    @Test
    void testToJson() {
        /**Test values.*/
        final int testDamage = 10;
        final int testLevel = 1;
        final int testSpeed = 5;
        final int testBuildCost = 12;
        final int testSellCost = 5;
        final LogicalPosition testPosition = new LogicalPosition(10, 10);
        Defense result = new DefenseImpl(testDamage, testLevel, testPosition, testSpeed,
        testBuildCost, testSellCost, null, Set.of());
        /**Create json object*/
        JSONObject jsonVersion = new JSONObject();
        jsonVersion.put(DefenseMapKeys.LEVEL, testLevel);
        jsonVersion.put(DefenseMapKeys.DAMAGE, testDamage);
        jsonVersion.put(DefenseMapKeys.SPEED, testSpeed);
        jsonVersion.put(DefenseMapKeys.BUILDING_COST, testBuildCost);
        jsonVersion.put(DefenseMapKeys.SELLING_COST, testSellCost);
        jsonVersion.put(DefenseMapKeys.POSITION, testPosition.toJSON());
        jsonVersion.put(DefenseMapKeys.UPGRADES, Set.of());
        /**Assertions.*/
        Assertions.assertEquals(result.toJSON(), jsonVersion.toString());
    }

    /**Test for json conversion (from json to defense).*/
    @Test
    void testFromJson() {
        /**Test values.*/
        final int testDamage = 10;
        final int testLevel = 1;
        final int testSpeed = 5;
        final int testBuildCost = 12;
        final int testSellCost = 5;
        final LogicalPosition testPosition = new LogicalPosition(10, 10);
        /**Create json object*/
        JSONObject jsonVersion = new JSONObject();
        jsonVersion.put(DefenseMapKeys.LEVEL, testLevel);
        jsonVersion.put(DefenseMapKeys.DAMAGE, testDamage);
        jsonVersion.put(DefenseMapKeys.SPEED, testSpeed);
        jsonVersion.put(DefenseMapKeys.BUILDING_COST, testBuildCost);
        jsonVersion.put(DefenseMapKeys.SELLING_COST, testSellCost);
        jsonVersion.put(DefenseMapKeys.POSITION, testPosition.toJSON());
        jsonVersion.put(DefenseMapKeys.UPGRADES, Set.of());
        /**Test */
        Defense result = DefenseImpl.fromJson(jsonVersion.toString());
        /**Assertions.*/
        Assertions.assertEquals(result.getLevel(), testLevel);
        Assertions.assertEquals(result.getDamage(), testDamage);
        Assertions.assertEquals(result.getAttackSpeed(), testSpeed);
        Assertions.assertEquals(result.getBuildingCost(), testBuildCost);
        Assertions.assertEquals(result.getSellingValue(), testSellCost);
        Assertions.assertEquals(result.getPosition(), testPosition);
    }
}
