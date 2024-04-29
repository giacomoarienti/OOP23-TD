package it.unibo.towerdefense.models.map;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;


/**
 * Class that generate different tipes of path.
 */
public class PathFactory {

    class Limit {

        private final BiPredicate<Integer, Integer> predicate;
        private final Function<Position, Integer> coord;
        private int value;

        Limit(Function<Position, Integer> coord, BiPredicate<Integer, Integer> p) {
            predicate = p;
            this.coord = coord;
        }

        public boolean update(Position pos) {
            if (predicate.test(coord.apply(pos), value)) {
                value = coord.apply(pos);
                return true;
            }
            return false;
        }

        public int get() {
            return value;
        }

        public void set(int value) {
            this.value = value;
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

    public Iterator<Direction> generate() {

        return Stream.iterate(0, new UnaryOperator<Integer>() {

            private final Random random = new Random();
            private final List<Limit> limits = List.of(
                new Limit(p -> p.getX(), (p,v) -> p > v),
                new Limit(p -> p.getX(), (p,v) -> p < v && p > 0),
                new Limit(p -> p.getY(), (p,v) -> p > v && p < 20),
                new Limit(p -> p.getY(), (p,v) -> p < v && p > 0)
            );
            Position pos = new PositionImpl(0, 0);
            Direction currentDirection;
            int sameDirectionCounter = 0;

            @Override
            public Integer apply(Integer d) {
                int dir = d;
                Position newPos = pos;
                currentDirection = Direction.values()[dir];
                newPos.add(Direction.values()[dir].asPosition());
                while (!limits.get(dir).update(newPos) ) {
                    dir = turnLeft(dir);
                }
                //TODO
                return dir;
            }

        }).map(i -> Direction.values()[i]).iterator();
    }

    private Iterator<Direction> repeatPattern(final List<Direction> list) {
        return Stream.generate(() -> list).flatMap(l -> l.stream()).iterator();
    }

}
