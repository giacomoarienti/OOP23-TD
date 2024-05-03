package it.unibo.towerdefense.controllers.defenses;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.models.defenses.Defense;
import it.unibo.towerdefense.models.defenses.DefenseFactory;
import it.unibo.towerdefense.models.defenses.DefenseFactoryImpl;
import it.unibo.towerdefense.views.graphics.GameRenderer;

public class DefensesControllerImpl implements DefensesController {

    /**Defense builder*/
    DefenseFactory factory = new DefenseFactoryImpl();
    /**All current existing defenses with their respective cooldown.*/
    private List<Pair<Defense,Integer>> defenses = new LinkedList<>();

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
    public void buildDefense(DefenseType type, LogicalPosition position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildDefense'");
    }

    @Override
    public int disassembleDefense(LogicalPosition position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disassembleDefense'");
    }

    @Override
    public Map<DefenseType, Integer> getBuildables(LogicalPosition position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBuildables'");
    }

    @Override
    public Map<Integer, Integer> attackEnemies(List<Pair<LogicalPosition, Integer>> availableTargets) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attackEnemies'");
    }
    
}
