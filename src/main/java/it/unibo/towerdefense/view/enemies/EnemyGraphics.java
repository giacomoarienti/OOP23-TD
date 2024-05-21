package it.unibo.towerdefense.view.enemies;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.view.graphics.ImageDrawable;

public interface EnemyGraphics {
    Stream<ImageDrawable> getDrawablesFor(EnemyInfo e);
}
