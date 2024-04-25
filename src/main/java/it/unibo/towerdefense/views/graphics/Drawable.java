package it.unibo.towerdefense.views.graphics;

import java.awt.Graphics2D;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.Size;

/**
 * Abstract class that represents a
 */
public abstract class Drawable {

    private Size size;
    private Position position;

    /**
     * Constructor from starting position and size.
     * @param pos starting position
     * @param size size of the drawable
     */
    public Drawable(final Position pos, final Size size) {
        this.position = pos.copy();
        this.size = size.copy();
    }

    /**
     * Constructor from size, the position is set to 0,0.
     * @param size size of the drawable
     */
    public Drawable(final Size size) {
        this(Position.origin(), size);
    }

    /**
     * Returns the dimension of the drawable.
     * @return the size of the object.
     */
    public Size getSize() {
        return size.copy();
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
