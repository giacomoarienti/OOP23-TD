package it.unibo.towerdefense.commons.dtos.enemies;

import it.unibo.towerdefense.commons.engine.Direction;
import it.unibo.towerdefense.commons.engine.LogicalPosition;

public class EnemyPosition extends LogicalPosition{

    private Direction dir;
    private int distance;

    public EnemyPosition(int x, int y, Direction dir, int distance){
        super(x, y);
        this.dir = dir;
        this.distance = distance;
    }

    public void setTo(final EnemyPosition pos){
        this.set(pos.getX(), pos.getY());
        this.setDir(pos.getDir());
        this.setDistance(pos.getDistance());
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public EnemyPosition clone(){
        return new EnemyPosition(this.getX(), this.getY(), this.getDir(), this.getDistance());
    }
}
