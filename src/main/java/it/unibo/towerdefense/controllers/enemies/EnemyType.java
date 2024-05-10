package it.unibo.towerdefense.controllers.enemies;

import java.util.Objects;

/**
 * At the highest level of abstraction an enemy type is defined by its level and
 * archetype.
 *
 * @see EnemyLevel
 * @see EnemyArchetype
 */
public abstract class EnemyType {
    /**
     * Getter for the level that characterizes this EnemyType.
     *
     * @return the corresponding EnemyLevel
     */
    abstract public EnemyLevel level();

    /**
     * Getter for the archetype that characterizes this EnemyType.
     *
     * @return the corresponding EnemyArchetype
     */
    abstract public EnemyArchetype type();

    /**
     * Two enemy types are the same if they have same level and type.
     */
    @Override
    public final boolean equals(final Object o) {
        return o instanceof EnemyType
                && ((EnemyType) o).level() == this.level()
                && ((EnemyType) o).type() == this.type();
    }

    /**
     * Overridden to support the new equals definition.
     */
    @Override
    public final int hashCode() {
        return Objects.hash(this.level(), this.type());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public final String toString(){
        return this.level().toString() + this.level().toString();
    }
}
