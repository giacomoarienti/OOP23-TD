package it.unibo.towerdefense.model.enemies;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;


import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.commons.utils.file.FileUtils;

/**
 * The main class for the model of enemies.
 *
 * @see Enemies
 *      Responsible of instantiating every other model class.
 */
public class EnemiesImpl implements Enemies {

    private final EnemyCollection enemies;
    private final EnemyFactory factory;
    private final Function<Integer, Wave> waveSupplier;
    private final Set<Observer<Enemy>> enemyDeathObservers;
    private Optional<Wave> current = Optional.empty();

    /**
     * Contstructor for the class.
     *
     * @param posFunction a function that takes as argument the current position of
     *                    an enemy and how much it should adance and gives back an
     *                    optional containing the new position or an empty optional
     *                    if the enemy has reached the end of the map
     * @param startingPos the starting position of enemies
     */
    public EnemiesImpl(final BiFunction<? super EnemyPosition, Integer, Optional<EnemyPosition>> posFunction,
            final EnemyPosition startingPos) {
        this.enemies = new EnemyCollectionImpl(posFunction);
        this.factory = new SimpleEnemyFactory(startingPos);
        WavePolicySupplier wp;
        EnemyCatalogue ec;
        try {
            wp = new WavePolicySupplierImpl(FileUtils.readFile(Filenames.wavesConfig()));
        } catch (Throwable t) {
            throw new RuntimeException("Failed to load wave policy configuration from file.", t);
        }
        try {
            ec = new EnemyCatalogueFactory(FileUtils.readFile(Filenames.typesConfig())).compile();
        } catch (Throwable t) {
            throw new RuntimeException("Failed to load enemy types configuration from file.", t);
        }
        this.waveSupplier = new PredicateBasedRandomWaveGenerator(wp, ec);
        this.enemyDeathObservers = new HashSet<>();
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
    public void update() {
        enemies.move();
        if (current.isPresent()) {
            current.get().next().ifPresent(et -> this.spawnEnemy(et));
            if (!current.get().hasNext()) {
                current = Optional.empty();
            }
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<RichEnemy> getEnemies() {
        return enemies.getEnemies();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean isWaveActive() {
        return current.isPresent() || !enemies.areDead();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void spawn(final int wave) {
        if (isWaveActive()) {
            throw new IllegalStateException("A wave is already being spawned.");
        } else {
            current = Optional.of(waveSupplier.apply(wave));
            if (!current.get().hasNext()) {
                throw new RuntimeException("A new wave cannot be empty.");
            }
        }
    }

    /**
     * Spawns an enemy of enemy type et.
     *
     * @param et the type of the enemy to spawn.
     */
    private void spawnEnemy(RichEnemyType et) {
        RichEnemy spawned = factory.spawn(et);
        enemies.add(spawned);
        enemyDeathObservers.forEach(o -> spawned.addDeathObserver(o));
    }
}
