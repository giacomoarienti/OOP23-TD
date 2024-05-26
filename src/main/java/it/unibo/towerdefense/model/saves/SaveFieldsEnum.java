package it.unibo.towerdefense.model.saves;

/**
 * Enum that contains the fields of a save.
 */
public enum SaveFieldsEnum {
    /**
     * The game field.
     */
    GAME("game"),
    /**
     * The map field.
     */
    MAP("map"),
    /**
     * The defenses field.
     */
    DEFENSES("defenses");

    private final String field;

    /**
     * Default constructor.
     * @param field the field
     */
    SaveFieldsEnum(final String field) {
        this.field = field;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.field;
    }
}
