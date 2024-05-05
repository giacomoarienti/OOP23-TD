package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.game.GameController;
import it.unibo.towerdefense.controllers.map.MapController;

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
    private final GameController gc;
    private Optional<Wave> current = Optional.empty();

    /**
     * Contstructor for the class.
     *
     * @param map the MapController, for retrieval of spawn and advancement
     *            positions
     * @param gc  the GameController, for communicating the end of a wave, and the
     *            gaining of money following an enemy's death
     */
    public EnemiesImpl(final MapController map, final GameController gc) {
        this.enemies = new EnemyCollectionImpl(gc, map);
        this.factory = new SimpleEnemyFactory(map.getSpawnPosition());
        this.waveSupplier = new PredicateBasedRandomWaveGenerator(
                new WavePolicySupplierImpl(Filenames.ROOT + Filenames.WAVECONF),
                new ConfigurableEnemyCatalogue(Filenames.ROOT + Filenames.TYPESCONF));
        this.gc = gc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        enemies.move();
        if (current.isPresent()) {
            current.get().next().ifPresent(et -> enemies.add(factory.spawn(et)));
            if (!current.get().hasNext()) {
                current = Optional.empty();
            }
        }
        /*
         * Enclosing this in an else clause would prevent the wave from
         * advancing during the first cycle after a wave !hasNext.
         */
        if (current.isEmpty() && enemies.areDead()) {
            gc.advanceWave();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Enemy> getEnemies() {
        return enemies.getEnemies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EnemyInfo> getEnemiesInfo() {
        return enemies.getEnemiesInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void spawn(int wave) {
        if (current.isPresent() || !enemies.areDead()) {
            throw new IllegalStateException("A wave is already being spawned.");
        } else {
            current = Optional.of(waveSupplier.apply(wave));
            if (!current.get().hasNext()) {
                throw new RuntimeException("A new wave cannot be empty.");
            }
        }
    }
}
