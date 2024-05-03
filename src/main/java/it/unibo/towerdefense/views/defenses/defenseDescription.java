package it.unibo.towerdefense.views.defenses;

/**Contains data that can be visualized in the build menu about defenses.*/
public class defenseDescription {
    
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
    public defenseDescription(String description, String name, int cost) {
        this.description = description;
        this.name = name;
        this.cost = cost;
    }
}
