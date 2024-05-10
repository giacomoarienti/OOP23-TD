package it.unibo.towerdefense.utils.images;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.towerdefense.models.engine.Size;

public class ImageLoader {

    /*
     * saved as doubles to preserve as much information as possible
     * conversion to integer is posponed.
     */
    private final double cellWidth;
    private final double cellHeigth;

    ImageLoader(Size pixelSize, int cellWidth, int cellHeigth){
        this.cellWidth = pixelSize.getWidth() / (double)cellWidth;
        this.cellHeigth = pixelSize.getHeight() / (double)cellHeigth;
    }

    /**
     * Loads an image from the given path.
     *
     * @param path the path from the java classpath to the file
     *             allowed formats are JPEG, PNG, BMP, WBMP, GIF
     * @param size the size in cells the image should have,
     *             i.e a size = 1 means that the longest side of
     *             the image will be the same length as a cell.
     * @return the loaded image
     */
    BufferedImage loadImage(String name, double size) throws IOException {
        try {
            BufferedImage baseImage = ImageIO.read(ClassLoader.getSystemResourceAsStream(name));
            int desiredWidth = (int)(size * cellWidth);
            int desiredHeight = (int)(size * cellHeigth);
            BufferedImage resizedImage = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(baseImage, 0, 0, desiredWidth, desiredHeight, null);
            g2d.dispose();
            return resizedImage;
        } catch (Exception e){
            throw new IOException("Couldn't load image with name " + name);
        }
    }
}
