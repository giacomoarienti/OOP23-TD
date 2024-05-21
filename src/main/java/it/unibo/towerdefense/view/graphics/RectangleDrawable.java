package it.unibo.towerdefense.view.graphics;

import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.Color;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;

/**
 * Class that represents a drawable rectangle.
 */
public class RectangleDrawable extends Drawable {

    private final Color color;

    /**
     * Constructor from Size, LogicalPosition and Color.
     * @param pos position where to draw
     * @param size the size of the rectangle
     * @param color the color of the rectangle
     */
    public RectangleDrawable(final LogicalPosition pos, final Size size, final Color color) {
        super(pos, size);
        this.color = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paint(final Graphics2D g2d) {
        final Position pos = this.getPosition();
        final Size size = this.getSize();
        // draw rectangle
        g2d.setColor(this.color);
        final var rect = new Rectangle2D.Double(
            pos.getX(), pos.getY(), size.getWidth(), size.getHeight()
        );
        g2d.fill(rect);
    }
}
