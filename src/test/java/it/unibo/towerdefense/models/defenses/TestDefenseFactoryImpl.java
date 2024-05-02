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
    private static final String ARCHER_TEST_PATH2 = "src/test/resources/defenses/Archer/TestArcherTowerUp.json";
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

    /**Test building of archer tower.*/
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

        /**Test getters using save file constructor.*/
        Defense tower = factory.defenseFromSaveFile(ARCHER_TEST_PATH1);
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
        Assertions.assertThrowsExactly(NoSuchFileException.class, () -> factory.defenseFromSaveFile("src/unexistent"));
    }
}
