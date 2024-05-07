package it.unibo.towerdefense.models.enemies;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.utils.patterns.Observer;

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
     * @param map the MapController, for retrieval of spawn and advancement
     *            positions
     * @param gc  the GameController, for communicating the end of a wave, and the
     *            gaining of money following an enemy's death
     */
    public EnemiesImpl(final BiFunction<LogicalPosition,Integer,Optional<LogicalPosition>> posFunction, final LogicalPosition startingPos) {
        this.enemies = new EnemyCollectionImpl(posFunction);
        this.factory = new SimpleEnemyFactory(startingPos);
        this.waveSupplier = new PredicateBasedRandomWaveGenerator(
                new WavePolicySupplierImpl(Filenames.ROOT + Filenames.WAVECONF),
                new ConfigurableEnemyCatalogue(Filenames.ROOT + Filenames.TYPESCONF));
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
            current.get().next().ifPresent(et -> this.spawn(et));
            if (!current.get().hasNext()) {
                current = Optional.empty();
            }
        }
    }

    /**
     * Spawns an enemy of enemy type et.
     *
     * @param et the type of the enemy to spawn.
     */
    private void spawn(RichEnemyType et) {
        Enemy spawned = factory.spawn(et);
        enemies.add(spawned);
        enemyDeathObservers.forEach(o -> spawned.addDeathObserver(o));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<Enemy> getEnemies() {
        return enemies.getEnemies();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<EnemyInfo> getEnemiesInfo() {
        return enemies.getEnemiesInfo();
    }

    /**
     * {@inheritDoc}.
     */
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
}
