package it.unibo.towerdefense.commons.utils.images;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;

/**
 * A class for loading an image from a filename, already scaled to the desired
 * size relative to the size of a cell.
 */
public class ImageLoader {
    /*
     * Size of a square cell in pixels.
     */
    private final int cellSize;

    /**
     * Constructor for the class.
     *
     * @param cellSize the side of a cell
     */
    public ImageLoader(final int cellSize) {
        if (cellSize <= 0) {
            throw new IllegalArgumentException("Cell size must be > 0");
        }
        this.cellSize = cellSize;
    }

    /**
     * Loads an image from the given path.
     *
     * @param name the path from the java classpath to the file
     *             allowed formats are JPEG, PNG, BMP, WBMP, GIF
     * @param size the size in cells the image should have,
     *             i.e a size = 1 means that the longest side of
     *             the image will be the same length as a cell.
     * @return the loaded image
     */
    public BufferedImage loadImage(final String name, final double size) throws IOException {
        if (size <= 0) {
            throw new IllegalArgumentException("size can't be <= 0");
        }
        InputStream image = ClassLoader.getSystemResourceAsStream(name);
        BufferedImage baseImage = ImageIO.read(image);
        return Scalr.resize(baseImage, (int) (cellSize * size));
    }
}
