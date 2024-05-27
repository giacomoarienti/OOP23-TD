package it.unibo.towerdefense.model.defenses;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.defenses.costants.DefenseMapKeys;

import java.util.Set;
import java.util.Optional;

/**Tests for the implementation of Defense.*/
class TestDefenseImpl {
    /**Test of constructors and get methods.*/
    @Test
    void testConstructorsAndGetters() {
        /**Test values.*/
        final DefenseType testType = DefenseType.ARCHERTOWER;
        final int testLevel = 1;
        final int testDamage = 10;
        final int testRange = 3;
        final int testSpeed = 5;
        final int testBuildCost = 12;
        final int testSellCost = 5;
        final LogicalPosition testPosition = new LogicalPosition(10, 10);

        /**Test full constructor*/
        final Defense result = new DefenseImpl(testType, testLevel, testDamage, testRange,
        testSpeed, testBuildCost, testSellCost, Optional.of(testPosition), null, Set.of());
        Assertions.assertEquals(testLevel, result.getLevel());
        Assertions.assertEquals(testDamage, result.getDamage());
        Assertions.assertEquals(testSpeed, result.getAttackSpeed());
        Assertions.assertEquals(testBuildCost, result.getBuildingCost());
        Assertions.assertEquals(testSellCost, result.getSellingValue());
        Assertions.assertEquals(testPosition, result.getPosition().get());
    }

    /**Test for setters functions.*/
    @Test
    void testSetters() {
        /**Test defense (we don't care about not settable values).*/
        final Defense result = new DefenseImpl(DefenseType.ARCHERTOWER, 0, 0, 0, 0, 0, 0, 
        Optional.empty(), null, Set.of());
        /**Test setters */
        final LogicalPosition testPosition = new LogicalPosition(20, 20);
        result.setPosition(testPosition);
        Assertions.assertEquals(testPosition, result.getPosition().get());
    }

    /**Test for json conversion (from defense to json).*/
    @Test
    void testToJson() {
        /**Test values.*/
        final DefenseType testType = DefenseType.WIZARDTOWER;
        final int testLevel = 1;
        final int testDamage = 15;
        final int testRange = 3;
        final int testSpeed = 5;
        final int testBuildCost = 12;
        final int testSellCost = 4;
        final LogicalPosition testPosition = new LogicalPosition(10, 10);
        final Defense result = new DefenseImpl(testType, testLevel, testDamage, testRange,
        testSpeed, testBuildCost, testSellCost, Optional.of(testPosition), null, Set.of());
        /**Create json object*/
        final JSONObject jsonVersion = new JSONObject();
        jsonVersion.put(DefenseMapKeys.LEVEL, testLevel);
        jsonVersion.put(DefenseMapKeys.DAMAGE, testDamage);
        jsonVersion.put(DefenseMapKeys.SPEED, testSpeed);
        jsonVersion.put(DefenseMapKeys.BUILDING_COST, testBuildCost);
        jsonVersion.put(DefenseMapKeys.SELLING_COST, testSellCost);
        jsonVersion.put(DefenseMapKeys.POSITION, testPosition.toJSON());
        jsonVersion.put(DefenseMapKeys.UPGRADES, Set.of());
        jsonVersion.put(DefenseMapKeys.RANGE, testRange);
        jsonVersion.put(DefenseMapKeys.TYPE, DefenseType.WIZARDTOWER);
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
        final DefenseType testType = DefenseType.ARCHERTOWER;
        final int testSellCost = 5;
        final int testRange = 5;
        final LogicalPosition testPosition = new LogicalPosition(10, 10);
        /**Create json object*/
        final JSONObject jsonVersion = new JSONObject();
        jsonVersion.put(DefenseMapKeys.LEVEL, testLevel);
        jsonVersion.put(DefenseMapKeys.TYPE, testType);
        jsonVersion.put(DefenseMapKeys.DAMAGE, testDamage);
        jsonVersion.put(DefenseMapKeys.SPEED, testSpeed);
        jsonVersion.put(DefenseMapKeys.BUILDING_COST, testBuildCost);
        jsonVersion.put(DefenseMapKeys.SELLING_COST, testSellCost);
        jsonVersion.put(DefenseMapKeys.POSITION, testPosition.toJSON());
        jsonVersion.put(DefenseMapKeys.UPGRADES, Set.of());
        jsonVersion.put(DefenseMapKeys.RANGE, testRange);
        /**Test */
        final Defense result = DefenseImpl.fromJson(jsonVersion.toString());
        /**Assertions.*/
        Assertions.assertEquals(result.getLevel(), testLevel);
        Assertions.assertEquals(result.getDamage(), testDamage);
        Assertions.assertEquals(result.getAttackSpeed(), testSpeed);
        Assertions.assertEquals(result.getBuildingCost(), testBuildCost);
        Assertions.assertEquals(result.getSellingValue(), testSellCost);
        Assertions.assertEquals(result.getPosition().get(), testPosition);    }
}
