package it.unibo.towerdefense.controllers.defenses;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.defenses.Defense;
import it.unibo.towerdefense.models.defenses.DefenseFactory;
import it.unibo.towerdefense.models.defenses.DefenseFactoryImpl;
import it.unibo.towerdefense.models.defenses.costants.DefenseMapFilePaths;
import it.unibo.towerdefense.views.defenses.defenseDescription;
import it.unibo.towerdefense.views.graphics.GameRenderer;

public class DefensesControllerImpl implements DefensesController {

    /**Defense builder*/
    DefenseFactory factory = new DefenseFactoryImpl();
    /**All current existing defenses with their respective cooldown.*/
    private List<Pair<Defense,Integer>> defenses = new LinkedList<>();
    /**The current custom position to be used by defenses.*/
    LogicalPosition endOfMap;

    /**finds a defense based on its position.
     * @return a defense id there is something on given position,a empty Optional otherwise.
    */
    private Optional<Defense> find(LogicalPosition pos) {
        for (Pair<Defense,Integer> pair : defenses) {
            if (pair.getKey().getPosition() == pos) {
                return Optional.of(pair.getKey());
            }
        }
        return Optional.empty();
    }

    /**
     * gives a defenseDescription for a given defense.
     * @return the defenseDescription of
     * @param def
     */
    private defenseDescription getDescriptionFrom(Defense def) {
        return new defenseDescription(def.getType().toString() + " lv. "+def.getLevel(),
         def.getType().toString(),
         def.getBuildingCost());
    }

    /**gets the models of buildable defenses for given defense
     * THIS ARE NOT TO BE PASSED TO THE VIEW.
     * @return a list of defenses.
     * @param buildPosition the current model to check.
    */
    private List<Defense> getModelsOfBuildables(LogicalPosition buildPosition) throws IOException {
        Optional<Defense> currentDef = find(buildPosition);
        if ( currentDef.isEmpty()) {
            return List.of(
                factory.levelOneDefense(DefenseMapFilePaths.ARCHER_TOWER_LV1, buildPosition, Optional.empty()),
                factory.levelOneDefense(DefenseMapFilePaths.BOMB_TOWER_LV1, buildPosition, Optional.empty()),
                factory.levelOneDefense(DefenseMapFilePaths.WIZARD_TOWER_LV1, buildPosition, Optional.empty()),
                factory.levelOneDefenseWithCustomPosition(DefenseMapFilePaths.WIZARD_TOWER_LV1,
                buildPosition, endOfMap, Optional.empty())
            );
        }
        return currentDef.get().getPossibleUpgrades().stream().toList();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void render(GameRenderer renderer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    @Override
    public void buildDefense(int choice, LogicalPosition position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildDefense'");
    }

    @Override
    public int disassembleDefense(LogicalPosition position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disassembleDefense'");
    }

    @Override
    public Map<Integer, defenseDescription> getBuildables(LogicalPosition position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disassembleDefense'");
    }

    @Override
    public Map<Integer, Integer> attackEnemies(List<Pair<LogicalPosition, Integer>> availableTargets) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attackEnemies'");
    }
    
}
