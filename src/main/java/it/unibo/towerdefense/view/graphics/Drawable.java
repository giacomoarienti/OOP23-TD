package it.unibo.towerdefense.view.graphics;

import java.awt.Graphics2D;

import it.unibo.towerdefense.commons.engine.Position;

/**
 * Abstract class that represents a drawable object.
 */
public abstract class Drawable {

    private Position position;

    /**
     * Constructor from starting position and size.
     * @param pos starting position
     */
    public Drawable(final Position pos) {
        this.position = pos.copy();
    }

    /**
     * Constructor from size, the position is set to 0,0.
     */
    public Drawable() {
        this(Position.origin());
    }

    /**
     * Returns the position of the drawable.
     * @return the size of the object.
     */
    public Position getPosition() {
        return position.copy();
    }

    /**
     * Paints the drawable on the canvas through Graphics2D.
     * @param g2d the graphics object to paint on
     */
    protected abstract void paint(Graphics2D g2d);
}
