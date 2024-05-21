package it.unibo.towerdefense.view.graphics;

import java.awt.Graphics2D;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;

/**
 * Abstract class that represents a drawable object.
 */
public abstract class Drawable {

    private LogicalPosition position;
    private Pair<Double, Double> scale = Pair.of(1.0, 1.0);
    private Size size;

    /**
     * Constructor from LogicalPosition and Size.
     * @param pos position where to draw
     * @param size the size of the object
     */
    public Drawable(final LogicalPosition pos, final Size size) {
        this.position = pos.clone();
        this.size = size.copy();
    }

    /**
     * Returns the position of the drawable.
     * @return the size of the object.
     */
    public Position getPosition() {
        return Position.of(
            (int) (
               this.position.getX()
                    * this.scale.getLeft()
                    / this.position.getScalingFactor()
                    - this.size.getWidth() / 2
            ),
            (int) (
                this.position.getY()
                    * this.scale.getRight()
                    / this.position.getScalingFactor()
                    - this.size.getHeight() / 2
            )
        );
    }

    /**
     * Returns the size of the drawable.
     * @return the size of the object.
     */
    public Size getSize() {
        return this.size.copy();
    }

    /**
     * Sets the scale factor which the position will be multiplied by.
     * @param scale the scale factor
     */
    public void setScale(final Pair<Double, Double> scale) {
        this.scale = scale;
    }

    /**
     * Paints the drawable on the canvas through Graphics2D.
     * @param g2d the graphics object to paint on
     */
    protected abstract void paint(Graphics2D g2d);
}
