package it.unibo.towerdefense.controllers.map;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;
import it.unibo.towerdefense.models.engine.Size;
import it.unibo.towerdefense.models.map.Cell;
import it.unibo.towerdefense.models.map.Direction;
import it.unibo.towerdefense.models.map.GameMap;
import it.unibo.towerdefense.models.map.GameMapImpl;
import it.unibo.towerdefense.models.map.PathCell;

/**
 * Class to interact with map methods.
 */
public class MapControllerImpl implements MapController {

    private final GameMap map;
    private Position selected = null;

    /**
     *Constructor from size of map in two unit of measure.
     * @param size size of map in terms of game cells.
     */
    public MapControllerImpl(final Size size) {
        this.map = new GameMapImpl(size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogicalPosition getSpawnPosition() {
        var spawn = map.getSpawnCell();
        return new LogicalPosition(spawn.getCenter().getX() * Math.abs(spawn.getInDirection().vertical()),
            spawn.getCenter().getY() * Math.abs(spawn.getInDirection().orizontal()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void select(final Position position) {
        if (selected.equals(position)) {
            selected = null;
        } else {
            selected = position;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Position> getSelected() {
        return selected == null ? Optional.empty() : Optional.of(selected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<LogicalPosition> getNextPosition(final LogicalPosition pos, final int distanceToMove) {

        Position cellPos = new PositionImpl(pos.getCellX(), pos.getCellY());
        Cell cell = map.getCellAt(cellPos);
        if (!(cell instanceof PathCell)) {
            throw new IllegalArgumentException("position must belong to a PathCell");
        }
        PathCell pCell = (PathCell) cell;
        Direction in = pCell.getInDirection();
        //Direction out = pCell.getOutDirection();
        //int remaningDistance = distanceToMove;

        if ((pos.getX() * in.orizontal() + pos.getY() * in.vertical())
            % LogicalPosition.SCALING_FACTOR > LogicalPosition.SCALING_FACTOR / 2) {}// deve acora superare il centro cella

        for (int i = 0; i < distanceToMove / LogicalPosition.SCALING_FACTOR; i++) {
            try {
                cellPos = map.getNext(cellPos);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNextPosition'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void build(final int optionNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<String, Integer>> getBuildingOptions() {
        if (selected == null) {
            return List.of();
        }

         // TODO Auto-generated method stub
         throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

}
