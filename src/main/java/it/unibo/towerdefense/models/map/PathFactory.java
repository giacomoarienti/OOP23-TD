package it.unibo.towerdefense.models.map;

import java.util.List;
import java.util.stream.Stream;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;

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
     * @param end coordinates of last point of line.
     * @return the path
     */
    public Path line(final Position end) {
        return new Path() {

            private List<Position> list = Stream.iterate(0, i -> i < end.getX(), i -> i + 1)
                .map(i -> (Position) new PositionImpl(i, end.getY())).toList();

            @Override
            public Position getNext(final Position coords) {
                int i = list.indexOf(coords);
                if (i < list.size() - 1) {
                    return list.get(i + 1);
                }
                return null;
            }

            @Override
            public Position getPrevious(final Position coords) {
                int i = list.indexOf(coords);
                if (i > 0) {
                    return list.get(i - 1);
                }
                return null;
            }

        };
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
