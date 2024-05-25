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
public class EmptyCircleDrawable extends Drawable {

    private final Color color;
    private final int radius;

    /**
     * Constructor from Size, LogicalPosition and Color.
     * @param pos center position of the shape
     * @param radius radius of the circle
     * @param color the color of the circle
     */
    public EmptyCircleDrawable(final LogicalPosition pos, final int radius, final Color color) {
        super(pos, Size.of(radius * 2, radius * 2));
        this.color = color;
        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paint(final Graphics2D g2d) {
        final Position pos = this.getCenterPosition();
        final var scaledRadius = this.scale(this.radius);
        // draw circle
        g2d.setColor(this.color);
        final var circle = new Ellipse2D.Double(
            pos.getX() - scaledRadius,
            pos.getY() - scaledRadius,
            2.0 * scaledRadius,
            2.0 * scaledRadius
        );
        g2d.draw(circle);
    }
}
