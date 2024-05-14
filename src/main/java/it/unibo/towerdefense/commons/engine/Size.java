package it.unibo.towerdefense.commons.engine;

import it.unibo.towerdefense.model.Copyable;

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
