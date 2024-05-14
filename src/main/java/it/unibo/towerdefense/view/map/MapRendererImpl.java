package it.unibo.towerdefense.view.map;

import java.util.List;
import java.util.stream.Stream;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.view.graphics.GameRenderer;
import it.unibo.towerdefense.view.graphics.ImageDrawable;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;
import java.awt.image.BufferedImage;

public class MapRendererImpl implements MapRenderer {

    private final ImageLoader imLo;
    private BufferedImage image;

    public MapRendererImpl(final ImageLoader imLo) {
        this.imLo = imLo;
        image = loadImage();
    }

    @Override
    public void renderPath(GameRenderer gameRenderer, final Stream<LogicalPosition> path) {
        path/*.peek(p -> System.out.println(p))*/.forEach(p -> gameRenderer.submitToCanvas(new ImageDrawable(image, p)));
    }

    private BufferedImage loadImage() {
        try {
            System.out.println("carico immagine");
            return imLo.loadImage("it/unibo/towerdefense/utils/images/test.png", 1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize the image for type map", e);
        }
    }
}
