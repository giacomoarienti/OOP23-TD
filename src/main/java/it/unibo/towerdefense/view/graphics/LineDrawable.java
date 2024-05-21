package it.unibo.towerdefense.view.graphics;

import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.Color;

import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**
 * Class that represents a drawable line.
 */
public class LineDrawable extends Drawable {

    private final Color color;
    private LogicalPosition to;
    private LogicalPosition from;

    /**
     * Constructor from LogicalPosition and Color.
     * @param from starting position of the line
     * @param to ending position of the line
     * @param color the color of the line
     */
    public LineDrawable(final LogicalPosition from, final LogicalPosition to, final Color color) {
        super();
        this.from = from.clone();
        this.to = to.clone();
        this.color = color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paint(final Graphics2D g2d) {
        // draw line
        g2d.setColor(this.color);
        final var line = new Line2D.Double(
            this.from.getX(),
            this.from.getY(),
            this.to.getCellX() * this.scale.getLeft(),
            this.to.getCellY() * this.scale.getRight()
        );
        g2d.draw(line);
    }
}
