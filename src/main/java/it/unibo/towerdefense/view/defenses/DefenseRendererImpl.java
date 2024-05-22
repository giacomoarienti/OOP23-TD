package it.unibo.towerdefense.view.defenses;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.management.RuntimeErrorException;

import java.awt.Image;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.SizeImpl;
import it.unibo.towerdefense.model.defenses.DefenseType;
import it.unibo.towerdefense.view.graphics.Renderer;
import it.unibo.towerdefense.view.graphics.EmptyCircleDrawable;
import it.unibo.towerdefense.view.graphics.ImageDrawable;
import it.unibo.towerdefense.view.graphics.LineDrawable;

public class DefenseRendererImpl implements DefenseRenderer {

    final private Renderer renderer;
    private List<AttackAnimation> attacks;
    private Map<DefenseType,List<Image>> mappedDefenseImages;

    public DefenseRendererImpl(Renderer renderer) {
        this.renderer = renderer;
        attacks = new LinkedList<>();
        loadImages();
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
           Image image = this.mappedDefenseImages.get(def.getType()).get(def.getLevel()-1);
           renderer.submitToCanvas(new ImageDrawable(image, def.getPosition()));
           renderer.submitToCanvas(new EmptyCircleDrawable(def.getPosition(), def.getRange(), Color.BLUE));
    }
    /**Adds attacks to list.*/
    private void addAttacks(DefenseDescription def) {
        def.getTargets().forEach(x ->
            attacks.add(new AttackAnimationImpl(def.getType()==DefenseType.BOMBTOWER,
            def.getPosition(), x))
        );
    }

    /**loads attacks for given defense description.
     * @param def the description to load.
    */
    private void renderAttacks() {
        /**remove finished animations*/
        this.attacks.removeIf(x -> !x.isAlive());

        this.attacks.forEach(x -> {
            renderer.submitToCanvas(new LineDrawable(x.getAttacked(), x.getAttacker(), Color.WHITE));
            x.decreaseTimeToLive();
        });
    }

    private void loadImages() {
        this.mappedDefenseImages = new HashMap<>();
        for (DefenseType defType : DefenseType.values()) {
            if (defType == DefenseType.NOTOWER) {
                continue;
            }
            List<Image> images = new LinkedList<>();
            for(int i=1; i<=4; i++){
                try{
                    System.out.println(defType);
                    Image image = renderer.getImageLoader().loadImage(DefenseImagePaths.buildDefensePath(defType, i), 1);
                    images.add(image);
                }
                catch (IOException e){
                    throw new RuntimeException(e.getMessage());
                }
            }
            mappedDefenseImages.put(defType, images);
        }
    }
}
