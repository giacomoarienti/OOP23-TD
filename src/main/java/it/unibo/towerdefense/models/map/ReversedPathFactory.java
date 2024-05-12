package it.unibo.towerdefense.models.map;

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

    private static final List<Direction> D_LIST = List.of(Direction.values());

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

    /**
     * Returns a random generate path that never go back or cross itself.
     * @param size size of map.
     * @param direciton initial direction of path.
     * @return the path as list of directions, where first is direction.
     */
    public Iterator<Direction> generate(final Size size, final Direction direciton) {
        return Stream.iterate(opposite(direciton), new UnaryOperator<Direction>() {

            private final Random random = new Random();
            private int n = random.nextInt(2) * 2;
            private int counter = 0;

            @Override
            public Direction apply(final Direction d) {
                if (counter < random.nextInt(
                    Math.abs(direciton.orizontal() * size.getHeight() + direciton.vertical() * size.getWidth()) / 4
                    )) {
                    counter++;
                    return d;
                }
                counter = 0;
                n = (n + 1) % 4;
                return n < 2 ? turnLeft(d) : turnRight(d);
            }

        }).map(d -> opposite(d)).peek(d -> System.out.println(d)).iterator();
    }

    private Iterator<Direction> repeatPattern(final List<Direction> list) {
        return Stream.generate(() -> list).flatMap(l -> l.stream().map(d -> opposite(d))).iterator();
    }

    private Direction turnLeft(final Direction d) {
        return D_LIST.get((D_LIST.indexOf(d) + 1) % D_LIST.size());
    }

    private Direction turnRight(final Direction d) {
        return turnLeft(turnLeft(turnLeft(d)));
    }

    private Direction opposite(Direction d) {
        return turnLeft(turnLeft(d));
    }

}
