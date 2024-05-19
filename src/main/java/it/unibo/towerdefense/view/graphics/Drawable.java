package it.unibo.towerdefense.view.graphics;

import java.awt.Graphics2D;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;

/**
 * Abstract class that represents a drawable object.
 */
public abstract class Drawable {

    private Position position;
    private double scale = 1;

    /**
     * Constructor from Position.
     * @param pos position where to draw
     */
    public Drawable(final Position pos) {
        if (pos instanceof LogicalPosition) {
            this.position = ((LogicalPosition) pos).toPosition();
        } else {
            this.position = pos;
        }
    }

    /**
     * Returns the position of the drawable.
     * @return the size of the object.
     */
    public Position getPosition() {
        return this.position.scaled(this.scale);
    }

    /**
     * Sets the scale factor which the position will be multiplied by.
     * @param scale the scale factor
     */
    public void setScale(final double scale) {
        this.scale = scale;
    }

    /**
     * Paints the drawable on the canvas through Graphics2D.
     * @param g2d the graphics object to paint on
     */
    protected abstract void paint(Graphics2D g2d);
}
