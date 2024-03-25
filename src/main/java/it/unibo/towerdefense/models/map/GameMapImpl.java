package it.unibo.towerdefense.models.map;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.Size;

/**
 * Class that implements GameMap methods, and generate the map.
 */
public class GameMapImpl implements GameMap {

    private enum Direction {
        N(0, +1), W(+1, 0), S(0, -1), E(-1, 0);

        private final int orizontal;
        private final int vertical;

        Direction(final int orizontal, final int vertical) {
            this.orizontal = orizontal;
            this.vertical = vertical;
        }

        public int orizontal() {
            return orizontal;
        }

        public int vertical() {
            return vertical;
        }

    }

    private final Size sizeInPixel;
    private final Size sizeInCell;
    private final Map<PathCell, List<PathCell>> path;
    private final PathCell spawn;
    private final Random random = new Random();
    static final int MAX_STRAIGHT = 5;

    /**
     * Constructor from size of map in terms of game space and screen space.
     * @param sizeInPixel size of map in terms of pixels
     * @param sizeInCell size of map in terms of Cells
     */
    public GameMapImpl(final Size sizeInPixel, final Size sizeInCell) {

        this.sizeInPixel = sizeInPixel;
        this.sizeInCell = sizeInCell;
        this.path = new HashMap<PathCell, List<PathCell>>();

        int distanceToEnd = 0;
        final PathCell end = new PathCellImpl(random.nextInt(sizeInCell.getHeight()), sizeInCell.getWidth() - 1, distanceToEnd);
        PathCell temp = end;
        int directionIndex = 3;
        int sameDirectionCounter = 0;
        final int spawnJ = 0;
        final int spawnI = random.nextInt(sizeInCell.getHeight());


        do {
            path.put(temp, new LinkedList<>());
            if (sameDirectionCounter > random.nextInt(MAX_STRAIGHT)) {      // TODO
                sameDirectionCounter = 0;
                directionIndex = turnRight(directionIndex);
            }

            sameDirectionCounter++;
            var dir = Direction.values()[directionIndex];
            path.get(temp).add(new PathCellImpl(temp.getI() + dir.vertical(), temp.getJ() + dir.orizontal(), distanceToEnd));
            temp = path.get(temp).get(0);
        } while (temp.getI() != spawnJ || temp.getJ() != spawnI);

        spawn = new PathCellImpl(spawnI, spawnJ, distanceToEnd);
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
    public Cell getCellAt(final Position position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCellAt'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathCell getSpawnCell() {
        return spawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathCell getNext(final PathCell current) throws NoSuchElementException {
        final var nexts = path.get(current);
        if (nexts.size() == 0) {
            throw new NoSuchElementException("This cell does not have a next one");
        }
        return nexts.get(random.nextInt(nexts.size()));
    }

    private int turnRight(final int ind) {
        return turnLeft(turnLeft(turnLeft(ind)));
    }

    private int turnLeft(final int ind) {
        return (ind + 1) % Direction.values().length;
    }

}
