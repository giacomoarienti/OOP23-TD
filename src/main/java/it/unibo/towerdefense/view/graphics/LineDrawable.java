package it.unibo.towerdefense.view.graphics;

import java.awt.geom.Line2D;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.Graphics2D;
import java.awt.Color;

import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**
 * Class that represents a drawable line.
 */
public class LineDrawable extends Drawable {

    private final Color color;
    private final LogicalPosition to;
    private final LogicalPosition from;

    /**
     * Constructor from LogicalPosition and Color.
     * @param from starting position of the line
     * @param to ending position of the line
     * @param color the color of the line
     */
    public LineDrawable(final LogicalPosition from, final LogicalPosition to, final Color color) {
        super();
        this.from = from.copy();
        this.to = to.copy();
        this.color = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paint(final Graphics2D g2d) {
        final Pair<Double, Double> scale = this.getScale();
        // draw line
        g2d.setColor(this.color);
        final var line = new Line2D.Double(
            this.from.getRelativeX() * scale.getLeft(),
            this.from.getRelativeY() * scale.getRight(),
            this.to.getRelativeX() * scale.getLeft(),
            this.to.getRelativeY() * scale.getRight()
        );
        g2d.draw(line);
    }
}
