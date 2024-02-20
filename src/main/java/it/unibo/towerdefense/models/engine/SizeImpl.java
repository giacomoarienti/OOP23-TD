package it.unibo.towerdefense.models.engine;

/**
 * Class that implements the Size interface.
 */
public class SizeImpl implements Size {

    private final int width;
    private final int height;

    /**
     * Constructor from width and height.
     * @param width
     * @param height
     */
    public SizeImpl(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Constructor from given size's width and height.
     * @param size to copy from
     */
    public SizeImpl(final Size size) {
        this.width = size.getWidth();
        this.height = size.getHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }
}
