package it.unibo.towerdefense.models.defenses;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Set;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.defenses.DefenseType;

public class TestDefenseFactoryImpl {
    private DefenseFactory factory = new DefenseFactoryImpl();

    /**Files paths.*/
    private static final String ARCHER_TEST_PATH1 = "src/test/resources/defenses/Archer/TestArcherTower1.json";
    private static final String ARCHER_TEST_PATH2 = "src/test/resources/defenses/Archer/TestArcherTower2.json";
    private static final String ARCHER_TEST_PATH3 = "src/test/resources/defenses/Archer/TestArcherTowerUp.json";
    private static final String BOMB_TEST_PATH1 = "src/test/resources/defenses/Bomb/TestBombTower1.json";
    private static final String BOMB_TEST_PATH2 = "src/test/resources/defenses/Bomb/TestBombTower2.json";
    private static final String BOMB_TEST_PATH3 = "src/test/resources/defenses/Bomb/TestBombTowerUp.json";
    private static final String WIZARD_TEST_PATH1 = "src/test/resources/defenses/Wizard/TestWizardTower1.json";
    private static final String WIZARD_TEST_PATH2 = "src/test/resources/defenses/Wizard/TestWizardTower2.json";
    private static final String WIZARD_TEST_PATH3 = "src/test/resources/defenses/Wizard/TestWizardTowerUp.json";

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
        Defense tower = factory.archerTowerFromSaveFile(ARCHER_TEST_PATH1);
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
        tower = factory.newArcherTowerFrom(ARCHER_TEST_PATH2, Optional.empty(),expectedPosition);
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
        Assertions.assertThrowsExactly(NoSuchFileException.class, () -> factory.archerTowerFromSaveFile("src/unexistent"));

        /**Test with upgrades.*/
        tower = factory.newArcherTowerFrom(ARCHER_TEST_PATH1, Optional.of(ARCHER_TEST_PATH3), expectedPosition);
        Assertions.assertEquals(tower.getPossibleUpgrades().size(), 2);
    }

    /**Test the bomber tower methods.*/
    @Test
    void testBomberTower() throws IOException {
        /**Expected values for getters.*/
        final int expectedLevel = 2;
        final DefenseType expectedType = DefenseType.BOMBTOWER;
        final int expectedDamage = 5;
        final int expectedSpeed = 3;
        final int expectedRange = 7;
        final int expectedBuildCost = 20;
        final int expectedSellCost = 10;
        final LogicalPosition expectedPosition = new LogicalPosition(10, 10);

        /**Test getters using save file constructor.*/
        Defense tower = factory.bomberTowerFromSaveFile(BOMB_TEST_PATH1);
        Assertions.assertEquals(expectedLevel, tower.getLevel());
        Assertions.assertEquals(expectedType, tower.getType());
        Assertions.assertEquals(expectedDamage, tower.getDamage());
        Assertions.assertEquals(expectedSpeed, tower.getAttackSpeed());
        Assertions.assertEquals(expectedRange, tower.getRange());
        Assertions.assertEquals(expectedBuildCost, tower.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, tower.getSellingValue());
        Assertions.assertEquals(expectedPosition, tower.getPosition());
        Assertions.assertEquals(tower.getPossibleUpgrades().size(), 1);
        /**check that upgrade got loaded correctly*/
        Defense upgrade = tower.getPossibleUpgrades().stream().findFirst().get();
        /**Expected values for getters.*/
        final int expectedLevelUp = 3;
        final DefenseType expectedTypeUp = DefenseType.BOMBTOWER;
        final int expectedDamageUp = 7;
        final int expectedSpeedUp = 4;
        final int expectedRangeUp = 8;
        final int expectedBuildCostUp = 35;
        final int expectedSellCostUp = 21;

        /**Test getters of upgrade*/
        Assertions.assertEquals(expectedLevelUp, upgrade.getLevel());
        Assertions.assertEquals(expectedTypeUp, upgrade.getType());
        Assertions.assertEquals(expectedDamageUp, upgrade.getDamage());
        Assertions.assertEquals(expectedSpeedUp, upgrade.getAttackSpeed());
        Assertions.assertEquals(expectedRangeUp, upgrade.getRange());
        Assertions.assertEquals(expectedBuildCostUp, upgrade.getBuildingCost());
        Assertions.assertEquals(expectedSellCostUp, upgrade.getSellingValue());
        Assertions.assertEquals(Set.of(), upgrade.getPossibleUpgrades());

        /**Test lv1 constructor.*/
        tower = factory.newBomberTower(BOMB_TEST_PATH2, Optional.empty(),expectedPosition);
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
        Assertions.assertThrowsExactly(NoSuchFileException.class, () -> factory.bomberTowerFromSaveFile("fakeFile"));

        /**Test with upgrades.*/
        tower = factory.newBomberTower(BOMB_TEST_PATH2, Optional.of(BOMB_TEST_PATH3), expectedPosition);
        Assertions.assertEquals(tower.getPossibleUpgrades().size(), 1);
    }

    /**Tests for wizard tower.*/
    @Test
    void testWizardTower() throws IOException{
        /**Expected values for getters.*/
        final int expectedLevel = 1;
        final DefenseType expectedType = DefenseType.WIZARDTOWER;
        final int expectedDamage = 5;
        final int expectedSpeed = 3;
        final int expectedRange = 7;
        final int expectedBuildCost = 20;
        final int expectedSellCost = 10;
        final LogicalPosition expectedPosition = new LogicalPosition(10, 10);
        final int expectedNumberOfHitEntites = 2;
        final Pair<LogicalPosition,Integer> testPair = new ImmutablePair<>(expectedPosition,1);

        /**Test getters using save file constructor.*/
        Defense tower = factory.wizardTowerToSaveFile(WIZARD_TEST_PATH1);
        Assertions.assertEquals(expectedLevel, tower.getLevel());
        Assertions.assertEquals(expectedType, tower.getType());
        Assertions.assertEquals(expectedDamage, tower.getDamage());
        Assertions.assertEquals(expectedSpeed, tower.getAttackSpeed());
        Assertions.assertEquals(expectedRange, tower.getRange());
        Assertions.assertEquals(expectedBuildCost, tower.getBuildingCost());
        Assertions.assertEquals(expectedSellCost, tower.getSellingValue());
        Assertions.assertEquals(expectedPosition, tower.getPosition());
        Assertions.assertEquals(Set.of(), tower.getPossibleUpgrades());

        /**Test lv1 constructor.*/
        tower = factory.newWizardTower(WIZARD_TEST_PATH2, Optional.empty(),expectedPosition);
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
         Assertions.assertThrowsExactly(NoSuchFileException.class, () -> factory.wizardTowerToSaveFile("fakeFile"));

         /**Test strategy (at level 1 only 2 targets should get hit).*/
         Assertions.assertEquals(expectedNumberOfHitEntites,
         tower.getStrategy().execute(List.of(testPair, testPair, testPair),tower.getDamage()).size());

        /**Test with upgrades.*/
        tower = factory.newWizardTower(WIZARD_TEST_PATH1, Optional.of(WIZARD_TEST_PATH3), expectedPosition);
        Assertions.assertEquals(tower.getPossibleUpgrades().size(), 1);
    }
}
