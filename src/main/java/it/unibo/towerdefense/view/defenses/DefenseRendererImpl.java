package it.unibo.towerdefense.view.defenses;

import java.awt.Color;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.awt.Image;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.EmptyCircleDrawable;
import it.unibo.towerdefense.view.graphics.ImageDrawable;
import it.unibo.towerdefense.view.graphics.LineDrawable;

    /**suppressing a warning to pass the renderer without errors.*/
    @SuppressFBWarnings(
        value = "EI2",
        justification = "Renderer is intentionally mutable and safe to store."
    )

/**Implementation of DefenseRenderer.*/
public class DefenseRendererImpl implements DefenseRenderer {

    private final Renderer renderer;
    private final List<AttackAnimation> attacks;
    private Map<DefenseType, List<Image>> mappedDefenseImages;
    private Map<DefenseType, Image> mappedBulletsImages;
    /**for drawing range.*/
    private final Map<DefenseType, Color> matchColors = Map.of(
        DefenseType.ARCHERTOWER, Color.BLUE,
        DefenseType.BOMBTOWER, Color.BLUE,
        DefenseType.WIZARDTOWER, Color.BLUE,
        DefenseType.THUNDERINVOKER, Color.RED
    );

    /**for understanding if animations are area based.*/
    private final Map<DefenseType, Boolean> matchAreaBased = Map.of(
        DefenseType.ARCHERTOWER, false,
        DefenseType.BOMBTOWER, true,
        DefenseType.WIZARDTOWER, false,
        DefenseType.THUNDERINVOKER, false
    );

     /**
     * Constructor for this class.
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
        final Image image = this.mappedDefenseImages.get(def.getType()).get(def.getLevel() - 1);
           renderer.submitToCanvas(new ImageDrawable(image, def.getPosition().get()));
           if (def.isFocused()) {
                renderer.submitToCanvas(new EmptyCircleDrawable(def.getPosition().get(), def.getRange(),
                matchColors.get(def.getType())));
           }
    }
    /**Adds attacks to list.
     * @param def the description to take attacks from.
    */
    private void addAttacks(final DefenseDescription def) {
        if (!matchAreaBased.get(def.getType())) {
                def.getTargets().forEach(x ->
                attacks.add(new AttackAnimationImpl(matchAreaBased.get(def.getType()),
                def.getPosition().get(), x, def.getType()))
            );
        } else if (!def.getTargets().isEmpty()) {
            attacks.add(new AttackAnimationImpl(matchAreaBased.get(def.getType()),
            def.getPosition().get(), def.getTargets().get(0), def.getType()));
        }

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
        for (final DefenseType defType : DefenseType.values()) {
            if (defType == DefenseType.NOTOWER) {
                continue;
            }
            /**Load bullets.*/
            try {
                final int areaBasedSize = 1;
                final Image bul = renderer.getImageLoader().loadImage(DefenseImagePaths.buildBulletPath(defType),
                matchAreaBased.get(defType) ? areaBasedSize : 0.5);
                mappedBulletsImages.put(defType, bul);
            } catch (IOException e) {
                throw  new UncheckedIOException(e);
            }

            /**Load defenses.*/
            final List<Image> images = new LinkedList<>();
            for (int i = 1; i <= 4; i++) {
                try {
                    final Image image = renderer.getImageLoader().loadImage(DefenseImagePaths.buildDefensePath(defType, i), 1);
                    images.add(image);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
            mappedDefenseImages.put(defType, images);
        }
    }
}
