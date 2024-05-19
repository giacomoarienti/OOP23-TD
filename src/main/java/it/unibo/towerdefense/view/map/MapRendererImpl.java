package it.unibo.towerdefense.view.map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;

import java.awt.image.BufferedImage;

import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.ImageDrawable;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;

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

    @Override
    public void renderPath(Renderer gr, final Stream<CellInfo> map) {
        final var mapDrawables = map.map(c -> new ImageDrawable(getImage(c), c.getPosition())).toList();
        gr.submitBackgroundAllToCanvas(mapDrawables);
    }


    private BufferedImage getImage(CellInfo c) {
        return c.isPathCell() ? path(c.getDirectionsSum()) : buildable(c);
    }

    private BufferedImage buildable(CellInfo c) {
        return !c.isBuildable() ? images.get(3) : c.isSelected() ? images.get(4) : images.get(2);
    }

    private BufferedImage path(int i) {
        return i < 2 ? images.get(i % 2) : Scalr.rotate(images.get(i % 2), Rotation.values()[i / 2 - 1]);
    }
}
