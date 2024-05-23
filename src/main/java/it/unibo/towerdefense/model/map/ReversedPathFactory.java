package it.unibo.towerdefense.model.map;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import it.unibo.towerdefense.commons.engine.Size;


/**
 * Class that generate different tipes of path.
 */
public class ReversedPathFactory {


    /**
     *Return a path corresponding to an orizontal line from left to right.
     * @return the path
     */
    public Iterator<MapDirection> line() {
        return repeatPattern(List.of(MapDirection.E));
    }

    /**
     *Return a path corresponding to a topLeft-downRight diagonal.
     * @return the path
     */
    public Iterator<MapDirection> diagonal() {
        return repeatPattern(List.of(MapDirection.W, MapDirection.S));
    }

    /**
     * Returns a random generate path that never go back or cross itself.
     * @param size size of map.
     * @param direction initial direction of path.
     * @return the path as list of directions, where first is direction.
     */
    public Iterator<MapDirection> generate(final Size size, final MapDirection direction) {
        return Stream.iterate(opposite(direction), new UnaryOperator<MapDirection>() {

            private final Random random = new Random();
            private int n = random.nextInt(2) * 2;
            private int counter = 0;

            @Override
            public MapDirection apply(final MapDirection d) {
                if (counter
                < random.nextInt(Math.abs(direction.horizontal() * size.getHeight() + direction.vertical() * size.getWidth())
                / (d == direction ? 4 : 2)
                )) {
                    counter++;
                    return d;
                }
                counter = 0;
                n = (n + 1) % 4;
                return n < 2 ? turnLeft(d) : turnRight(d);
            }

        }).map(d -> opposite(d)).iterator();
    }

    /**
     * Repeat cyclically the given pattern.
     * @param list list of directions to repeat.
     * @return iterator of repeated directions.
     */
    public Iterator<MapDirection> repeatPattern(final List<MapDirection> list) {
        return Stream.generate(() -> list).flatMap(l -> l.stream().map(d -> opposite(d))).iterator();
    }

    private MapDirection turnLeft(final MapDirection d) {
        return MapDirection.values()[(d.ordinal() + 1) % 4];
    }

    private MapDirection turnRight(final MapDirection d) {
        return turnLeft(turnLeft(turnLeft(d)));
    }

    private MapDirection opposite(final MapDirection d) {
        return turnLeft(turnLeft(d));
    }

}
