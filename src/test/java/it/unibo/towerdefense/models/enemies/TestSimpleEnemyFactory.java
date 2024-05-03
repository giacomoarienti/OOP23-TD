package it.unibo.towerdefense.models.enemies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;
import it.unibo.towerdefense.utils.patterns.Observer;

public class TestSimpleEnemyFactory {

    private SimpleEnemyFactory tested;

    private static final LogicalPosition STARTING_POS = new LogicalPosition(0, 0);

    @BeforeEach
    void init(){
        tested = new SimpleEnemyFactory(STARTING_POS);
    }

    @Test
    void testSpawn() {
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Enemy created = tested.spawn(t);
        Assertions.assertEquals(STARTING_POS, created.getPosition());
        Assertions.assertEquals(t.getMaxHP(), created.getHp());
        Assertions.assertEquals(t.getSpeed(), created.getSpeed());
        Assertions.assertEquals(t.level(), created.info().type().level());
        Assertions.assertEquals(t.type(), created.info().type().type());
    }

    @Test
    void testNegativeHurt(){
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Enemy created = tested.spawn(t);
        Assertions.assertThrows(RuntimeException.class, () -> created.hurt(-1));
    }

    @Test
    void testDeath(){
        RichEnemyType t = new TestEnemyType(EnemyLevel.I, EnemyArchetype.A, 100, 100, 100);
        Enemy created = tested.spawn(t);

        interface TestObserver<T> extends Observer<T>{
            public boolean getFlag();
        };
        TestObserver<Enemy> o = new TestObserver<Enemy>() {
            boolean flag = false;
            @Override
            public void notify(Enemy source) {
                if(source == created){
                    flag = true;
                }
            }
            public boolean getFlag(){
                return flag;
            }
        };

        created.addDeathObserver(o);
        created.hurt(t.getMaxHP()/2 + 1);
        Assertions.assertFalse(o.getFlag());
        created.hurt(t.getMaxHP()/2 + 1);
        Assertions.assertTrue(o.getFlag());
        Assertions.assertThrows(IllegalStateException.class, () -> created.hurt(1));
    }
}
