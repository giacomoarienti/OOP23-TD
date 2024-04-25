package it.unibo.towerdefense.models.map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;
import it.unibo.towerdefense.models.engine.Size;

/**
 * Class that implements GameMap methods, and generate the map.
 */
public class GameMapImpl implements GameMap {


    private final Size size;
    private final Iterator<Direction> path;
    private final PathCell spawn;
    private final PathCell end;
    private final Random random = new Random();
    private static final int OBSTACLE_RATE = 10;
    private Cell[][] map;

    /**
     * Constructor from size of map in terms of game space and screen space.
     * @param size size of map in terms of Cells
     */
    public GameMapImpl(final Size size) {

        map = new Cell[size.getHeight()][size.getWidth()];
        this.size = size;
        path = new PathFactory().line();

        Position pos = new PositionImpl(0, random.nextInt(size.getHeight()));
        spawn = new PathCellImpl(pos, path.next(), path.next());

        pos.add(path.next().asPosition());
        PathCell newCell = spawn;

        while (isInArray(pos)) {
            map[pos.getX()][pos.getY()] = newCell;
            pos.add(newCell.getOutDirection().asPosition());
            newCell = new PathCellImpl(pos, newCell.getInDirection(), path.next());
        }
        end = newCell;

        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                if (map[i][j] == null) {
                    map[i][j] = new BuildableCellImpl(new PositionImpl(i, j), random.nextInt(OBSTACLE_RATE) != 0);
                }
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Size getSize() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCellAt(final Position pos) {
        if (!isInArray(pos)) {
            return null;
        }
        return map[pos.getX()][pos.getY()];
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
    public PathCell getEndCell() {
        return end;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getNext(final Position current) throws NoSuchElementException {
        /*var nextCords = path.getNext(new PositionImpl(current.getX(), current.getY()));
        if (nextCords == null) {
            throw new NoSuchElementException("This cell does not have a next one");
        }
        return nextCords;*/
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    private boolean isInArray(Position final pos) {
        return pos.getX() >= 0 && pos.getX() < size.getWidth() && pos.getY() >= 0 && pos.getX() < size.getHeight();
    }

}
