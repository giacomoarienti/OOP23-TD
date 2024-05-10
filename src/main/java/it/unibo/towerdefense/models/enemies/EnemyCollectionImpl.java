package it.unibo.towerdefense.models.enemies;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;

/**
 * {@inheritDoc}.
 */
public class EnemyCollectionImpl implements EnemyCollection {

    private final Set<Enemy> enemies;
    private final BiFunction<LogicalPosition, Integer, Optional<LogicalPosition>> posFunction;

    /**
     * Constructor for the class.
     *
     * @param posFunction a function that takes as argument the current position of
     *                    an enemy and how much it should adance and gives back an
     *                    optional containing the new position or an empty optional
     *                    if the enemy has reached the end of the map
     */
    EnemyCollectionImpl(final BiFunction<LogicalPosition, Integer, Optional<LogicalPosition>> posFunction) {
        this.posFunction = posFunction;
        this.enemies = new HashSet<>();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void move() {
        final List<Enemy> dead = enemies.stream().filter(
                e -> {
                    Optional<LogicalPosition> next = posFunction.apply(e.getPosition(), e.getSpeed());
                    if (next.isEmpty()) {
                        return true;
                    } else {
                        e.move(next.get());
                        return false;
                    }
                }).toList();
        dead.forEach(e -> e.die());
    }

    /**
     * Method called by the dying enemies to notify the collection of their death.
     */
    @Override
    public void notify(final Enemy which) {
        enemies.remove(which);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<Enemy> getEnemies() {
        return Set.copyOf(enemies);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<EnemyInfo> getEnemiesInfo() {
        return enemies.stream().map(e -> e.info()).toList();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void add(final Enemy e) {
        if (e.isDead()) {
            throw new IllegalArgumentException("Can't add a dead enemy to the collection");
        }
        enemies.add(e);
        e.addDeathObserver(this);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean areDead() {
        return enemies.isEmpty();
    }

}
