package it.unibo.towerdefense.models.map;

import java.util.Iterator;
import java.util.Random;
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
    private static final int MAX_X_SIZE = 100;
    private static final int MAX_Y_SIZE = 100;
    private Cell[][] map;

    /**
     * Constructor from size of map in terms of game space and screen space.
     * @param size size of map in terms of Cells
     */
    public GameMapImpl(final Size size) {

        if (size.getHeight() > MAX_Y_SIZE || size.getWidth() > MAX_X_SIZE) {
            throw new IllegalArgumentException("Max dimension allowed are: " + MAX_X_SIZE + ", " + MAX_Y_SIZE);
        }
        this.size = size;
        map = new Cell[size.getHeight()][size.getWidth()];
        final Iterator<Direction> path = new PathFactory().generate(size, Direction.E);
        Position pos = new PositionImpl(0, random.nextInt(size.getHeight() / 4, size.getHeight() / 4 * 3));
        spawn = new PathCellImpl(pos, path.next(), path.next());
        PathCell newCell = spawn;
        System.out.println(newCell);

        while (isInMap(pos)) {
            map[pos.getX()][pos.getY()] = newCell;
            pos.add(newCell.getOutDirection().asPosition());
            newCell = new PathCellImpl(pos, newCell.getOutDirection(), path.next());
        }
        System.out.println(newCell);
        end = newCell;

        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                if (map[i][j] == null) {
                    map[i][j] = new BuildableCellImpl(new PositionImpl(i, j), random.nextInt(OBSTACLE_RATE) != 0);
                }
            }
        }

    }

    /**
     * Constructor from all map info.
     * @param path stream of Cells of path.
     * @param buildable stream of buildable Cells.
     * @param size size of map.
     * @param spawn the spawn cell.
     * @param end the end of path.
     */
    public GameMapImpl(final Stream<PathCell> path, final Stream<BuildableCell> buildable,
        final Size size, final PathCell spawn, final PathCell end) {

        this.size = size;
        this.spawn = spawn;
        this.end = end;
        this.map = new Cell[size.getHeight()][size.getWidth()];
        path.forEach(c -> map[c.getX()][c.getY()] = c);
        buildable.forEach(c -> map[c.getX()][c.getY()] = c);
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
        final JSONArray jArrayPath = new JSONArray();
        final JSONArray jArrayBuildable = new JSONArray();

        jObj.put("spawn", end.toJSON()).put("end", end.toJSON())
            .put("height", size.getHeight()).put("width", size.getWidth());

        toStream(map, size).forEach(c -> {
            if (c instanceof PathCell) {
                jArrayPath.put(((PathCellImpl) c).toJSON());
            }
            else {
                jArrayBuildable.put(((BuildableCellImpl) c).toJSON());
            }
        });
        jObj.put("path", jArrayPath);
        jObj.put("buildable", jArrayBuildable);
        return jObj.toString();
    }

    /**
     * Returns the GameMap object from JSON string.
     * @param jsonData the JSON representation
     * @return the GameMap object
     */
    public static GameMap fromJson(final String jsonData) {
        final JSONObject jObj = new JSONObject(jsonData);
        final JSONArray jArrayPath = jObj.getJSONArray("path");
        final JSONArray jArrayBuildable = jObj.getJSONArray("buildable");
        return new GameMapImpl(
            itStream(jArrayPath.length()).map(i -> PathCellImpl.fromJson(jArrayPath.getString(i))),
            itStream(jArrayBuildable.length()).map(i -> BuildableCellImpl.fromJson(jArrayBuildable.getString(i))),
            new SizeImpl(jObj.getInt("height"), jObj.getInt("width")),
            PathCellImpl.fromJson(jObj.getString("spawn")),
            PathCellImpl.fromJson(jObj.getString("end"))
        );
    }

    /**
     * Create a stream of T objects from T bidimensional array.
     * @param <T> tipe of array elements
     * @param array
     * @param size size of array
     * @return Stream of array elements.
     */
    private static <T> Stream<T> toStream(final T[][] array, final Size size) {
        return Stream.iterate(0, x -> x < size.getWidth(), x -> x + 1)
            .flatMap(x -> Stream.iterate(
                new PositionImpl(x, 0), p -> p.getY() < size.getHeight(), p -> new PositionImpl(x, p.getY() + 1)))
            .map(p -> array[p.getX()][p.getY()]);
    }

    /**
     * Create a Stream of Integers iterating from 0 to bound - 1.
     * @param bound bound of iteration
     * @return Stream of Integers
     */
    private static Stream<Integer> itStream(final int bound) {
        return Stream.iterate(0, x -> x < bound, x -> x + 1);
    }
}
