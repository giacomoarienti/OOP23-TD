package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.controllers.enemies.EnemyLevel;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;


/**
 * Class responsible of initializing and maintaining a list of all possible enemies.
 */
class EnemyCatalogue {
    /**
     * Level of the enemy, determines total stats.
     */
    private enum Level {
        I(EnemyLevel.I),
        II(EnemyLevel.II),
        III(EnemyLevel.III),
        IV(EnemyLevel.IV),
        V(EnemyLevel.V);
        private final int STARTING_POWER_LEVEL = 1000; // HP*SPEED
        private final EnemyLevel l;
        private Level(final EnemyLevel l){
            this.l = l;
        }
        EnemyLevel getEnemyLevel(){
            return l;
        }
        int getPowerLevel(){
            return STARTING_POWER_LEVEL * (2<<this.ordinal());
        }
    };

    /**
     * Archetype of the enemy, determines rateo speed/hp
     */
    private enum Archetype {
        A(EnemyArchetype.A, 150), //agile
        B(EnemyArchetype.B, 100), //balanced
        C(EnemyArchetype.C, 50); //chunky
        private final EnemyArchetype a;
        private final int rateo; // speed/hp = rateo/100
        private Archetype(final EnemyArchetype a, final int rateo){
            this.a = a;
            this.rateo = rateo;
        }
        EnemyArchetype getEnemyArchetype(){
            return a;
        }
        int getRateo(){
            return this.rateo;
        }
    };

    /**
     * EnemyType implementation using records.
     */
    private record BasicEnemyType(int getMaxHP, int getSpeed, EnemyLevel getEnemyLevel, EnemyArchetype getEnemyArchetype) implements EnemyType{};

    private static EnemyCatalogue singleton = null;
    private final Collection<EnemyType> availableTypes;

    /**
     * Constructor for the class.
     * Used singleton pattern.
     */
    private EnemyCatalogue(){
        availableTypes = Arrays.stream(Level.values())
                                .flatMap( l -> Arrays.stream(Archetype.values()).map( t -> build(l, t)) )
                                .toList();
    }

    /**
     * Method to get the singleton.
     * @return the singleton
     */
    public static EnemyCatalogue getCatalogue(){
        if(singleton == null){
            singleton = new EnemyCatalogue();
        }
        return singleton;
    }

    /**
     * Returns a list of all EnemyTypes in the game.
     * @return a list of all EnemyTypes in the game
     */
    public List<EnemyType> getEnemyTypes(){
        return getEnemyTypes(et -> true);
    }

    /**
     * Returns a list of all EnemyTypes in the game that match a given predicate.
     * @return a list of all EnemyTypes in the game that match a given predicate
     */
    public List<EnemyType> getEnemyTypes(final Predicate<EnemyType> test){
        return availableTypes.stream().filter(test).toList();
    }

    /**
     * Builds an EnemyType of given Level and Archetype
     * @param l the Level of the enemy, determines total stats
     * @param t the Archetype of the enemy, determines stats rateo
     * @return the built EnemyType
     */
    private EnemyType build(final Level l, final Archetype t){
        final int hp = (l.getPowerLevel() * 100) / (100 + t.getRateo());
        return new BasicEnemyType(hp,
                                (hp / 100) * t.getRateo(),
                                l.getEnemyLevel(),
                                t.getEnemyArchetype());
    }
}
