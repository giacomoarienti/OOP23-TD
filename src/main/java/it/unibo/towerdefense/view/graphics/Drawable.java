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
    protected Pair<Double, Double> scale = Pair.of(1.0, 1.0);
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
     * Empty-constructor.
     */
    public Drawable() { }

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
     * Returns the position of center the drawable.
     * @return the size of the object.
     */
    public Position getCenterPosition() {
        return Position.of(
            (int) (
               this.position.getX()
                    * this.scale.getLeft()
                    / this.position.getScalingFactor()
            ),
            (int) (
                this.position.getY()
                    * this.scale.getRight()
                    / this.position.getScalingFactor()
            )
        );
    }

    /**
     * Returns the scaled value of the given value.
     * @param value the value to scale
     */
    public double scale(final int value) {
        return value * this.scale.getLeft() / this.position.getScalingFactor();
    }

    /**
     * Returns the size of the drawable.
     * @return the size of the object.
     */
    public Size getSize() {
        return this.size.copy();
    }

    /**
     * Returns the scaled size of the drawable.
     */
    public Size getScaledSize() {
        return Size.of(
            (int) (this.size.getWidth() * this.scale.getLeft()),
            (int) (this.size.getHeight() * this.scale.getRight())
        );
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
