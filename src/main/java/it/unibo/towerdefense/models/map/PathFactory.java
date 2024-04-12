package it.unibo.towerdefense.models.map;

import java.util.List;
import java.util.stream.Stream;

/**
 * Class that generate different tipes of path.
 */
public class PathFactory {

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
    public Path line(final Coords end) {
        return new Path() {

            private List<Coords> list = Stream.iterate(0, i -> i < end.x(), i -> i + 1).map(i -> new Coords(i, end.y())).toList();

            @Override
            public Coords getNext(final Coords coords) {
                int i = list.indexOf(coords);
                if (i < list.size() - 1) {
                    return list.get(i + 1);
                }
                return null;
            }

            @Override
            public Coords getPrevious(final Coords coords) {
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
