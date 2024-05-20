package it.unibo.towerdefense.model.enemies;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyPosition;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.ModelManager;
import it.unibo.towerdefense.model.game.GameManager;
import it.unibo.towerdefense.model.map.MapManager;
import it.unibo.towerdefense.model.map.PathVector;

/**
 * Class responsible for managing the interactions of the enemies model with
 * other parts of the model.
 */
public class EnemiesManagerImpl implements EnemiesManager {

    private final Enemies enemies;
    private final BindableBiFunction<EnemyPosition, Integer, Optional<EnemyPosition>> posFunction;
    private final BindableSupplier<EnemyPosition> startingPosSupplier;
    private boolean bound;

    /**
     * Constructor for the class.
     * Initializes Enemies in a non-binded state, calls to any method which is not
     * bind in this state will result in an IllegalStateException.
     */
    public EnemiesManagerImpl() {
        posFunction = new BindableBiFunction<>();
        startingPosSupplier = new BindableSupplier<>();
        enemies = new EnemiesImpl(posFunction, startingPosSupplier);
        bound = false;
    }

    /**
     * Binds the other managers to this part of the model.
     */
    @Override
    public void bind(ModelManager mm) {
        if (!bound) {
            final MapManager map = mm.getMap();
            final GameManager game = mm.getGame();

            posFunction.bind((pos, speed) -> convert(map.getNextPosition(LogicalPosition.copyOf(pos), speed), pos.getDistanceFromStart() + speed));
            startingPosSupplier.bind(() -> convert(map.getSpawnPosition(), 0).get());

            enemies.addDeathObserver(e -> {
                System.out.println("Enemy dead!");
                if(e.isDead()){
                    System.out.println("Enemy was dead.");
                    game.addMoney(e.getValue());
                }else{
                    game.decreaseLives();
                }
                if (!enemies.isWaveActive()) {
                    game.advanceWave();
                }
            });

            bound = true;
        } else {
            throw new IllegalArgumentException("EnemiesManagerImpl has already been bound.");
        }
    }

    /**
     * Converts the PathVector given as input to an Optional of an enemyposition
     * which will be empty if pv's distance to end is 0, meaning the enemy has
     * reached the end.
     *
     * @param pv the pathvector to convert
     * @param distance the new distance from start
     * @return the corresponding Optional EnemyPosition
     */
    private Optional<EnemyPosition> convert(PathVector pv, long distance) {
        return pv.distanceToEnd() > 0
                ? Optional.of(new EnemyPosition(
                        pv.position().getX(),
                        pv.position().getY(),
                        pv.direction(),
                        distance))
                : Optional.empty();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void update() {
        if (!bound) {
            throw new IllegalStateException("bind() has not been called yet on EnemiesManager");
        }
        enemies.update();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void spawn(int wave) {
        if (!bound) {
            throw new IllegalStateException("bind() has not been called yet on EnemiesManager");
        }
        enemies.spawn(wave);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<? extends Enemy> getEnemies() {
        if (!bound) {
            throw new IllegalStateException("bind() has not been called yet on EnemiesManager");
        }
        return enemies.getEnemies();
    }

    /**
     * Class for a BiFunction which can be defined after initialization.
     */
    private class BindableBiFunction<A, B, O> implements BiFunction<A, B, O> {
        private Optional<BiFunction<A, B, O>> f;

        /**
         * Constructs the BiFunction in a non-binded state.
         *
         * Calls to methods other than bind in this state will result in an
         * IllegalStateException.
         */
        private BindableBiFunction() {
            f = Optional.empty();
        }

        /**
         * {@inheritDoc}.
         */
        @Override
        public O apply(A a, B b) {
            if (f.isPresent()) {
                return f.get().apply(a, b);
            } else {
                throw new IllegalStateException("BiFunction has not been binded yet.");
            }
        }

        /**
         * Binds the function given as parameter as the one to apply.
         *
         * Must only be called once.
         *
         * @param f the function to bind
         */
        private void bind(BiFunction<A, B, O> f) {
            if (this.f.isEmpty()) {
                this.f = Optional.of(f);
            } else {
                throw new IllegalStateException("BiFunction has already been binded.");
            }
        }
    }

    /**
     * Class for a Supplier which can be defined after initialization.
     */
    private class BindableSupplier<O> implements Supplier<O> {
        private Optional<Supplier<O>> s;

        /**
         * C
         */
        private BindableSupplier() {
            s = Optional.empty();
        }

        /**
         * Constructs the Supplier in a non-binded state.
         *
         * Calls to methods other than bind in this state will result in an
         * IllegalStateException.
         */
        @Override
        public O get() {
            if (s.isPresent()) {
                return s.get().get();
            } else {
                throw new IllegalStateException("Supplier has not been binded yet.");
            }
        }

        /**
         * Binds the Supplier given as parameter as the one to get.
         *
         * Must only be called once.
         *
         * @param s the supplier to bind
         */
        private void bind(Supplier<O> s) {
            if (this.s.isEmpty()) {
                this.s = Optional.of(s);
            } else {
                throw new IllegalStateException("Supplier has already been binded.");
            }
        }
    }
}
