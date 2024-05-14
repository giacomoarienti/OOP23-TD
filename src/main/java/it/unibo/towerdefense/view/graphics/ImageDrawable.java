package it.unibo.towerdefense.view.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import it.unibo.towerdefense.commons.engine.Position;

/**
 * Class that represents a drawable image.
 */
public class ImageDrawable extends Drawable {

    private final Image image;

    /**
     * Constructor from image, position and size.
     * @param image the image to draw
     * @param pos starting position
     */
    public ImageDrawable(final Image image, final Position pos) {
        super(pos);
        this.image = image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paint(final Graphics2D g2d) {
        final Position pos = this.getPosition();
        // draw image
        g2d.drawImage(
            this.image,
            pos.getX(), pos.getY(),
            null
        );
    }
}
