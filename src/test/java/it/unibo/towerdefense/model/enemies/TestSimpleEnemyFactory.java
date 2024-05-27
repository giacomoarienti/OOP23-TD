package it.unibo.towerdefense.model.enemies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyLevel;
import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * Tests for SimpleEnemyFactory.
 */
class TestSimpleEnemyFactory {
    private RichEnemyType t;
    private RichEnemy created;

    private static final EnemyPosition STARTING_POSITION = new EnemyPosition(0, 0, Direction.E, 100);

    /**
     * Initializes the class for testing.
     */
    @BeforeEach
    void init() {
        final SimpleEnemyFactory tested = new SimpleEnemyFactory();
        final int val = 100;
        t = TestingEnemyType.build(EnemyLevel.I, EnemyArchetype.A, val, val, val * val, val * val);
        created = tested.spawn(t, STARTING_POSITION);
    }

    /**
     * Tests the spawned enemies behave as they should.
     */
    @Test
    void testSpawn() {
        Assertions.assertEquals(STARTING_POSITION, created.getPosition());
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
    void testMove() {
        final EnemyPosition newPos = new EnemyPosition(10, 0, Direction.E, 100);
        created.move(newPos);
        Assertions.assertEquals(newPos, created.getPosition());
        Assertions.assertEquals(newPos, created.info().pos());
        Assertions.assertEquals(newPos.getDir(), created.getPosition().getDir());
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
            boolean isFlagged();
        }

        final TestObserver<RichEnemy> o = new TestObserver<RichEnemy>() {
            private boolean flag;

            @Override
            public void notify(final RichEnemy source) {
                if (source.equals(created)) {
                    flag = true;
                }
            }

            @Override
            public boolean isFlagged() {
                return flag;
            }
        };

        created.addDeathObserver(o);
        created.hurt(t.getMaxHP() / 2 + 1);
        Assertions.assertFalse(o.isFlagged());
        created.hurt(t.getMaxHP() / 2 + 1);
        Assertions.assertTrue(o.isFlagged());
        Assertions.assertTrue(created.isDead());
        Assertions.assertThrows(IllegalStateException.class, () -> created.hurt(1));
        Assertions.assertThrows(IllegalStateException.class,
                () -> created.move(new EnemyPosition(10, 0, Direction.E, 100)));
    }
}
