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

}
