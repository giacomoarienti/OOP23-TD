package it.unibo.towerdefense.models.enemies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import it.unibo.towerdefense.controllers.enemies.EnemyInfo;
import it.unibo.towerdefense.controllers.gameMap.MapController;

/**
 * @inheritDoc .
 */
public class EnemiesImpl implements Enemies {

    private Set<Enemy> enemies;
    private final int width;
    private final int height;
    private final MapController map;

    /**
     * Constructor for the class.
     *
     * @param width  of the game area in cells
     * @param height of the game area in cells
     * @param map    handle to get next positions
     */
    public EnemiesImpl(final int width, final int height, final MapController map) {
        this.width = width;
        this.height = height;
        this.enemies = new HashSet<>();
        this.map = map;
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void move() {
        /*
         * for(Enemy e : enemies){
         * int budget = e.getSpeed();
         * while(budget > 0){
         * Optional<PathCell> next = map.getNext(e.getPosition().toPathCell());
         * if(next.isEmpty()){
         * signalDeath(e);
         * }else{
         * EnemyPosition nextPos = EnemyPosition.fromPathCell(next.get());
         * int distance = e.getPosition().distanceFromAdjacent(nextPos);
         * budget-=distance;
         * }
         * }
         * }unfinished
         */
    }

    /**
     * @inheritDoc .
     */
    @Override
    public void signalDeath(final Enemy which) {
        enemies.remove(which);
    }

    /**
     * @inheritDoc .
     */
    @Override
    public Set<Enemy> getEnemies() {
        return Set.copyOf(enemies);
    }

    /**
     * @inheritDoc .
     */
    @Override
    public Collection<EnemyInfo> getEnemiesInfo() {
        return enemies.stream().map(e -> e.info()).toList();
    }

}
