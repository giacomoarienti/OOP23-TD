package it.unibo.towerdefense.commons.dtos.score;

import it.unibo.towerdefense.model.score.ScoreImpl;

/**
 * Data transfer object for Score.
 */
public class ScoreDTOImpl implements ScoreDTO {

    private final String name;
    private final int wave;

    /**
     * Constructs a new ScoreDTO object with the provided name and wave.
     * @param name the name of the player.
     * @param wave the wave reached by the player.
     */
    public ScoreDTOImpl(final String name, final int wave) {
        this.name = name;
        this.wave = wave;
    }

    /**
     * Constructs a new ScoreDTO object based on the provided ScoreImpl object.
     * @param score the ScoreImpl object to create the DTO from.
     */
    public ScoreDTOImpl(final ScoreImpl score) {
        this.name = score.getName();
        this.wave = score.getWave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWave() {
        return wave;
    }
}
