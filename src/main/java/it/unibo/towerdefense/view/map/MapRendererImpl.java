package it.unibo.towerdefense.view.map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;

import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.ImageDrawable;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;

/**
 * Class that implements MapRenderer with images from specific files.
 */
public class MapRendererImpl implements MapRenderer {

    private static final String ROOT = "it/unibo/towerdefense/view/map/";
    private static final String EXTENSION = ".png";
    private static final List<String> NAMES = List.of("straight", "curve", "buildable", "obstacle", "selected");
    private final List<BufferedImage> images = new ArrayList<>();

    /**
     * Constructor from ImageLoader.
     * @param imLo Object to load images from files.
     */
    public MapRendererImpl(final ImageLoader imLo) {
        for (final String name: NAMES) {
            try {
                images.add(imLo.loadImage(ROOT + name + EXTENSION, 1.0));
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to initialize the image for type " + name, e);
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
        final int i = index(in, out);
        final int straightIndex = 5;
        return i < 2 ? images.get(i) : Scalr.rotate(images.get(i < straightIndex ? 1 : 0), Rotation.values()[2 - (i % 3)]);
    }

    /**
     * Hash function that returns a unique index from a ordered pair of directions.
     * It returns same index for anti-reciprocal pairs: es. E,N = S,O.
     * @param in direction.
     * @param out direction.
     * @return the index witch values from 0 to 6.
     */
    private static int index(final Direction in, final Direction out) {
        final int q = 7;
        return Math.min(in.ordinal() * 4 + out.ordinal(), opposite(out) * 4 + opposite(in)) % q;
    }

    private static int opposite(final Direction d) {
        return (d.ordinal() + 2) % 4;
    }
}
