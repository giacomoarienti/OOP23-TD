package it.unibo.towerdefense.commons.dtos.defenses;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.defenses.DefenseType;

import java.util.List;

/**Contains data that can be visualized in the build menu about defenses.*/
public class DefenseDescription {
    /**Cost.*/
    private int cost;
    /**Sell value.*/
    private int sellValue;
    /**Level.*/
    private int level;
    /**Type.*/
    private DefenseType type;
    /**Position.*/
    private LogicalPosition position;
    /**List of attacking targets */
    private List<LogicalPosition> targets;
    /**range.*/
    private int range;

    /**simple constructor with all fields.
     * @param description
     * @param name
     * @param cost
     * @param level
    */
    public DefenseDescription(final int cost,final int sell,final int level, int range,
     final DefenseType type, final LogicalPosition pos, final List<LogicalPosition> targets) {
        this.level = level;
        this.cost = cost;
        this.type = type;
        this.sellValue = sell;
        this.position = pos;
        this.targets = targets;
        this.range = range;
    }

    /**getter for description.
     * @return the description.
    */
    public String getDescription() {
        return "A level " +  this.level + " defense";
    }

    /**getter for name.
     * @return the name.
    */
    public String getName() {
        return "lv " + this.level + " " + this.getType();
    }

    /**getter for description.
     * @return the description.
    */
    public int getCost() {
        return this.cost;
    }

    /**getter for sellvalue.
     * @return the sell value.
    */
    public int getSellValue() {
        return this.sellValue;
    }

    /**getter for type.
     * @return the type.
    */
    public DefenseType getType() {
        return this.type;
    }

    /**getter for level.
     * @return the level.
    */
    public int getLevel() {
        return this.level;
    }

    /**getter for position.
     * @return the position.
    */
    public LogicalPosition getPosition() {
        return this.position;
    }

    public int getRange() {
        return this.range;
    }

    /**
     *
     * @return a List of positions being attacked
     */
    public List<LogicalPosition> getTargets() {
        return this.targets;
    }
}
