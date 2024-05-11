package it.unibo.towerdefense.models.enemies;

import java.util.Iterator;
import java.util.Optional;

/**
 * A Wave is rendered as an Iterator of Optional EnemyTypes, the Iterator is
 * advanced each cycle, the wave is considered over when !Iterator.hasNext().
 * If the returned optional is empty no enemy should spawn for that cycle.
 */
interface Wave extends Iterator<Optional<RichEnemyType>> {
}
