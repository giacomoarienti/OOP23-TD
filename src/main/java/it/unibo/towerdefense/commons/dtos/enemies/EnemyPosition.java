package it.unibo.towerdefense.commons.dtos.enemies;

import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

public class EnemyPosition extends LogicalPosition{

    private Direction dir;
    private long distance;

    public EnemyPosition(int x, int y, Direction dir, long distance){
        super(x, y);
        this.dir = dir;
        this.distance = distance;
    }

    public void setTo(final EnemyPosition pos){
        this.set(pos.getX(), pos.getY());
        this.setDir(pos.getDir());
        this.setDistance(pos.getDistanceFromStart());
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public long getDistanceFromStart() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    @Override
    public EnemyPosition clone(){
        return new EnemyPosition(this.getX(), this.getY(), this.getDir(), this.getDistanceFromStart());
    }
}
