package it.unibo.towerdefense.model.map;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseDescription;
import it.unibo.towerdefense.commons.dtos.map.BuildingOption;
import it.unibo.towerdefense.commons.dtos.map.BuildingOptionImpl;
import it.unibo.towerdefense.commons.dtos.map.CellInfo;
import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.engine.Position;
import it.unibo.towerdefense.commons.engine.PositionImpl;
import it.unibo.towerdefense.commons.engine.Size;
import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.model.defenses.DefenseManager;
import it.unibo.towerdefense.model.game.GameManager;

/**
 * Class to interact with map methods.
 */
public class MapManagerImpl implements MapManager {

    private final GameMap map;
    private BuildableCell selected;
    private List<DefenseDescription> options;
    private DefenseManager defenses;
    private GameManager game;


    /**
     *Constructor from size of map.
     * @param size size of map in terms of game cells.
     */
    public MapManagerImpl(final Size size) {
        this.map = new GameMapImpl(size);
    }

    /**
     *Constructor from jsondata of map.
     * @param jsondata JSON representation of GameMap Object.
     */
    public MapManagerImpl(final String jsondata) {
        this.map = GameMapImpl.fromJson(jsondata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(final ModelManager mm) {
        defenses = mm.getDefenses();
        game = mm.getGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathVector getSpawnPosition() {
        final PathCell spawnCell = map.getSpawnCell();
        return new PathVector(
            spawnCell.inSideMidpoint(),
            spawnCell.getInDirection().asDirection(),
            spawnCell.distanceToEnd() * LogicalPosition.SCALING_FACTOR
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
        final Cell c = map.getCellAt(position);
        if (c == null) {
            return;
        }
        if (c.equals(selected)) {
            defenseSelection(false);
            selected = null;
        } else {
            if (c instanceof BuildableCell && ((BuildableCell) c).isBuildable()) {
                selected = (BuildableCell) c;
                defenseSelection(true);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathVector getNextPosition(final LogicalPosition pos, final int distanceToMove) {

        final Position cellPos = new PositionImpl(pos.getCellX(), pos.getCellY());
        final Cell cell = map.getCellAt(cellPos);
        if (!(cell instanceof PathCell)) {
            throw new IllegalArgumentException("position must belong to a PathCell");
        }
        final PathCell pCell = (PathCell) cell;
        int distanceToEnd = pCell.distanceToEnd() * LogicalPosition.SCALING_FACTOR;
        MapDirection dir = pCell.getInDirection();
        int remainingDistance = distanceToMove;
        LogicalPosition tempPos = pos;

        for (int i = 2; i > 0; i--) {

            final int positionInCell = realModule(
                pos.getX() * dir.horizontal() + pos.getY() * dir.vertical(),
                LogicalPosition.SCALING_FACTOR / 2 * i
            );
            if (i == 2 || dir != pCell.getInDirection()) {
                distanceToEnd -= positionInCell;
            }
            if (distanceToEnd < distanceToMove) {
                return new PathVector(getEndPosition(), map.getEndCell().getOutDirection().asDirection(), 0);
            }
            final int factor = LogicalPosition.SCALING_FACTOR / i;
            if (positionInCell < factor) {
                final int distanceToTravel = factor - positionInCell;
                if (remainingDistance <= distanceToTravel) {
                    tempPos = addDistance(tempPos, dir, remainingDistance);
                    return new PathVector(tempPos, dir.asDirection(), distanceToEnd - remainingDistance);
                }
                remainingDistance -=  distanceToTravel;
                tempPos = addDistance(tempPos, dir, distanceToTravel);
            }
            dir = pCell.getOutDirection();
        }
        return getNextPosition(tempPos, remainingDistance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void build(final int optionNumber) {
        if (selected == null || optionNumber < 0) {
            throw new IllegalStateException("ERROR, can't build!");
        }
        if (optionNumber > options.size() - 1) {
            game.addMoney(defenses.disassembleDefense(selected.getCenter()));
        } else {
            final var choice = options.get(optionNumber);
            if (!game.purchase(choice.getCost())) {
                throw new IllegalArgumentException("Not enough money!");
            }
            try {
                defenses.buildDefense(optionNumber, selected.getCenter());
                defenseSelection(true);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuildingOption> getBuildingOptions() {
        final List<BuildingOption> l = new ArrayList<>();

        if (updateBuildingOption()) {
            final var optDef = defenses.getDefenseAt(selected.getCenter());
            options.stream().forEach(dd ->
                l.add(new BuildingOptionImpl(dd, game.isPurchasable(dd.getCost()))));
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
                    public boolean isAvailable() {
                        return true;
                    }

                    @Override
                    public String getDescription() {
                        return "Sell this defense.";
                    }

                });
            }
        }
        return l;
    }

    /**
     * {@inheritDoc}
     */
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

    private void defenseSelection(final boolean isSelected) {
        defenses.setSelectedDefense(selected.getCenter(), isSelected);
    }

    private boolean updateBuildingOption() {
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
        return new LogicalPosition(pos.getX() + distance * dir.horizontal(), pos.getY() + distance * dir.vertical());
    }
}
