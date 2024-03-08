package it.unibo.towerdefense.models.map;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.Size;

public class GameMapImpl implements GameMap {

    private final Size sizeInPixel;
    private final Size sizeInCell;
    private final Map<PathCell, List<PathCell>> path;
    private final Random random = new Random();

    public GameMapImpl(final Size sizeInPixel, final Size sizeInCell) {
        this.sizeInPixel = sizeInPixel;
        this.sizeInCell = sizeInCell;
        this.path = new HashMap<PathCell, List<PathCell>>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Size getSize() {
        return sizeInCell;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCellAt(Position position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCellAt'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathCell getSpawnCell() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSpawnCell'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathCell getNext(final PathCell current) throws NoSuchElementException {
        final var nexts = path.get(current);
        if (nexts.size() == 0) {
            throw new NoSuchElementException();
        }
        return nexts.get(random.nextInt(nexts.size()));
    }

}
