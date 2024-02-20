package it.unibo.towerdefense.models.engine;

/**
 * Interface that models the concept of a collision
 * box in a 2D space as a rectangle.
 */
public interface CollisionBox {

    /**
     * Position getter.
     * @return the starting position of the CollisionBox
     */
    Position getPosition();

    /**
     * Size getter.
     * @return the size of the CollisionBox
     */
    Size getSize();

    /**
     * Checks if two CollisionBoxes are colliding.
     * @param other the CollisionBox to check
     * @return true if the two boxes are colliding
     */
    boolean collidesWith(CollisionBox other);
}
