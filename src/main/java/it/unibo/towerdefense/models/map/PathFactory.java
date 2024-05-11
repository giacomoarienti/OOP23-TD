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
public class PathFactory {

    private final static List<Direction> D_LIST = List.of(Direction.values());

    private int turnRight(final int ind) {
        return turnLeft(turnLeft(turnLeft(ind)));
    }

    private int turnLeft(final int ind) {
        return (ind + 1) % D_LIST.size();
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

    public Iterator<Direction> generate(Size size, Direction direciton) {
        return Stream.iterate(D_LIST.indexOf(direciton), new UnaryOperator<Integer>() {

            private final Random random = new Random();
            int n = random.nextInt(2) * 2;
            int counter = 0;

            @Override
            public Integer apply(Integer d) {
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

        }).map(i -> D_LIST.get(i)).peek(d -> System.out.println(d)).iterator();
    }

    private Iterator<Direction> repeatPattern(final List<Direction> list) {
        return Stream.generate(() -> list).flatMap(l -> l.stream()).iterator();
    }

}
