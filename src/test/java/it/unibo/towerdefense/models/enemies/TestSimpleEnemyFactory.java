package it.unibo.towerdefense.models.enemies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyLevel;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.utils.patterns.Observer;

/**
 * Tests for SimpleEnemyFactory.
 */
public class TestSimpleEnemyFactory {

    private SimpleEnemyFactory tested;
    private RichEnemyType t;
    private RichEnemy created;

    private static final LogicalPosition STARTING_POS = new LogicalPosition(0, 0);

    /**
     * Initializes the class for testing.
     */
    @BeforeEach
    void init() {
        tested = new SimpleEnemyFactory(STARTING_POS, Direction.EAST);
        t = TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100, 10000);
        created = tested.spawn(t);
    }

    /**
     * Tests the spawned enemies behave as they should.
     */
    @Test
    void testSpawn() {
        Assertions.assertEquals(STARTING_POS, created.getPosition());
        Assertions.assertEquals(t.getMaxHP(), created.getHp());
        Assertions.assertEquals(t.getMaxHP(), created.info().hp());
        Assertions.assertEquals(t.getSpeed(), created.getSpeed());
        Assertions.assertEquals(t.level(), created.info().type().level());
        Assertions.assertEquals(t.type(), created.info().type().type());
    }

    /**
     * Tests the enemy moves correctly.
     */
    @Test
    void testMove(){
        LogicalPosition newPos = STARTING_POS.clone();
        newPos.add(new LogicalPosition(10, 0));
        Direction newDir = Direction.fromAToB(STARTING_POS, newPos);
        created.move(newPos, newDir);
        Assertions.assertEquals(newPos, created.getPosition());
        Assertions.assertEquals(newPos, created.info().pos());
        Assertions.assertEquals(newDir, created.info().direction());
    }

    /**
     * Tests hurt throws when provided a negative integer.
     */
    @Test
    void testNegativeHurt() {
        Assertions.assertThrows(RuntimeException.class, () -> created.hurt(-1));
    }

    /**
     * Tests enemies correctly report their death and will not allow to be hurt or
     * moved when dead.
     */
    @Test
    void testDeath() {
        interface TestObserver<T> extends Observer<T> {
            public boolean getFlag();
        };

        TestObserver<RichEnemy> o = new TestObserver<RichEnemy>() {
            boolean flag = false;

            @Override
            public void notify(RichEnemy source) {
                if (source == created) {
                    flag = true;
                }
            }

            public boolean getFlag() {
                return flag;
            }
        };

        created.addDeathObserver(o);
        created.hurt(t.getMaxHP() / 2 + 1);
        Assertions.assertFalse(o.getFlag());
        created.hurt(t.getMaxHP() / 2 + 1);
        Assertions.assertTrue(o.getFlag());
        Assertions.assertTrue(created.isDead());
        Assertions.assertThrows(IllegalStateException.class, () -> created.hurt(1));
        Assertions.assertThrows(IllegalStateException.class, () -> created.move(new LogicalPosition(1, 1), Direction.EAST));
    }
}
