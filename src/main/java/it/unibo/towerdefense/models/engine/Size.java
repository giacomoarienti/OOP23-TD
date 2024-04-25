package it.unibo.towerdefense.models.engine;

import it.unibo.towerdefense.models.Copyable;

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
}
