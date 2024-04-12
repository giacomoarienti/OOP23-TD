package it.unibo.towerdefense.controllers.defenses;

import it.unibo.towerdefense.controllers.Controller;
import it.unibo.towerdefense.models.defenses.Defense;
import it.unibo.towerdefense.models.engine.Position;

import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Interface for the controller of the game defenses.
 */
public interface DefensesController extends Controller {
    /**
    * @return all the defenses currently built and their positions.
    */
    Set<Pair<Position, Defense>> getDefenses();

    /**
     * it reduces the cooldown of all defenses,the cooldown is the mechanic wich checks if a defense is ready to hurt an enemy.
     */
    void updateCooldowns();

    /**
     * executes the EnemyChoiceStrategy of all the defenses that are eligible for being attacked.
     * @TODO define parameters
     */
    void executeStrategies();
} 
