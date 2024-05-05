package it.unibo.towerdefense.models.enemies;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;

/**
 * {@inheritDoc}.
 */
public class EnemyCollectionImpl implements EnemyCollection {

    private final Set<Enemy> enemies;
    private final GameController gc;
    private final MapController map;

    /**
     * Constructor for the class.
     *
     * @param gc  GameController to advance wave and add money on enemy defeat
     * @param map handle to get next positions
     */
    public EnemyCollectionImpl(final GameController gc, final MapController map) {
        this.gc = gc;
        this.map = map;
        this.enemies = new HashSet<>();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void move() {
        final List<Enemy> dead = enemies.stream().filter(
                e -> {
                    Optional<LogicalPosition> next = map.getNextPosition(e.getPosition(), e.getSpeed());
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
        gc.addMoney(which.getValue());
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
