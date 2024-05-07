package it.unibo.towerdefense.views.defenses;

/**Contains data that can be visualized in the build menu about defenses.*/
public class DefenseDescription {
    /**Description of defense.*/
    private String description;
    /**name.*/
    private String name;
    /**Cost.*/
    private int cost;

    /**simple constructor with all fields.
     * @param description
     * @param name
     * @param cost
    */
    public DefenseDescription(final String description, final String name, final int cost) {
        this.description = description;
        this.name = name;
        this.cost = cost;
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
}
