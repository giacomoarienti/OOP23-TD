package it.unibo.towerdefense.controllers.map;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.commons.dtos.DefenseDescription;
import it.unibo.towerdefense.controllers.defenses.DefenseType;
import it.unibo.towerdefense.controllers.mediator.ControllerMediator;
import it.unibo.towerdefense.models.engine.Position;
import it.unibo.towerdefense.models.engine.PositionImpl;
import it.unibo.towerdefense.models.engine.Size;
import it.unibo.towerdefense.models.map.BuildableCell;
import it.unibo.towerdefense.models.map.Cell;
import it.unibo.towerdefense.models.map.Direction;
import it.unibo.towerdefense.models.map.GameMap;
import it.unibo.towerdefense.models.map.GameMapImpl;
import it.unibo.towerdefense.models.map.PathCell;
import it.unibo.towerdefense.views.graphics.GameRenderer;

/**
 * Class to interact with map methods.
 */
public class MapControllerImpl implements MapController {

    private final GameMap map;
    private final ControllerMediator master;
    //private final GameRenderer gameRenderer;
    private BuildableCell selected = null;
    private List<DefenseDescription> options;


    /**
     *Constructor from size of map and the mediator.
     * @param size size of map in terms of game cells.
     * @param masterController the mediator controller.
     */
    public MapControllerImpl(Size size, final ControllerMediator masterController) {
        try {
            this.map = new GameMapImpl(size);
        } catch (IllegalArgumentException e) {
            throw e;
        }
        this.master = masterController;
    }

    /**
     *Constructor from jasondata of map and the mediator.
     * @param jsondata JSON representation of GameMap Object.
     * @param masterController the mediator controller.
     */
    public MapControllerImpl(
        final String jsondata,
        final ControllerMediator masterController
    ) {
        this.map = GameMapImpl.fromJson(jsondata);
        this.master = masterController;
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
        return map.getSpawnCell().inSideMidpoint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LogicalPosition getEndPosition() {
        return map.getEndCell().inSideMidpoint();
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
            if (c instanceof BuildableCell) {
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

        if (map.getEndCell().contains(pos)) {
            return Optional.empty();
        }
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

            int positionInCell = realModule(
                pos.getX() * dir.orizontal() + pos.getY() * dir.vertical(),
                LogicalPosition.SCALING_FACTOR / 2 * i
            );
            int factor = LogicalPosition.SCALING_FACTOR / i;

            if (positionInCell < factor) {
                int distanceToTravel = factor - positionInCell;
                if (remaningDistance < distanceToTravel) {
                    tempPos = addDistance(tempPos, dir, remaningDistance);
                    if (map.getEndCell().contains(tempPos)) {
                        return Optional.empty();
                    }
                    return Optional.of(tempPos);
                }
                remaningDistance -=  distanceToTravel;
                tempPos = addDistance(tempPos, dir, distanceToTravel);
            }
            dir = pCell.getOutDirection();
        }
        return getNextPosition(tempPos, remaningDistance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void build(final int optionNumber) throws IOException {
        if (selected == null || options.isEmpty() || optionNumber > options.size() - 1) {
            throw new IllegalStateException("ERROR, can't build!");
        }
        var choice = options.get(optionNumber);
        if (choice.getName().equals(DefenseType.NOTOWER.name())) {
            master.getGameController().addMoney(master.getDefensesController().disassembleDefense(selected.getCenter()));
            return;
        }
        if (!master.getGameController().purchase(choice.getCost())) {
            throw new IllegalArgumentException("Not enought money!");
        }
        master.getDefensesController().buildDefense(optionNumber, selected.getCenter());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DefenseDescription> getBuildingOptions() {
        if (updateBuildinOption()) {
            return List.copyOf(options);
        }
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return map.toJSON();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final GameRenderer renderer) {
        //TODO;
    }

    private boolean updateBuildinOption() {
        if (selected == null) {
            return false;
        }
        try {
            this.options = master.getDefensesController().getBuildables(selected.getCenter());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Do module operation as it should work, also if operands are negatives.
     * @param a dividend
     * @param b divisor
     * @return rest of integer division
     */
    private static int realModule(final int a, final int b) {
        return (a % b + b) % b;
    }

    private static LogicalPosition addDistance(final LogicalPosition pos, final Direction dir, final int distance) {
        return new LogicalPosition(pos.getX() + distance * dir.orizontal(), pos.getY() + distance * dir.vertical());
    }
}
