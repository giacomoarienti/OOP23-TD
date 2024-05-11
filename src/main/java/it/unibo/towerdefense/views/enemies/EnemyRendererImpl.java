package it.unibo.towerdefense.views.enemies;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import it.unibo.towerdefense.commons.graphics.GameRenderer;
import it.unibo.towerdefense.commons.graphics.ImageDrawable;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.enemies.EnemyType;
import it.unibo.towerdefense.utils.images.ImageLoader;

/**
 * {@InheritDoc}.
 */
public class EnemyRendererImpl implements EnemyRenderer {

    private final static String ROOT = "it/unibo/towerdefense/views/enemies/";
    private final static String EXTENSION = ".png";
    private Map<EnemyType, BufferedImage> images;


    public static void main(String[] args){
        new EnemyRendererImpl(new ImageLoader(1000));
    }

    public EnemyRendererImpl(final ImageLoader loader) {
        EnemyType.getEnemyTypes().forEach(et -> {
            try {
                images.put(et, loader.loadImage(ROOT + et.toString() + EXTENSION, 1));
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize the image for type " + et.toString(), e);
            }
        });
    }

    @Override
    public void render(GameRenderer gameRenderer, List<EnemyInfo> enemies) {
        gameRenderer.submitAllToCanvas(enemies.stream().map(e -> getDrawable(e)).toList());
    }

    private ImageDrawable getDrawable(EnemyInfo enemy) {
        return new ImageDrawable(images.get(enemy.type()), enemy.pos());
    }
}
