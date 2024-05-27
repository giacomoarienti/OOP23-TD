package it.unibo.towerdefense.view.graphics;

import java.awt.Graphics2D;

import org.apache.commons.lang3.tuple.Pair;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.Size;

/**
 * Abstract class that represents a drawable object.
 */
public abstract class Drawable {

    private final LogicalPosition position;
    private final Size size;
    private Pair<Double, Double> scale = Pair.of(1.0, 1.0);

    /**
     * Constructor from LogicalPosition and Size.
     * @param pos position where to draw
     * @param size the size of the object
     */
    public Drawable(final LogicalPosition pos, final Size size) {
        this.position = pos.copy();
        this.size = size.copy();
    }

    /**
     * Initializes its fields to empty values.
     */
    public Drawable() {
        this.position = new LogicalPosition(0, 0);
        this.size = Size.of(0, 0);
    }

    /**
     * Returns the position of the drawable.
     * @return the position of the object.
     */
    @SuppressFBWarnings(
        value = "ICAST",
        justification = "Casting the result of integral division to double is intentional for scaling."
    )
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
     * @return the scaled value
     */
    public double scale(final int value) {
        return value * this.scale.getLeft() / this.position.getScalingFactor();
    }

    /**
     * Returns the scale factor.
     * @return the scale factor
     */
    public Pair<Double, Double> getScale() {
        return this.scale;
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
     * @return the scaled size of the object.
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
