package it.unibo.towerdefense.views.enemies;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.enemies.EnemyType;
import it.unibo.towerdefense.utils.images.ImageLoader;
import it.unibo.towerdefense.views.graphics.GameRenderer;
import it.unibo.towerdefense.views.graphics.ImageDrawable;

/**
 * {@InheritDoc}.
 */
public class EnemyRendererImpl implements EnemyRenderer {

    private Map<EnemyType, BufferedImage> images;

    public EnemyRendererImpl(final ImageLoader loader){
    }

    @Override
    public void render(GameRenderer gameRenderer, List<EnemyInfo> enemies) {
        gameRenderer.submitAllToCanvas(enemies.stream().map( e -> getDrawable(e)).toList());
    }

    private ImageDrawable getDrawable(EnemyInfo enemy){
        return new ImageDrawable(images.get(enemy.type()), enemy.pos());
    }
}
