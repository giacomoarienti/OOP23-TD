package it.unibo.towerdefense.view.defenses;

import java.io.IOException;
import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.utils.images.ImageLoader;
import it.unibo.towerdefense.view.graphics.GameRenderer;

public class DefenseRendererImpl implements DefenseRenderer {

    private GameRenderer renderer;

    public DefenseRendererImpl(GameRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void render(Stream<DefenseDescription> defenses) {
        ImageLoader loader = renderer.getImageLoader();
            defenses.forEach(x -> {
                try {
                    loader.loadImage(
                        DefenseImagePaths.buildPath(x.getType(), x.getLevel()), DefenseImagePaths.IMAGE_SIZE);
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        );
    }
}
