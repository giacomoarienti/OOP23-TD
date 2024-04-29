package it.unibo.towerdefense.models.map;

import java.util.Iterator;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;
import it.unibo.towerdefense.models.engine.Size;
import it.unibo.towerdefense.models.engine.SizeImpl;

/**
 * Class that implements GameMap methods, and generate the map.
 */
public class GameMapImpl implements GameMap {


    private final Size size;
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

        final Iterator<Direction> path = new PathFactory().diagonal();
        Position pos = new PositionImpl(0, random.nextInt(size.getHeight() / 2));
        spawn = new PathCellImpl(pos, path.next(), path.next());
        PathCell newCell = spawn;

        while (isInMap(pos)) {
            map[pos.getX()][pos.getY()] = newCell;
            pos.add(newCell.getOutDirection().asPosition());
            newCell = new PathCellImpl(pos, newCell.getOutDirection(), path.next());
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

    public GameMapImpl(final Stream<Cell> mapStream, final Size size, final PathCell spawn, final PathCell end) {
        this.size = size;
        this.spawn = spawn;
        this.end = end;
        this.map = new Cell[size.getHeight()][size.getWidth()];
        mapStream.forEach(c -> map[c.getX()][c.getY()] = c);
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
        if (!isInMap(pos)) {
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

    private boolean isInMap(final Position pos) {
        return pos.getX() >= 0 && pos.getX() < size.getWidth() && pos.getY() >= 0 && pos.getY() < size.getHeight();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String toJSON() {
        final JSONObject jObj = new JSONObject();
        jObj.put("spawn", end.toJSON()).put("end", end.toJSON())
            .put("height", size.getHeight()).put("width", size.getWidth());

        final JSONArray jArray = new JSONArray();
        toStream(map, size).forEach(c -> c.toJSON()); //TODO
        jObj.put("map", jArray);
        return jObj.toString();
    }

    public static GameMap fromJson(final String jsonData) {
        final JSONObject jObj = new JSONObject(jsonData);
        final JSONArray jArray = jObj.optJSONArray("map");
        return new GameMapImpl(
            IntStream.range(0, jArray.length()).mapToObj(i -> (Cell) jArray.get(i)), //TODO
            new SizeImpl(jObj.getInt("height"), jObj.getInt("width")),
            PathCellImpl.fromJson(jObj.getString("spawn")),
            PathCellImpl.fromJson(jObj.getString("end"))
        );
    }

    private <T> Stream<T> toStream(T[][] array, Size size) {
        return Stream.iterate(0, x -> x < size.getWidth(), x -> x + 1)
            .flatMap(x -> Stream.iterate(
                new PositionImpl(x, 0), p -> p.getY() < size.getHeight(), p -> new PositionImpl(x, p.getY() + 1)))
            .map(p -> array[p.getX()][p.getY()]);
    }
}
