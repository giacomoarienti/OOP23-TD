package it.unibo.towerdefense.model.defenses;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Set;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.utils.file.FileUtils;

public class TestDefenseFactoryImpl {
    private DefenseFactory factory = new DefenseFactoryImpl();

    /**Files paths.*/
    private static final String ARCHER_TEST_PATH =
    "src/test/resources/it/unibo/towerdefense/models/defenses/Archer/TestArcherTower.json";
    private static final String BOMB_TEST_PATH =
    "src/test/resources/it/unibo/towerdefense/models/defenses/Bomb/TestBombTower.json";
    private static final String WIZARD_TEST_PATH =
    "src/test/resources/it/unibo/towerdefense/models/defenses/Wizard/TestWizardTower.json";

    @BeforeEach
    /**set up some collections.*/
    void setUp() {

    }

    /**Test building from save file.*/
    @Test
    void testFromSaveFile() throws IOException {
        /**Expected values for getters.*/
        final int expectedLevel = 1;
        final DefenseType expectedType = DefenseType.ARCHERTOWER;
        final int expectedDamage = 5;
        final int expectedSpeed = 3;
        final int expectedRange = 7;
        final int expectedBuildCost = 20;
        final int expectedSellCost = 10;
        final LogicalPosition expectedPosition = new LogicalPosition(10, 10);

        /**Test getters using save file.*/
        Defense tower = factory.defenseFromJsonSave(new JSONObject(FileUtils.readFile(ARCHER_TEST_PATH)).toString());
        Assertions.assertEquals(expectedLevel, tower.getLevel());
        Assertions.assertEquals(expectedType, tower.getType());
        Assertions.assertEquals(expectedDamage, tower.getDamage());
        Assertions.assertEquals(expectedSpeed, tower.getAttackSpeed());
        Assertions.assertEquals(expectedRange, tower.getRange());
        Assertions.assertEquals(expectedBuildCost, tower.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, tower.getSellingValue());
        Assertions.assertEquals(expectedPosition, tower.getPosition());
        Assertions.assertEquals(Set.of(), tower.getPossibleUpgrades());
    }

    /**Test building from stat file.*/
    @Test
    void testFromStatFile() throws IOException {
        /**Expected values for getters.*/
        final int expectedLevel = 1;
        final DefenseType expectedType = DefenseType.BOMBTOWER;
        final int expectedDamage = 8;
        final int expectedSpeed = 5;
        final int expectedRange = 10;
        final int expectedBuildCost = 25;
        final int expectedSellCost = 11;
        final LogicalPosition expectedPosition = new LogicalPosition(15, 15);
        /**expected value for upgrade */
        final int expectedLevelUp = 2;
        final DefenseType expectedTypeUp = DefenseType.BOMBTOWER;
        final int expectedDamageUp = 20;
        final int expectedSpeedUp = 11;
        final int expectedRangeUp = 15;
        final int expectedBuildCostUp = 35;
        final int expectedSellCostUp = 21;
        final LogicalPosition expectedPositionUp = new LogicalPosition(15, 15);

        /**Test getters using save file constructor.*/
        Defense tower = factory.levelOneDefense(BOMB_TEST_PATH, expectedPosition, Optional.empty());
        Assertions.assertEquals(expectedLevel, tower.getLevel());
        Assertions.assertEquals(expectedType, tower.getType());
        Assertions.assertEquals(expectedDamage, tower.getDamage());
        Assertions.assertEquals(expectedSpeed, tower.getAttackSpeed());
        Assertions.assertEquals(expectedRange, tower.getRange());
        Assertions.assertEquals(expectedBuildCost, tower.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, tower.getSellingValue());
        Assertions.assertEquals(expectedPosition, tower.getPosition());
        Assertions.assertEquals(1, tower.getPossibleUpgrades().size());
        /**Test upgrade.*/
        Defense upgrade = tower.getPossibleUpgrades().stream().toList().get(0);
        Assertions.assertEquals(expectedLevelUp, upgrade.getLevel());
        Assertions.assertEquals(expectedTypeUp, upgrade.getType());
        Assertions.assertEquals(expectedDamageUp, upgrade.getDamage());
        Assertions.assertEquals(expectedSpeedUp, upgrade.getAttackSpeed());
        Assertions.assertEquals(expectedRangeUp, upgrade.getRange());
        Assertions.assertEquals(expectedBuildCostUp, upgrade.getBuildingCost());
        Assertions.assertEquals(expectedSellCostUp, upgrade.getSellingValue());
        Assertions.assertEquals(expectedPositionUp, upgrade.getPosition());
        Assertions.assertEquals(Set.of(), upgrade.getPossibleUpgrades());


        /**Test exception thrown.*/
        Assertions.assertThrowsExactly(NoSuchFileException.class, () ->
        factory.levelOneDefense("src/unexistent", null, Optional.empty()));
    }

    /**Test upgrade method.*/
    @Test
    void testUpgrade() throws IOException {
        /**Expected values for getters.*/
        final int expectedLevel = 2;
        final DefenseType expectedType = DefenseType.WIZARDTOWER;
        final int expectedDamage = 10;
        final int expectedSpeed = 4;
        final int expectedRange = 8;
        final int expectedBuildCost = 31;
        final int expectedSellCost = 18;

        /**Build upgrade.*/
        final LogicalPosition testPosition = new LogicalPosition(8, 5);
        Defense baseDef = factory.levelOneDefense(WIZARD_TEST_PATH, testPosition, Optional.empty());
        Defense up  = factory.upgrade(baseDef, 0, Optional.empty());

        /**Test getters.*/
        Assertions.assertEquals(expectedLevel, up.getLevel());
        Assertions.assertEquals(expectedType, up.getType());
        Assertions.assertEquals(expectedDamage, up.getDamage());
        Assertions.assertEquals(expectedRange, up.getRange());
        Assertions.assertEquals(expectedSpeed, up.getAttackSpeed());
        Assertions.assertEquals(expectedBuildCost, up.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, up.getSellingValue());
        Assertions.assertEquals(baseDef.getPosition(), up.getPosition());
        Assertions.assertNotNull(up.getStrategy());
    }
}
