package it.unibo.towerdefense.models.map;

import java.util.NoSuchElementException;
import java.util.Random;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;
import it.unibo.towerdefense.models.engine.Size;

/**
 * Class that implements GameMap methods, and generate the map.
 */
public class GameMapImpl implements GameMap {


    private final Size sizeInCell;
    private final Path path;
    private final PathCell spawn;
    private final Random random = new Random();
    private Cell[][] map;

    /**
     * Constructor from size of map in terms of game space and screen space.
     * @param size size of map in terms of Cells
     */
    public GameMapImpl(final Size size) {

        this.sizeInCell = size;
        map = new Cell[sizeInCell.getHeight()][sizeInCell.getWidth()];

        final Position end = new PositionImpl(sizeInCell.getWidth(), random.nextInt(sizeInCell.getHeight()));
        this.path = new PathFactory().line(end);
        Position temp = end;
        PathCell newCell;
        int distanceToEnd = 0;
        do {
            newCell = new PathCellImpl(temp, distanceToEnd);
            map[temp.getX()][temp.getY()] = newCell;
            temp = path.getPrevious(temp);
            distanceToEnd++;
        } while (temp != null);
        spawn = newCell;

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
        return map[position.getX()][position.getY()];
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
    public Position getNext(final Position current) throws NoSuchElementException {
        var nextCords = path.getNext(new PositionImpl(current.getX(), current.getY()));
        if (nextCords == null) {
            throw new NoSuchElementException("This cell does not have a next one");
        }
        return nextCords;
    }

}
