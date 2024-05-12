package it.unibo.towerdefense.controllers.map;

import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

/**
 * Record that describes a position of a enemy on the path.
 * @param position position
 * @param direction direction in this position
 * @param distanceToEnd distance from end of path
 */
public record PathVector(LogicalPosition position, Direction direction, int distanceToEnd) {};