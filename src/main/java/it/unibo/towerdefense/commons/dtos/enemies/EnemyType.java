package it.unibo.towerdefense.commons.dtos.enemies;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * At the highest level of abstraction an enemy type is defined by its level and
 * archetype.
 *
 * @see EnemyLevel
 * @see EnemyArchetype
 */
public abstract class EnemyType {

    /**
     * Enum containing the different EnemyLevels in the game.
     */
    public enum EnemyLevel {
        /**
         * Level 1.
         */
        I,

        /**
         * Level 2.
         */
        II,

        /**
         * Level 3.
         */
        III,

        /**
         * Level 4.
         */
        IV,

        /**
         * Level 5.
         */
        V;
    }

    /**
     * Enum containing the different EnemyArchetypes in the game.
     */
    public enum EnemyArchetype {
        /**
         * Agile.
         */
        A,

        /**
         * Balanced.
         */
        B,

        /**
         * Chunky.
         */
        C;
    }

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
    public final String toString() {
        return this.level().toString() + this.type().toString();
    }

    /**
     * The EnemyTypes present in the game are the cartesian product between the
     * defined levels and types.
     *
     * @return a Set containing all the EnemyTypes in the game.
     */
    public static Set<EnemyType> getEnemyTypes() {
        return Arrays.stream(EnemyLevel.values())
                .flatMap(l -> Arrays.stream(EnemyArchetype.values())
                .map(t -> new EnemyType() {
                    @Override
                    public EnemyLevel level() {
                        return l;
                    }

                    @Override
                    public EnemyArchetype type() {
                        return t;
                    }
                }))
                .collect(Collectors.toSet());
    }
}
