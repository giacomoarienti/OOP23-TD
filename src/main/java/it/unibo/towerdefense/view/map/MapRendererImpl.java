package it.unibo.towerdefense.view.map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;

import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.view.graphics.GameRenderer;
import it.unibo.towerdefense.view.graphics.ImageDrawable;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;

import java.awt.image.BufferedImage;

public class MapRendererImpl implements MapRenderer {

    private final static String ROOT = "it/unibo/towerdefense/views/map/";
    private final static String EXTENSION = ".png";
    private final static List<String> NAMES = List.of("test", "test", "test");
    private final List<BufferedImage> images = new ArrayList<>();

    public MapRendererImpl(final ImageLoader imLo) {
        for (int i = 0; i < 3; i++) {
            try {
            images.add(imLo.loadImage(ROOT + NAMES.get(i) + EXTENSION, 1.0));
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize the image for type " + NAMES.get(i), e);
            }
        }
    }

    @Override
    public void renderPath(GameRenderer gr, final Stream<CellInfo> map) {
        map.forEach(p -> gr.submitToCanvas(new ImageDrawable(getImage(p), p.getPosition())));
    }


    private BufferedImage getImage(CellInfo p) {
        return p.isPathCell() ? path(p.getDirectionsSum()) : buildable(p);
    }

    private BufferedImage buildable(CellInfo p) {
        return images.get(2);
    }

    private BufferedImage path(int i) {
        return i < 2 ? images.get(i % 2) : Scalr.rotate(images.get(i % 2), Rotation.values()[i / 2 - 1]);
    }
}
