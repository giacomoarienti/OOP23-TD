package it.unibo.towerdefense.view.map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;

import java.awt.image.BufferedImage;

import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.ImageDrawable;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;

/**
 * Class that implements MapRenderer with images from specific files.
 */
public class MapRendererImpl implements MapRenderer {

    private final static String ROOT = "it/unibo/towerdefense/view/map/";
    private final static String EXTENSION = ".png";
    private final static List<String> NAMES = List.of("straight", "curve", "buildable", "obstacle", "selected");
    private final List<BufferedImage> images = new ArrayList<>();

    public MapRendererImpl(final ImageLoader imLo) {
        for (int i = 0; i < NAMES.size(); i++) {
            try {
            images.add(imLo.loadImage(ROOT + NAMES.get(i) + EXTENSION, 1.0));
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize the image for type " + NAMES.get(i), e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Renderer renderer, final Stream<CellInfo> mapInfo) {
        final var mapDrawables = mapInfo.map(c -> new ImageDrawable(getImage(c), c.getPosition())).toList();
        renderer.submitBackgroundAllToCanvas(mapDrawables);
    }


    private BufferedImage getImage(final CellInfo c) {
        return c.isPathCell() ? path(c.getDirections().getLeft(), c.getDirections().getRight()) : buildable(c);
    }

    private BufferedImage buildable(final CellInfo c) {
        return !c.isBuildable() ? images.get(3) : c.isSelected() ? images.get(4) : images.get(2);
    }

    private BufferedImage path(final Direction in, final Direction out) {
        int i = index(in, out);
        return i < 2 ? images.get(i) : Scalr.rotate(images.get(i < 5 ? 1 : 0), Rotation.values()[2 - (i % 3)]);
    }

    private static int index(final Direction in, final Direction out) {
        return Math.min(in.ordinal() * 4 + out.ordinal(), opposite(out) * 4 + opposite(in)) % 7;
    }

    private static int opposite(final Direction d) {
        return (d.ordinal() + 2) % 4;
    }
}
