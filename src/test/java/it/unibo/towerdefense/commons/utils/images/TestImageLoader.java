package it.unibo.towerdefense.commons.utils.images;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for ImageLoader.
 */
class TestImageLoader {

    private static final double RSCALE2 = 4.5567;
    private static final int RSIZE2 = 1030;
    private static final double RSCALE1 = 1;
    private static final int RSIZE1 = 100;
    private static final double SSCALE2 = 0.6345;
    private static final int SSIZE2 = 384;
    private static final double SSCALE1 = 1;
    private static final int SSIZE1 = 100;

    /**
     * Tests on wrong initialization.
     */
    @Test
    void testSize() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ImageLoader(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ImageLoader(0));
    }

    /**
     * Tests on wrong arguments.
     */
    @Nested
    class NestedTestBlock {
        private static final int SIZE = 100;
        private ImageLoader tested;

        /**
         * Initializes an ImageLoader to be tested.
         */
        @BeforeEach
        void init() {
            tested = new ImageLoader(SIZE);
        }

        /**
         * Tests the loader throws if it can't load an image.
         */
        @Test
        void testAbsentImage() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> tested.loadImage("nonexistent.png", 1.0));
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> tested.loadImage("it/unibo/towerdefense/utils/images/test.svg", 1.0));
        }

        /**
         * Tets the loader throws if size <= 0.
         */
        @Test
        void testWrongSize() {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> tested.loadImage("it/unibo/towerdefense/utils/images/test.svg", 0));
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> tested.loadImage("it/unibo/towerdefense/utils/images/test.svg", -1));
        }
    }

    /**
     * Tests the image with name filename is scaled correctly to be scale * size on
     * its longest side.
     *
     * @param filename the name of the image to load
     * @param size     the size of a square cell
     * @param scale    the desired scale relative to a cell
     * @throws IOException if the image can't be loaded
     */
    private void testScaling(final String filename, final int size, final double scale) throws IOException {
        final ImageLoader loader = new ImageLoader(size);
        final BufferedImage test = loader.loadImage(filename, scale);
        /*
         * Image can't be bigger than size*scale x size*scale
         */
        Assertions.assertTrue(test.getWidth() <= size * scale);
        Assertions.assertTrue(test.getHeight() <= size * scale);
        /*
         * Longest side should be size * scale
         */
        Assertions.assertEquals((int) (size * scale), Math.max(test.getWidth(), test.getHeight()));
    }

    /**
     * Tests a square image loads with the desired size.
     */
    @Test
    void testLoadSquareImage() throws IOException {
        testScaling("it/unibo/towerdefense/utils/images/test.png", SSIZE1, SSCALE1);
        testScaling("it/unibo/towerdefense/utils/images/test.png", SSIZE2, SSCALE2);
    }

    /**
     * Tests a rectangular image loads with the desired size.
     */
    @Test
    void testLoadRectangularImage() throws IOException {
        testScaling("it/unibo/towerdefense/utils/images/test2.jpg", RSIZE1, RSCALE1);
        testScaling("it/unibo/towerdefense/utils/images/test2.jpg", RSIZE2, RSCALE2);
    }
}
