package it.unibo.towerdefense.model.enemies;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.exceptions.ConfigurationLoadingException;
import it.unibo.towerdefense.commons.patterns.Observer;
import it.unibo.towerdefense.commons.utils.file.FileUtils;

/**
 * The main class for the model of enemies.
 *
 * @see Enemies
 *      Responsible of instantiating every other model class.
 */
class EnemiesImpl implements Enemies {

    private final EnemyCollection enemies;
    private final EnemyFactory factory;
    private final Function<Integer, Wave> waveSupplier;
    private final Supplier<EnemyPosition> startingPosSupplier;
    private Optional<Wave> current = Optional.empty();

    /**
     * Contstructor for the class.
     *
     * @param posFunction         a function that takes as argument the current
     *                            position of
     *                            an enemy and how much it should adance and gives
     *                            back an
     *                            optional containing the new position or an empty
     *                            optional
     *                            if the enemy has reached the end of the map
     * @param startingPosSupplier the supplier for the starting position of enemies
     */
    EnemiesImpl(final BiFunction<? super EnemyPosition, Integer, Optional<EnemyPosition>> posFunction,
            final Supplier<EnemyPosition> startingPosSupplier) {
        this.startingPosSupplier = startingPosSupplier;
        this.enemies = new EnemyCollectionImpl(posFunction);
        this.factory = new SimpleEnemyFactory();
        try {
            final WavePolicySupplier wp = new WavePolicySupplierImpl(
                FileUtils.readResource(Filenames.wavesConfig()));
            final EnemyCatalogue ec = new EnemyCatalogueFactory(
                FileUtils.readResource(Filenames.typesConfig())).compile();
            this.waveSupplier = new PredicateBasedRandomWaveGenerator(wp, ec);
        } catch (IOException e) {
            throw new ConfigurationLoadingException("Failed to load enemy-related configuration file.", e);
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void addDeathObserver(final Observer<Enemy> o) {
        enemies.addDeathObserver(o);
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
                throw new IllegalStateException("A new wave cannot be empty.");
            }
        }
    }

    /**
     * Spawns an enemy of enemy type et.
     *
     * @param et the type of the enemy to spawn.
     */
    private void spawnEnemy(final RichEnemyType et) {
        final RichEnemy spawned = factory.spawn(et, startingPosSupplier.get());
        enemies.add(spawned);
    }
}
