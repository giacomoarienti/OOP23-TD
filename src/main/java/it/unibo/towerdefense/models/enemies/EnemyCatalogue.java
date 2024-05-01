package it.unibo.towerdefense.models.enemies;

import java.util.List;

import java.util.function.Predicate;

public interface EnemyCatalogue {
    public List<RichEnemyType> getEnemyTypes();
    public List<RichEnemyType> getEnemyTypes(Predicate<? super RichEnemyType> test);
}
