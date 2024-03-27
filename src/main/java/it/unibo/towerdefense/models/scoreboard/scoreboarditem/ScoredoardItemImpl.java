package it.unibo.towerdefense.models.scoreboard.scoreboarditem;

public class ScoredoardItemImpl implements ScoreboardItem {

    private final String name;
    private final int wave;

    public ScoredoardItemImpl() {
        this.name = "";
        this.wave = 0;
    }

    private ScoredoardItemImpl(final String name, final int wave) {
        this.name = name;
        this.wave = wave;
    }

    @Override
    public ScoreboardItem fromJSON(final String jsonData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromJSON'");
    }

    @Override
    public String toJSON() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toJSON'");
    }

}
