package it.unibo.towerdefense.utils.images;

import java.awt.Image;
import java.nio.file.Path;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.engine.Size;

public class ImageLoader {

    private final Size pixelSize;

    ImageLoader(Size pixelSize){
        this.pixelSize = pixelSize;
    }

    Image loadImage(Path path, LogicalPosition upperCorner){
        throw new UnsupportedOperationException();
    }
}
