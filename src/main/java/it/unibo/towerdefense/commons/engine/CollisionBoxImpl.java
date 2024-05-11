package it.unibo.towerdefense.commons.engine;

/**
 * Implementation of the concept of CollisionBox
 * in a 2D space.
 */
public class CollisionBoxImpl implements CollisionBox {

    private final Position position;
    private final Size size;

    /**
     * Constructor from Position and Size.
     * @param position the starting position
     * @param size the size of the box
     */
    public CollisionBoxImpl(final Position position, final Size size) {
        this.position = new PositionImpl(position);
        this.size = new SizeImpl(size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return new PositionImpl(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Size getSize() {
        return new SizeImpl(size);
    }

    /**
     * {@inheritDoc}
     * Code from: java.awt.Rectangle::intersect
     */
    @Override
    public boolean collidesWith(final CollisionBox other) {
        int tw = this.size.getWidth();
        int th = this.size.getHeight();
        int rw = other.getSize().getWidth();
        int rh = other.getSize().getHeight();
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        final int tx = this.position.getX();
        final int ty = this.position.getY();
        final int rx = other.getPosition().getX();
        final int ry = other.getPosition().getY();
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        //      overflow || intersect
        return (rw < rx || rw > tx)
                && (rh < ry || rh > ty)
                && (tw < tx || tw > rx)
                && (th < ty || th > ry);
    }
}
