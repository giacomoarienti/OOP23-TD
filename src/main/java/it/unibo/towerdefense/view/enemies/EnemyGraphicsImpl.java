package it.unibo.towerdefense.view.enemies;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.json.JSONException;
import org.json.JSONObject;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyInfo;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyLevel;
import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.utils.file.FileUtils;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;
import it.unibo.towerdefense.view.graphics.ImageDrawable;

/**
 * {@inheritDoc}.
 */
class EnemyGraphicsImpl implements EnemyGraphics {

    /**
     * The folder containing all sprites.
     */
    private static final String ROOT = "it/unibo/towerdefense/view/enemies/";
    /**
     * Extension of the sprites' files.
     */
    private static final String EXTENSION = ".png";
    /**
     * Name of the file containing sizes configuration.
     */
    private static final String SIZES_FILE = "sizes.json";
    /**
     * The amount of hp corresponding to an increment (a segment) of the hp bar.
     * MUST be dividend of EnemyInfo.HP_SCALE.
     */
    private static final int HP_INCREMENT = 25;
    /**
     * The scale for hp bar sprite.
     */
    private static final double HP_BAR_SCALE = 1.3;
    private final Double[][] sizes; // [level][type]
    private final BufferedImage[] healthBars;
    private final BufferedImage[][][] enemiesSprites; // list of [level][type][direction]

    /**
     * Constructor for the class.
     *
     * Loads the images to use for representing the enemies and organizes them in
     * fast access data structures.
     *
     * @param loader the ImageLoader to use
     */
    EnemyGraphicsImpl(final ImageLoader loader) {
        enemiesSprites = new BufferedImage[EnemyLevel.values().length][EnemyArchetype.values().length][Direction
                .values().length];
        sizes = new Double[EnemyLevel.values().length][EnemyArchetype.values().length];
        try {
            JSONObject sizesConfigVals = new JSONObject(
                    FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + SIZES_FILE).toURI())));
            Arrays.stream(EnemyLevel.values())
                    .forEach(level -> Arrays.stream(EnemyArchetype.values())
                            .forEach(type -> sizes[level.ordinal()][type.ordinal()] = sizesConfigVals
                                    .getDouble(level.name()) * sizesConfigVals.getDouble(type.name())));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(
                    "Couldn't locate configuration file for enemy sizes with name: " + ROOT + SIZES_FILE, e);
        } catch (JSONException e) {
            throw new RuntimeException("Configuration file for enemy sizes is ill-formatted.", e);
        }

        EnemyType.getEnemyTypes().parallelStream().forEach(et -> {
            Arrays.stream(Direction.values()).forEach(d -> {
                try {
                    enemiesSprites[et.level().ordinal()][et.type().ordinal()][d.ordinal()] = loader.loadImage(
                            // name for the sprite of an enemy is [archetype letter][direction
                            // letter].[extension]
                            ROOT + et.type().name() + d.name() + EXTENSION,
                            sizes[et.level().ordinal()][et.type().ordinal()]);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to initialize the image for type " + et.toString()
                            + " and direction " + d.toString(), e);
                }
            });
        });

        healthBars = new BufferedImage[(EnemyInfo.HP_SCALE / HP_INCREMENT) + 1];
        IntStream.range(0, EnemyInfo.HP_SCALE + 1).filter(i -> i % HP_INCREMENT == 0).forEach(v -> {
            try {
                healthBars[v / HP_INCREMENT] = loader.loadImage(ROOT + String.valueOf(v) + EXTENSION, HP_BAR_SCALE);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize the image for health value " + String.valueOf(v), e);
            }
        });
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Stream<ImageDrawable> getDrawablesFor(final EnemyInfo e) {
        return Stream.of(
                new ImageDrawable(enemiesSprites[e.type().level().ordinal()][e.type().type().ordinal()][e.pos().getDir()
                        .ordinal()], e.pos()),
                new ImageDrawable(healthBars[e.hp() / HP_INCREMENT], e.pos()));
    }
}
