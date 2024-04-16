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
    private final double cellWidth;
    private final double cellHeight;
    private Cell[][] map;

    /**
     * Constructor from size of map in terms of game space and screen space.
     * @param sizeInPixel size of map in terms of pixels
     * @param sizeInCell size of map in terms of Cells
     */
    public GameMapImpl(final Size sizeInPixel, final Size sizeInCell) {

        this.sizeInCell = sizeInCell;
        cellHeight = (double) sizeInPixel.getHeight() / sizeInCell.getHeight();
        cellWidth = (double) sizeInPixel.getWidth() / sizeInCell.getWidth();
        map = new Cell[sizeInCell.getHeight()][sizeInCell.getWidth()];

        final Coords end = new Coords(sizeInCell.getWidth(), random.nextInt(sizeInCell.getHeight()));
        this.path = new PathFactory().line(end);
        Coords temp = end;
        PathCell newCell;
        int distanceToEnd = 0;
        do {
            var topLeft = new PositionImpl((int) (cellWidth * temp.x()), (int) (cellHeight * temp.y()));
            var downRight = new PositionImpl((int) (cellWidth * (temp.x() + 1)) - 1,(int)  (cellHeight * (temp.y() + 1)) - 1);
            newCell = new PathCellImpl(temp, topLeft, downRight, distanceToEnd);
            map[temp.x()][temp.y()] = newCell;
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
        return map[(int) (position.getX() / cellWidth)][(int) (position.getY() / cellHeight)];
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
        var nextCords = path.getNext(new Coords(current.getI(), current.getJ()));
        if (nextCords == null) {
            throw new NoSuchElementException("This cell does not have a next one");
        }
        return (PathCell) map[nextCords.x()][nextCords.y()];
    }

}
