package it.unibo.towerdefense.view.graphics;

import java.awt.geom.Ellipse2D;
import java.awt.Graphics2D;
import java.awt.Color;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;

/**
 * Class that represents a circle drawable.
 */
public class CircleDrawable extends Drawable {

    private final Color color;

    /**
     * Constructor from Size, LogicalPosition and Color.
     * @param pos center position of the shape
     * @param size the size of the shape
     * @param color the color of the shape
     */
    public CircleDrawable(final LogicalPosition pos, final Size size, final Color color) {
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
        final var circle = new Ellipse2D.Double(
            pos.getX(), pos.getY(), size.getWidth(), size.getHeight()
        );
        g2d.fill(circle);
    }
}