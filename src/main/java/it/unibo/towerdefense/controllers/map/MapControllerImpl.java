package it.unibo.towerdefense.controllers.map;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.defenses.DefenseType;
import it.unibo.towerdefense.controllers.defenses.DefensesController;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;
import it.unibo.towerdefense.models.engine.Size;
import it.unibo.towerdefense.models.map.BuildableCell;
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
    private final GameController gameController;
    private final DefensesController defensesController;
    private BuildableCell selected = null;
    private List<Entry<DefenseType, Integer>> options;

    /**
     *Constructor from size of map in two unit of measure.
     * @param size size of map in terms of game cells.
     * @param defensesController the defenses controller.
     * @param gameController the game controller.
     */
    public MapControllerImpl(final Size size, final DefensesController defensesController, final GameController gameController) {
        this.map = new GameMapImpl(size);
        this.gameController = gameController;
        this.defensesController = defensesController;
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
     * Returns the midpoint of in-direction's cell side.
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
        Cell c = map.getCellAt(position);
        if (c.equals(selected)) {
            selected = null;
        } else {
            if (c instanceof BuildableCell && ((BuildableCell) c).isBuildable()) {
                selected = (BuildableCell) c;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Position> getSelected() {
        return selected == null ? Optional.empty() : Optional.of(new PositionImpl(selected.getX(), selected.getY()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<LogicalPosition> getNextPosition(final LogicalPosition pos, final int distanceToMove) {

        Position cellPos = new PositionImpl(pos.getCellX(), pos.getCellY());
        Cell cell = map.getCellAt(cellPos);
        if (cell == null || !(cell instanceof PathCell)) {
            throw new IllegalArgumentException("position must belong to a PathCell");
        }
        LogicalPosition tempPos = pos;
        PathCell pCell = (PathCell) cell;
        Direction dir = pCell.getInDirection();
        int remaningDistance = distanceToMove;

        for (int i = 2; i > 0; i--) {

            int positionInCell = (pos.getX() * dir.orizontal() + pos.getY() * dir.vertical()) % LogicalPosition.SCALING_FACTOR;
            int factor = LogicalPosition.SCALING_FACTOR / i;

            if (positionInCell <= factor) {
                int distanceToTravel = factor - positionInCell;
                if (remaningDistance < distanceToTravel) {
                    return Optional.of(new LogicalPosition(
                        tempPos.getX() + remaningDistance * dir.orizontal(),
                        tempPos.getY() + remaningDistance * dir.vertical())
                    );
                }
                remaningDistance -=  distanceToTravel;
                tempPos = new LogicalPosition(
                    tempPos.getX() + distanceToTravel * dir.orizontal(),
                    tempPos.getY() + distanceToTravel * dir.vertical()
                );
                dir = pCell.getOutDirection();
            }
        }
        if (map.getEndCell().contains(tempPos)) {
            return Optional.empty();
        }
        return getNextPosition(tempPos, remaningDistance);
        /*while (remaningDistance >= LogicalPosition.SCALING_FACTOR) {
            remaningDistance -= LogicalPosition.SCALING_FACTOR;
            try {
                cellPos = map.getNext(cellPos);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        pCell = (PathCell) map.getCellAt(cellPos);
        Direction in = pCell.getInDirection();
        int halfScalig = LogicalPosition.SCALING_FACTOR / 2;
        if (remaningDistance < halfScalig) {
            dir = in;
            halfScalig = 0;
        } else {
            dir = pCell.getOutDirection();
            remaningDistance -= halfScalig;
        }
        return Optional.of(new LogicalPosition(
                pCell.getX() * LogicalPosition.SCALING_FACTOR + remaningDistance * dir.orizontal() + halfScalig * in.orizontal(),
                pCell.getY() * LogicalPosition.SCALING_FACTOR + remaningDistance * dir.vertical() + halfScalig * in.vertical()
            ));*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void build(final int optionNumber) {
        if (selected == null || options.isEmpty()) {
            throw new IllegalStateException("Can't build!");
        }
        defensesController.buildDefense(options.get(optionNumber).getKey(), selected.getCenter());
    }

    private  List<Map.Entry<DefenseType, Integer>> requestBuildinOption() {
        if (selected == null) {
            return List.of();
        }
        return defensesController.getBuildables(selected.getCenter()).entrySet().stream().toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Pair<String, Integer>> getBuildingOptions() {
        options = requestBuildinOption();
        return options.stream().map(e -> Pair.of(e.getKey().toString(), e.getValue())).toList();
    }

}
