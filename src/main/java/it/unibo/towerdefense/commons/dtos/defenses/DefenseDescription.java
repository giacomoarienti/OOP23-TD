package it.unibo.towerdefense.commons.dtos.defenses;

import it.unibo.towerdefense.commons.engine.LogicalPosition;
import java.util.List;
import java.util.Map;

/**Contains data that can be visualized in the build menu about defenses.*/
public class DefenseDescription {
    /**Attack.*/
    private int damage;
    /**Speed.*/
    private int speed;
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
    /**check if the defense has been selected.*/
    private boolean isFocused;

    /**simple constructor with all fields.
     * @param description
     * @param name
     * @param cost
     * @param level
    */
    public DefenseDescription(final int damage, final int speed, final int cost,
    final int sell,final int level, int range, boolean focused,
     final DefenseType type, final LogicalPosition pos, final List<LogicalPosition> targets) {
        this.damage = damage;
        this.speed = speed;
        this.level = level;
        this.cost = cost;
        this.type = type;
        this.sellValue = sell;
        this.position = pos;
        this.targets = targets;
        this.range = range;
        this.isFocused = focused;
    }

    /**getter for description.
     * @return the description.
    */
    public String getDescription() {
        final String speed_prefix = "speed -> ";
        final String range_prefix = "range -> ";
        final String damage_prefix = "attack -> ";
        final Map<DefenseType,String> mappedDescriptions = Map.of(
            DefenseType.ARCHERTOWER,"This defense attacks the closest enemy in its range.",
            DefenseType.BOMBTOWER,"Attacks an enemy in its range and deals area damage around it.",
            DefenseType.WIZARDTOWER,"Attacks  multiple targets within its range.",
            DefenseType.THUNDERINVOKER,"This building will shoot at the furthest enemy in the map,if out of his range"
        );
        String result ="";
        result+=(speed_prefix+this.speed+"\n");
        result+=(range_prefix+this.getRange()+"\n");
        result+=(damage_prefix+this.damage+"\n");
        result+=mappedDescriptions.get(this.type);
        return result;
    }

    /**getter for name.
     * @return the name.
    */
    public String getName() {
        final Map<DefenseType,String> mappedNames = Map.of(
            DefenseType.ARCHERTOWER, "Archer tower",
            DefenseType.BOMBTOWER, "Bomb tower",
            DefenseType.WIZARDTOWER, "Wizard tower",
            DefenseType.THUNDERINVOKER, "Thunder Invoker"
        );
        return mappedNames.get(this.type)+"(lv. "+this.level+")";
    }

    /**getter for cost.
     * @return the cost.
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

    /**getter for range.
     * @return the range.
     */
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

    /**Reuturns boolean that  */
    public boolean getIsFocused() {
        return this.isFocused;
    }
}
