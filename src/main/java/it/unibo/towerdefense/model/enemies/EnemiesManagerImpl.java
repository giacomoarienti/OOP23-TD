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
 * Class responsible for managing the interactions of the enemies model with other parts of the model.
 */
public class EnemiesManagerImpl implements EnemiesManager {

    private final Enemies enemies;
    private final BindableBiFunction<EnemyPosition, Integer, Optional<EnemyPosition>> posFunction;
    private final BindableSupplier<EnemyPosition> startingPosSupplier;
    private boolean bound;

    /**
     * Constructor for the class.
     * Initializes Enemies in a non-binded state.
     */
    public EnemiesManagerImpl(){
        posFunction = new BindableBiFunction<>();
        startingPosSupplier = new BindableSupplier<>();
        enemies = new EnemiesImpl(posFunction, startingPosSupplier);
        bound = false;
    }

    @Override
    public void bind(ModelManager mm) {
        if(!bound) {
            final MapManager map = mm.getMap();
            final GameManager game = mm.getGame();

            posFunction.bind((pos, speed) -> convert(map.getNextPosition(LogicalPosition.copyOf(pos), speed)));
            startingPosSupplier.bind(() -> convert(map.getSpawnPosition()).get());

            enemies.addDeathObserver(e -> {
                game.addMoney(e.getValue());
                if (!enemies.isWaveActive()) {
                    game.advanceWave();
                }
            });

            bound = true;
        }else{
            throw new IllegalArgumentException("EnemiesManagerImpl has already been bound.");
        }
    }

    /**
     * Converts the PathVector given as input to an Optional of an enemyposition
     * which will be empty if pv's distance to end is 0, meaning the enemy has
     * reached the end.
     *
     * @param pv the pathvector to convert
     * @return the corresponding Optional EnemyPosition
     */
    private Optional<EnemyPosition> convert(PathVector pv) {
        return pv.distanceToEnd() > 0
                ? Optional.of(new EnemyPosition(
                        pv.position().getX(),
                        pv.position().getY(),
                        pv.direction(),
                        pv.distanceToEnd()))
                : Optional.empty();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void update(){
        if(!bound){
            throw new IllegalStateException("bind() has not been called yet on EnemiesManager");
        }
        enemies.update();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void spawn(int wave){
        if(!bound){
            throw new IllegalStateException("bind() has not been called yet on EnemiesManager");
        }
        enemies.spawn(wave);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<? extends Enemy> getEnemies(){
        if(!bound){
            throw new IllegalStateException("bind() has not been called yet on EnemiesManager");
        }
        return enemies.getEnemies();
    }

    private class BindableBiFunction<A, B, O> implements BiFunction<A, B, O>{
        private Optional<BiFunction<A, B, O>> f;

        private BindableBiFunction(){
            f = Optional.empty();
        }

        @Override
        public O apply(A a, B b) {
            if(f.isPresent()){
                return f.get().apply(a, b);
            }else{
                throw new IllegalStateException("BiFunction has not been binded yet.");
            }
        }

        private void bind(BiFunction<A, B, O> f){
            if(this.f.isEmpty()){
                this.f = Optional.of(f);
            }else{
                throw new IllegalStateException("BiFunction has already been binded.");
            }
        }
    }

    private class BindableSupplier<O> implements Supplier<O> {
        private Optional<Supplier<O>> s;

        private BindableSupplier(){
            s = Optional.empty();
        }

        @Override
        public O get() {
            if(s.isPresent()){
                return s.get().get();
            }else{
                throw new IllegalStateException("Supplier has not been binded yet.");
            }
        }

        private void bind(Supplier<O> s){
            if(this.s.isEmpty()){
                this.s = Optional.of(s);
            }else{
                throw new IllegalStateException("Supplier has already been binded.");
            }
        }
    }
}
