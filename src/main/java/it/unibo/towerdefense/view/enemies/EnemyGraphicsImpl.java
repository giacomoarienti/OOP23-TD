package it.unibo.towerdefense.view.enemies;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
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

class EnemyGraphicsImpl implements EnemyGraphics {

    private final static String ROOT = "it/unibo/towerdefense/view/enemies/";
    private final static String EXTENSION = ".png";
    private final static String SIZES_FILE = "sizes.json";
    private final static int HP_INCREMENT = 25; //MUST be dividend of EnemyInfo.HP_SCALE
    private final static double HP_BAR_SCALE = 1.3;
    private final Double[][] sizes; // [level][type]
    private final BufferedImage[] healthBars;
    private final BufferedImage[][][] enemiesSprites; //list of [level][type][direction]

    EnemyGraphicsImpl(final ImageLoader loader){
        enemiesSprites = new BufferedImage[EnemyLevel.values().length][EnemyArchetype.values().length][Direction.values().length];
        sizes = new Double[EnemyLevel.values().length][EnemyArchetype.values().length];
        try{
            JSONObject sizesConfigVals = new JSONObject(FileUtils.readFile(Paths.get(ClassLoader.getSystemResource(ROOT + SIZES_FILE).toURI())));
            Arrays.stream(EnemyLevel.values())
            .forEach(level -> Arrays.stream(EnemyArchetype.values())
                .forEach(type -> sizes[level.ordinal()][type.ordinal()] = sizesConfigVals.getDouble(level.name()) * sizesConfigVals.getDouble(type.name())
            ));
        } catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Couldn't locate configuration file for enemy sizes with name: " + ROOT + SIZES_FILE, e);
        } catch(JSONException e) {
            throw new RuntimeException("Configuration file for enemy sizes is ill-formatted.", e);
        }

        EnemyType.getEnemyTypes().parallelStream().forEach(et -> {
            try {
                BufferedImage source = loader.loadImage(ROOT + et.toString() + EXTENSION, sizes[et.level().ordinal()][et.type().ordinal()]);
                BufferedImage[] sprites = enemiesSprites[et.level().ordinal()][et.type().ordinal()];
                sprites[Direction.N.ordinal()] = source;
                sprites[Direction.E.ordinal()] = Scalr.rotate(source, Rotation.CW_90);
                sprites[Direction.S.ordinal()] = Scalr.rotate(source, Rotation.CW_180);
                sprites[Direction.W.ordinal()] = Scalr.rotate(source, Rotation.CW_270);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize the image for type " + et.toString(), e);
            }
        });

        healthBars = new BufferedImage[(EnemyInfo.HP_SCALE/HP_INCREMENT) + 1];
        IntStream.range(0, EnemyInfo.HP_SCALE + 1).filter(i -> i % HP_INCREMENT == 0).forEach(v -> {
            try{
                healthBars[v / 25] = loader.loadImage(ROOT + String.valueOf(v) + EXTENSION, HP_BAR_SCALE);
            }catch (Exception e) {
                throw new RuntimeException("Failed to initialize the image for health value " + String.valueOf(v), e);
            }
        });
    }

    @Override
    public Stream<ImageDrawable> getDrawablesFor(final EnemyInfo e) {
        return Stream.of(
            new ImageDrawable(enemiesSprites[e.type().level().ordinal()][e.type().type().ordinal()][e.pos().getDir().ordinal()], e.pos()),
            new ImageDrawable(healthBars[e.hp() / HP_INCREMENT], e.pos())
        );
    }
}
