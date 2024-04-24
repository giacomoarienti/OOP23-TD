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
        final int expected_damage = 5;
        final int expected_speed = 3;
        final int expected_level = 1;
        final int expected_build_cost = 20;
        final int expected_sell_cost = 10;
        Defense tower = factory.archerTower(TEST_PATH1);

        /**Test getters */
        Assertions.assertEquals(expected_damage, tower.getDamage());
        Assertions.assertEquals(expected_speed, tower.getAttackSpeed());
        Assertions.assertEquals(expected_level, tower.getLevel());
        Assertions.assertEquals(expected_build_cost, tower.getBuildingCost());
        Assertions.assertEquals(expected_sell_cost, tower.getSellingValue());


        /**Test internal strategy */
    }
}
