package it.unibo.towerdefense.view.enemies;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.ImageDrawable;

/**
 * {@InheritDoc}.
 */
public class EnemyRendererImpl implements EnemyRenderer {

    private final static List<Integer> HEALTH_VALUES = List.of(25, 50, 75, 100);
    private final static double HEALTH_BAR_SCALE = 1.0;
    private final static String ROOT = "it/unibo/towerdefense/view/enemies/";
    private final static String EXTENSION = ".png";
    private Map<EnemyType, Double> sizes;
    private Map<EnemyType, List<BufferedImage>> images;
    private SortedMap<Integer, BufferedImage> healthBars;
    private final Renderer renderer;

    /**
     * Loads all the images from disk and saves them in a structure for quick access.
     *
     * @param loader the ImageLoader to load the images.
     */
    public EnemyRendererImpl(final Renderer renderer) {
        this.renderer = renderer;
        ImageLoader loader = renderer.getImageLoader();
        images = new HashMap<>();
        sizes = EnemyType.getEnemyTypes().stream().collect(Collectors.toMap(et -> et, et -> 1.0));
        EnemyType.getEnemyTypes().parallelStream().forEach(et -> {
            try {
                BufferedImage source = loader.loadImage(ROOT + et.toString() + EXTENSION, sizes.get(et));
                images.put(et, List.of(
                    Scalr.rotate(source, Rotation.CW_90), //E
                    source, //N
                    Scalr.rotate(source, Rotation.CW_270), //W
                    Scalr.rotate(source, Rotation.CW_180) //S
                ));
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize the image for type " + et.toString(), e);
            }
        });
        healthBars = new TreeMap<>();
        HEALTH_VALUES.forEach(v -> {
            try{
                healthBars.put(v, loader.loadImage(ROOT + v.toString() + EXTENSION, HEALTH_BAR_SCALE));
            }catch (Exception e) {
                throw new RuntimeException("Failed to initialize the image for health value " + v, e);
            }
        });
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void render(Stream<EnemyInfo> enemies) {
        renderer.submitAllToCanvas(enemies
            .parallel()
            .sorted((e1, e2) -> Long.compare(e1.pos().getDistanceFromStart(), e2.pos().getDistanceFromStart()))
            .flatMap(e -> Stream.of(getDrawable(e), getHealthBar(e)))
            .toList());
    }

    /**
     * Returns the correct image to render for a given enemy.
     */
    private ImageDrawable getDrawable(EnemyInfo enemy) {
        return new ImageDrawable(images.get(enemy.type()).get(enemy.pos().getDir().ordinal()), enemy.pos());
    }

    private ImageDrawable getHealthBar(EnemyInfo enemy) {
        return new ImageDrawable(healthBars.get(healthBars.tailMap(enemy.hp()).firstKey()), enemy.pos());
    }
}
