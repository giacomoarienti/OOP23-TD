package it.unibo.towerdefense.model.enemies;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.patterns.Observer;

/**
 * {@inheritDoc}.
 */
class EnemyCollectionImpl implements EnemyCollection {
    private final Set<RichEnemy> enemies;
    private final BiFunction<? super EnemyPosition, Integer, Optional<EnemyPosition>> posFunction;
    private final Set<Observer<Enemy>> enemyDeathObservers;

    /**
     * Constructor for the class.
     *
     * @param posFunction a function that takes as argument the current position of
     *                    an enemy and how much it should adance and gives back an
     *                    optional containing the new position or an empty optional
     *                    if the enemy has reached the end of the map
     */
    EnemyCollectionImpl(final BiFunction<? super EnemyPosition, Integer, Optional<EnemyPosition>> posFunction) {
        this.posFunction = posFunction;
        this.enemies = new HashSet<>();
        this.enemyDeathObservers = new HashSet<>();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void move() {
        final List<RichEnemy> dead = enemies.parallelStream().filter(
                e -> {
                    Optional<EnemyPosition> next = posFunction.apply(e.getPosition(), e.getSpeed());
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
     * {@inheritDoc}.
     */
    @Override
    public void notify(final RichEnemy which) {
        enemies.remove(which);
        enemyDeathObservers.forEach(o -> o.notify(which));
    }

    /**
     * {@inheritDoc}.
     */
    public void addDeathObserver(Observer<Enemy> o) {
        enemyDeathObservers.add(o);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<RichEnemy> getEnemies() {
        return Set.copyOf(enemies);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void add(final RichEnemy e) {
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
