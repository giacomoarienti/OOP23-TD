package it.unibo.towerdefense.utils.images;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests for ImageLoader.
 */
public class TestImageLoader {

    /**
     * Tests on wrong initialization.
     */
    @Test
    void testSize(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ImageLoader(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ImageLoader(0));
    }

    /**
     * Tests on wrong arguments.
     */
    @Nested
    public class NestedTestBlock {
        private final static int size = 100;
        private ImageLoader tested;
        /**
         * Initializes an ImageLoader to be tested.
         */
        @BeforeEach
        void init(){
            tested = new ImageLoader(size);
        }

        /**
         * Tests the loader throws if it can't load an image.
         */
        @Test
        void testAbsentImage() {
            Assertions.assertThrows(IOException.class, () -> tested.loadImage("nonexistent.png", 1.0));
            Assertions.assertThrows(IOException.class, () -> tested.loadImage("it/unibo/towerdefense/utils/images/test.svg", 1.0));
        }

        /**
         * Tets the loader throws if size <= 0.
         */
        @Test
        void testWrongSize() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> tested.loadImage("it/unibo/towerdefense/utils/images/test.svg", 0));
            Assertions.assertThrows(IllegalArgumentException.class, () -> tested.loadImage("it/unibo/towerdefense/utils/images/test.svg", -1));
        }
    }

    /**
     * Tests the image with name filename is scaled correctly to be scale * size on its longest side.
     *
     * @param filename the name of the image to load
     * @param size the size of a square cell
     * @param scale the desired scale relative to a cell
     * @throws IOException if the image can't be loaded
     */
    private void testScaling(String filename, int size, double scale) throws IOException {
        ImageLoader loader = new ImageLoader(size);
        BufferedImage test = loader.loadImage(filename, scale);
        /*
        * Image can't be bigger than size*scale x size*scale
        */
        Assertions.assertTrue(test.getWidth() <= size * scale);
        Assertions.assertTrue(test.getHeight() <= size * scale);
        /*
        * Longest side should be size * scale
        */
        Assertions.assertTrue(Math.max(test.getWidth(), test.getHeight()) == (int)(size * scale));
    }

    /**
     * Tests a square image loads with the desired size.
     */
    @Test
    void testLoadSquareImage() throws IOException {
        testScaling("it/unibo/towerdefense/utils/images/test.png", 100, 1);
        testScaling("it/unibo/towerdefense/utils/images/test.png", 384, 0.6345);
    }

    /**
     * Tests a rectangular image loads with the desired size.
     */
    @Test
    void testLoadRectangularImage() throws IOException {
        testScaling("it/unibo/towerdefense/utils/images/test2.jpg", 100, 1);
        testScaling("it/unibo/towerdefense/utils/images/test2.jpg", 1030, 4.5567);
    }
}
