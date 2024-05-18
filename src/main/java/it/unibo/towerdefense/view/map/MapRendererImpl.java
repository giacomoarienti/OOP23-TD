package it.unibo.towerdefense.view.map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import it.unibo.towerdefense.commons.Constants;
import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.PositionImpl;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.ImageDrawable;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;

public class MapRendererImpl implements MapRenderer {

    private final static String ROOT = "it/unibo/towerdefense/view/map/";
    private final static String EXTENSION = ".png";
    private final static List<String> NAMES = List.of("straight", "curve", "buildable", "obstacle");
    private final List<BufferedImage> images = new ArrayList<>();

    public MapRendererImpl(final ImageLoader imLo) {
        for (int i = 0; i < 4; i++) {
            try {
            images.add(imLo.loadImage(ROOT + NAMES.get(i) + EXTENSION, 1.0));
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize the image for type " + NAMES.get(i), e);
            }
        }
    }

    @Override
    public void renderPath(Renderer gr, final Stream<CellInfo> map) {
        final var mapDrawables = map.map(p -> new ImageDrawable(
            getImage(p),
            new PositionImpl(
                p.getPosition().getCellX(),
                p.getPosition().getCellY()
            )
        )).toList();
        gr.submitBackgroundAllToCanvas(mapDrawables);
    }


    private BufferedImage getImage(CellInfo p) {
        return p.isPathCell() ? path(p.getDirectionsSum()) : buildable(p);
    }

    private BufferedImage buildable(CellInfo p) {
        return p.isBuildable() ? images.get(2) : images.get(3);
    }

    private BufferedImage path(int i) {
        return images.get(i % 2);
    }

    private static BufferedImage rotateImageClockwise(BufferedImage originalImage, int degrees) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Creare una nuova immagine ruotata con dimensioni scambiate
        BufferedImage rotatedImage = new BufferedImage(height, width, originalImage.getType());

        // Creare una trasformazione affine per ruotare l'immagine
        AffineTransform transform = new AffineTransform();
        transform.translate(height / 2.0, width / 2.0);
        transform.rotate(Math.toRadians(degrees));
        transform.translate(-width / 2.0, -height / 2.0);

        // Applicare la trasformazione all'immagine
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        op.filter(originalImage, rotatedImage);

        return rotatedImage;
    }
}
