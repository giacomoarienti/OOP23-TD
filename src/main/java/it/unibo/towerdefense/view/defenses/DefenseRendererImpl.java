package it.unibo.towerdefense.view.defenses;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.model.defenses.DefenseType;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.ImageDrawable;

public class DefenseRendererImpl implements DefenseRenderer {

    private Renderer renderer;
    private List<AttackAnimation> attacks;

    public DefenseRendererImpl(Renderer renderer) {
        this.renderer = renderer;
        this.attacks = List.of();
    }

    @Override
    public void render(Stream<DefenseDescription> defenses) {
            defenses.forEach(x -> {
                renderDefenses(x);
                addAttacks(x);
            }
        );
        renderAttacks();
    }

    /**loads an image for a given defense description.
     * @param def the description to load.
    */
    private void renderDefenses(DefenseDescription def) {
        try {
           BufferedImage image =
           renderer.getImageLoader().
           loadImage(DefenseImagePaths.buildDefensePath(def.getType(), def.getLevel()), DefenseImagePaths.IMAGE_SIZE);
           renderer.submitToCanvas(new ImageDrawable(image, def.getPosition()));
           /**add attacks.*/

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    /**Adds attacks to list.*/
    private void addAttacks(DefenseDescription def) {
        def.getTargets().stream().forEach(x ->
            attacks.add(new AttackAnimationImpl(def.getType()==DefenseType.BOMBTOWER,
            def.getPosition(), x))
        );
    }

    /**loads attacks for given defense description.
     * @param def the description to load.
    */
    private void renderAttacks() {
        /**remove finished animations*/
        this.attacks = attacks.stream().filter(x->x.isAlive()).toList();
        this.attacks.forEach(x -> {

        });
    }
}
