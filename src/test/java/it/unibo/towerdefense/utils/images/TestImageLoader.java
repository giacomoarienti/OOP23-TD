package it.unibo.towerdefense.utils.images;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.towerdefense.models.engine.SizeImpl;

public class TestImageLoader {

    private ImageLoader tested;

    private final static int width = 1000;
    private final static int height = 1000;
    private final static int cellsWidth = 10;
    private final static int cellsHeight = 10;

    @BeforeEach
    void init(){
        tested = new ImageLoader(new SizeImpl(width, height), width, height);
    }

    @Test
    void testLoadImage() throws IOException {
        BufferedImage test = tested.loadImage("it/unibo/towerdefense/utils/images/test.png", 1);
        Assertions.assertEquals(width / cellsWidth, test.getWidth());
        Assertions.assertEquals(height / cellsHeight, test.getWidth());
    }
}
