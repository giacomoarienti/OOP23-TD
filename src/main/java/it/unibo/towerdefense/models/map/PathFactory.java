package it.unibo.towerdefense.models.map;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


/**
 * Class that generate different tipes of path.
 */
public class PathFactory {


    private int turnRight(final int ind) {
        return turnLeft(turnLeft(turnLeft(ind)));
    }

    private int turnLeft(final int ind) {
        return (ind + 1) % Direction.values().length;
    }


    /**
     *Return a path corresponding to an orizontal line from left to right.
     * @return the path
     */
    public Iterator<Direction> line() {
        return repeatPattern(List.of(Direction.E));
    }

    /**
     *Return a path corresponding to a topLeft-downRight diagonal.
     * @return the path
     */
    public Iterator<Direction> diagonal() {
        return repeatPattern(List.of(Direction.E, Direction.S));
    }

    private Iterator<Direction> repeatPattern(final List<Direction> list) {
        return Stream.generate(() -> list).flatMap(l -> l.stream()).iterator();
    }

    /*int distanceToEnd = 0;
        final PathCell end = new PathCellImpl(random.nextInt(sizeInCell.getHeight()), sizeInCell.getWidth() - 1, distanceToEnd);
        PathCell temp = end;
        static final int MAX_STRAIGHT = 5;
        int directionIndex = 3;
        int sameDirectionCounter = 0;



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

    public Path line(final Size sizeInCell, int spawnX, int  spawnY) {
        return new Path() {
        };
    }*/

}
