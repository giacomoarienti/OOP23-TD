package it.unibo.towerdefense.views.enemies;

import java.awt.Image;

import java.util.List;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.enemies.EnemyType;
import it.unibo.towerdefense.utils.images.ImageLoader;
import it.unibo.towerdefense.views.graphics.GameRenderer;
import it.unibo.towerdefense.views.graphics.ImageDrawable;

/**
 * {@InheritDoc}.
 */
public class EnemyRendererImpl implements EnemyRenderer {

    public EnemyRendererImpl(final ImageLoader loader){
    }

    @Override
    public void render(GameRenderer gameRenderer, List<EnemyInfo> enemies) {
        gameRenderer.submitAllToCanvas(enemies.stream().map( e -> getDrawable(e)).toList());
    }

    private ImageDrawable getDrawable(EnemyInfo enemy){
        return new ImageDrawable(getImage(enemy.type()), enemy.pos());
    }

    private Image getImage(EnemyType t){
        throw new UnsupportedOperationException();
    }
}
