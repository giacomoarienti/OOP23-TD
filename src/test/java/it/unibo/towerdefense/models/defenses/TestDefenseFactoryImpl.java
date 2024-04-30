package it.unibo.towerdefense.models.defenses;

import java.io.IOException;
import java.util.Set;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.defenses.DefenseType;

public class TestDefenseFactoryImpl {
    private DefenseFactory factory = new DefenseFactoryImpl();

    /**Files paths.*/
    private static final String TEST_PATH1 = "src/test/resources/defenses/TestArcherTower1.json";
    private static final String TEST_PATH2 = "src/test/resources/defenses/TestArcherTower2.json";

    @BeforeEach
    /**set up some collections.*/
    void setUp() {

    }

    /**Test the archer tower methods.*/
    @Test
    void testArcherTower() throws IOException {
        /**Expected values for getters.*/
        final int expectedLevel = 1;
        final DefenseType expectedType = DefenseType.ARCHERTOWER;
        final int expectedDamage = 5;
        final int expectedSpeed = 3;
        final int expectedRange = 7;
        final int expectedBuildCost = 20;
        final int expectedSellCost = 10;
        final LogicalPosition expectedPosition = new LogicalPosition(10, 10);


        /**Test getters using save file constructor.*/
        Defense tower = factory.archerTowerFromSaveFile(TEST_PATH1);
        Assertions.assertEquals(expectedLevel, tower.getLevel());
        Assertions.assertEquals(expectedType, tower.getType());
        Assertions.assertEquals(expectedDamage, tower.getDamage());
        Assertions.assertEquals(expectedSpeed, tower.getAttackSpeed());
        Assertions.assertEquals(expectedRange, tower.getRange());
        Assertions.assertEquals(expectedBuildCost, tower.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, tower.getSellingValue());
        Assertions.assertEquals(expectedPosition, tower.getPosition());
        Assertions.assertEquals(Set.of(), tower.getPossibleUpgrades());

        /**Test getters using new file constructor*/
        tower = factory.newArcherTowerFrom(TEST_PATH2, Optional.empty(),expectedPosition);
        Assertions.assertEquals(expectedLevel, tower.getLevel());
        Assertions.assertEquals(expectedType, tower.getType());
        Assertions.assertEquals(expectedDamage, tower.getDamage());
        Assertions.assertEquals(expectedSpeed, tower.getAttackSpeed());
        Assertions.assertEquals(expectedRange, tower.getRange());
        Assertions.assertEquals(expectedBuildCost, tower.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, tower.getSellingValue());
        Assertions.assertEquals(expectedPosition, tower.getPosition());
        Assertions.assertEquals(Set.of(), tower.getPossibleUpgrades());

        /**Test exception thrown.*/
        Assertions.assertThrowsExactly(IOException.class, () -> factory.archerTowerFromSaveFile(""));
        Assertions.assertThrowsExactly(IOException.class, ()
         -> factory.newArcherTowerFrom("",Optional.of(null),null));
    }

    /**Test the bomber tower methods.*/
    @Test
    void testBomberTower() throws IOException {
        /**Expected values for getters.*/
        final int expectedDamage = 5;
        final int expectedSpeed = 3;
        final int expectedLevel = 1;
        final int expectedBuildCost = 20;
        final int expectedSellCost = 10;
        Defense tower = factory.archerTowerFromSaveFile(TEST_PATH1);

        /**Test getters */
        Assertions.assertEquals(expectedDamage, tower.getDamage());
        Assertions.assertEquals(expectedSpeed, tower.getAttackSpeed());
        Assertions.assertEquals(expectedLevel, tower.getLevel());
        Assertions.assertEquals(expectedBuildCost, tower.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, tower.getSellingValue());

        /**Test internal strategy */
    }
}
