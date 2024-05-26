package it.unibo.towerdefense.view.enemies;

import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.view.graphics.ImageDrawable;

/**
 * Class responsible for managing the way enemies are shown.
 */
public interface EnemyGraphics {
    /**
     * Returns a stream of ImageDrawables which represent the enemy correspondint to
     * the given DTO.
     *
     * @param e the DTO with information about the enemy to render
     * @return a stream of ImageDrawables for the enemy
     */
    Stream<ImageDrawable> getDrawablesFor(EnemyInfo e);
}
