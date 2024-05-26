package it.unibo.towerdefense.view.defenses;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


import java.awt.Image;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.EmptyCircleDrawable;
import it.unibo.towerdefense.view.graphics.ImageDrawable;
import it.unibo.towerdefense.view.graphics.LineDrawable;

/**Implementation of DefenseRenderer.*/
public class DefenseRendererImpl implements DefenseRenderer {

    private final Renderer renderer;
    private List<AttackAnimation> attacks;
    private Map<DefenseType, List<Image>> mappedDefenseImages;
    private Map<DefenseType, Image> mappedBulletsImages;

    /**Constructor for this class.
     * @param renderer used to submit images.
    */
    public DefenseRendererImpl(final Renderer renderer) {
        this.renderer = renderer;
        this.attacks = new LinkedList<>();
        loadImages();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final Stream<DefenseDescription> defenses) {
            defenses.forEach(x -> {
                renderDefenses(x);
                addAttacks(x);
                renderAttacks();
            }
        );
    }

    /**loads an image for a given defense description.
     * @param def the description to load.
    */
    private void renderDefenses(final DefenseDescription def) {
           Image image = this.mappedDefenseImages.get(def.getType()).get(def.getLevel() - 1);
           renderer.submitToCanvas(new ImageDrawable(image, def.getPosition()));
           if (def.getIsFocused()) {
                renderer.submitToCanvas(new EmptyCircleDrawable(def.getPosition(), def.getRange(), Color.BLUE));
           }
    }
    /**Adds attacks to list.
     * @param def the description to take attacks from.
    */
    private void addAttacks(final DefenseDescription def) {
        def.getTargets().forEach(x ->
            attacks.add(new AttackAnimationImpl(def.getType() == DefenseType.BOMBTOWER,
            def.getPosition(), x, def.getType()))
        );
    }

    /**renders bullets in game.*/
    private void renderAttacks() {
        /**remove finished animations*/
        this.attacks.removeIf(x -> !x.isAlive());

        this.attacks.forEach(x -> {
            renderer.submitToCanvas(new LineDrawable(x.getAttacked(), x.getAttacker(), Color.WHITE));
            renderer.submitToCanvas(new ImageDrawable(mappedBulletsImages.get(x.bulletToRender()), x.getAttacked()));
            x.decreaseTimeToLive();
        });
    }

    /**loads sprites of bullets and defenses so that it's not necessary
     * reloading every time.
     */
    private void loadImages() {
        this.mappedDefenseImages = new HashMap<>();
        this.mappedBulletsImages = new HashMap<>();
        for (DefenseType defType : DefenseType.values()) {
            if (defType == DefenseType.NOTOWER) {
                continue;
            }
            /**Load bullets.*/
            try {
                Image bul = renderer.getImageLoader().loadImage(DefenseImagePaths.buildBulletPath(defType), 0.5);
                mappedBulletsImages.put(defType, bul);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

            /**Load defenses.*/
            List<Image> images = new LinkedList<>();
            for (int i = 1; i <= 4; i++) {
                try {
                    System.out.println(defType);
                    Image image = renderer.getImageLoader().loadImage(DefenseImagePaths.buildDefensePath(defType, i), 1);
                    images.add(image);
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            mappedDefenseImages.put(defType, images);
        }
    }
}
