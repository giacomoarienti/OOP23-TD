package it.unibo.towerdefense.commons.engine;

import it.unibo.towerdefense.commons.api.Copyable;

/**
 * Interface that models the concept of size
 * (as a rectangle) in a 2D space.
 */
public interface Size extends Copyable<Size> {

    /**
     * Width getter.
     * @return the width
     */
    int getWidth();

    /**
     * Height getter.
     * @return the height
     */
    int getHeight();

    /**
     * Factory method to create a new Size object.
     * @param width the width of the size
     * @param height the height of the size
     * @return the new Size object
     */
    static Size of(final int width, final int height) {
        return new SizeImpl(width, height);
    }
}
