package it.unibo.towerdefense.model.saving;

/**
 * Enum that contains the fields of a saving.
 */
public enum SavingFieldsEnum {
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
    SavingFieldsEnum(final String field) {
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
