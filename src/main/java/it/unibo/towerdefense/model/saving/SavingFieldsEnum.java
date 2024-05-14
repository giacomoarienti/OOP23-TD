package it.unibo.towerdefense.model.saving;

/**
 * Enum that contains the fields of a saving.
 */
public enum SavingFieldsEnum {
    GAME("game"),
    MAP("map"),
    DEFENSES("defenses");

    private final String field;

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
