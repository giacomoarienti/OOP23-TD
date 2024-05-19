package it.unibo.towerdefense.commons.dtos.defenses;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.defenses.DefenseType;

/**Contains data that can be visualized in the build menu about defenses.*/
public class DefenseDescription {
    /**Description of defense.*/
    private String description;
    /**name.*/
    private String name;
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

    /**simple constructor with all fields.
     * @param description
     * @param name
     * @param cost
     * @param level
    */
    public DefenseDescription(final String description, final String name, final int cost,
    final int sell,final int level, final DefenseType type, final LogicalPosition pos) {
        this.description = description;
        this.level = level;
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.sellValue = sell;
        this.position = pos;
    }

    /**getter for description.
     * @return the description.
    */
    public String getDescription() {
        return this.description;
    }

    /**getter for name.
     * @return the name.
    */
    public String getName() {
        return this.name;
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
}
