package it.unibo.towerdefense.models.defenses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDefenseFactoryImpl {
    private DefenseFactory factory = new DefenseFactoryImpl();
    
    /**Files paths.*/
    private static final String TEST_PATH1 = "/defenses/test_archer_tower.json";

    @BeforeEach
    /**set up some collections.*/
    void setUp() {

    }

    /**Test the archer tower method.*/
    @Test
    void testArcherTower() {
        /**Expected values for getters.*/
        final int EXPECTED_DAMAGE = 5;
        final int EXPECTED_SPEED = 3;
        final int EXPECTED_LEVEL = 1;
        final int EXPECTED_BUILD_COST = 20;
        final int EXPECTED_SELL_COST = 10;
        Defense tower = factory.archerTower(TEST_PATH1);

        /**Test getters */
        Assertions.assertEquals(EXPECTED_DAMAGE, tower.getDamage());
        Assertions.assertEquals(EXPECTED_SPEED, tower.getAttackSpeed());
        Assertions.assertEquals(EXPECTED_LEVEL, tower.getLevel());
        Assertions.assertEquals(EXPECTED_SELL_COST, tower.getSellingValue());
        Assertions.assertEquals(EXPECTED_BUILD_COST, tower.getBuildingCost());

        /**Test internal strategy */
    }
}
