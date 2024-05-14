package it.unibo.towerdefense.commons.dtos;

import it.unibo.towerdefense.models.defenses.DefenseType;

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
    /**Type.*/
    private DefenseType type;

    /**simple constructor with all fields.
     * @param description
     * @param name
     * @param cost
    */
    public DefenseDescription(final String description, final String name, final int cost,
    final int sell, final DefenseType type) {
        this.description = description;
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.sellValue = sell;
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

    /**Returns the description for empty zones.*/
    public static DefenseDescription nonBuiltDefense() {
        final String desc = "Nothing is here";
        final String name = "empty zone";
        return new DefenseDescription(desc, name, 0, 0, DefenseType.NOTOWER);
    }
}
