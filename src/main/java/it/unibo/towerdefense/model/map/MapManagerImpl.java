package it.unibo.towerdefense.model.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.dtos.map.BuildingOption;
import it.unibo.towerdefense.commons.dtos.map.BuildingOptionImpl;
import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.PositionImpl;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.model.defenses.Defense;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.defenses.DefenseManagerImpl;
import it.unibo.towerdefense.model.game.GameManager;

/**
 * Class to interact with map methods.
 */
public class MapManagerImpl implements MapManager {

    private final GameMap map;
    private BuildableCell selected = null;
    private List<Defense> options;
    private DefenseManager defenses;
    private GameManager game;


    /**
     *Constructor from size of map and the mediator.
     * @param size size of map in terms of game cells.
     * @param masterController the mediator controller.
     */
    public MapManagerImpl(Size size) {
        try {
            this.map = new GameMapImpl(size);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     *Constructor from jasondata of map and the mediator.
     * @param jsondata JSON representation of GameMap Object.
     * @param masterController the mediator controller.
     */
    public MapManagerImpl(final String jsondata) {
        this.map = GameMapImpl.fromJson(jsondata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(ModelManager mm) {
        defenses = mm.getDefenses();
        game = mm.getGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathVector getSpawnPosition() {
        final PathCell spawCell = map.getSpawnCell();
        return new PathVector(
            spawCell.inSideMidpoint(),
            spawCell.getInDirection().asDirection(),
            spawCell.distanceToEnd() * LogicalPosition.SCALING_FACTOR
        );
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
        if (c == null) {
            return ;
        }
        if (c.equals(selected)) {
            selected = null;
        } else {
            if (c instanceof BuildableCell && ((BuildableCell)c).isBuildable()) {
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
    public PathVector getNextPosition(final LogicalPosition pos, final int distanceToMove) {

        Position cellPos = new PositionImpl(pos.getCellX(), pos.getCellY());
        Cell cell = map.getCellAt(cellPos);
        if (cell == null || !(cell instanceof PathCell)) {
            throw new IllegalArgumentException("position must belong to a PathCell");
        }
        LogicalPosition tempPos = pos;
        PathCell pCell = (PathCell) cell;
        MapDirection dir = pCell.getInDirection();
        int remaningDistance = distanceToMove;
        int distanceToEnd = pCell.distanceToEnd() * LogicalPosition.SCALING_FACTOR;

        for (int i = 2; i > 0; i--) {

            int factor = LogicalPosition.SCALING_FACTOR / i;
            int positionInCell = realModule(
                pos.getX() * dir.orizontal() + pos.getY() * dir.vertical(),
                LogicalPosition.SCALING_FACTOR / 2 * i
            );
            if (i == 2 || dir != pCell.getInDirection()) {
                distanceToEnd -= positionInCell;
            }
            if (distanceToEnd < distanceToMove) {
                return new PathVector(getEndPosition(), map.getEndCell().getOutDirection().asDirection(), 0);
            }
            if (positionInCell < factor) {
                int distanceToTravel = factor - positionInCell;
                if (remaningDistance <= distanceToTravel) {
                    tempPos = addDistance(tempPos, dir, remaningDistance);
                    return new PathVector(tempPos, dir.asDirection(), distanceToEnd - remaningDistance);
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
    public void build(final int optionNumber) {
        if (selected == null || options.isEmpty() || optionNumber < 0) {
            throw new IllegalStateException("ERROR, can't build!");
        }
        if (optionNumber > options.size() - 1) {
            game.addMoney(defenses.disassembleDefense(selected.getCenter()));
        } else {
            var choice = options.get(optionNumber);
            if (!game.purchase(choice.getBuildingCost())) {
                throw new IllegalArgumentException("Not enought money!");
            }
            try {
                defenses.buildDefense(optionNumber, selected.getCenter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuildingOption> getBuildingOptions() {
        List<BuildingOption> l = new ArrayList<>();
        var optDef = defenses.getDefenseAt(selected.getCenter());

        if (updateBuildinOption()) {
            options.stream().forEach(dd ->
                l.add(new BuildingOptionImpl(DefenseManagerImpl.getDescriptionFrom(dd), game.isPurchasable(dd.getBuildingCost()))));
        }
        if (optDef.isPresent()) {
            l.add(new BuildingOption() {

                @Override
                public String getText() {
                    return "Sell";
                }

                @Override
                public String getCost() {
                    return Integer.toString(optDef.get().getSellingValue());
                }

                @Override
                public boolean isPurchasable() {
                    return true;
                }

            });
        }
        return l;
    }

    @Override
    public Stream<CellInfo> getMap() {
        return map.getMap().map(c -> new CellInfo() {

            @Override
            public LogicalPosition getPosition() {
                return c.getCenter();
            }

            @Override
            public boolean isPathCell() {
                return c instanceof PathCell;
            }

            @Override
            public boolean isBuildable() {
                return !isPathCell() && ((BuildableCell) c).isBuildable();
            }

            @Override
            public boolean isSelected() {
                return c.equals(selected);
            }

            @Override
            public Pair<Direction, Direction> getDirections() {
                if (isPathCell()) {
                    return Pair.of(
                        ((PathCell) c).getInDirection().asDirection(),
                        ((PathCell) c).getOutDirection().asDirection()
                    );
                }
                throw new UnsupportedOperationException("This does not represent a PathCell");
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJSON() {
        return map.toJSON();
    }

    private boolean updateBuildinOption() {
        if (selected == null) {
            return false;
        }
        try {
            this.options = defenses.getBuildables(selected.getCenter());
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

    private static LogicalPosition addDistance(final LogicalPosition pos, final MapDirection dir, final int distance) {
        return new LogicalPosition(pos.getX() + distance * dir.orizontal(), pos.getY() + distance * dir.vertical());
    }
}
