package it.unibo.towerdefense.models.defenses;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.defenses.DefenseType;
import it.unibo.towerdefense.models.defenses.costants.DefenseMapKeys;
import it.unibo.towerdefense.utils.file.FileUtils;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Implementation of the defenseFactory interface.
 */
public class DefenseFactoryImpl implements DefenseFactory {

    /**
     * an internal factory for the strategies.
     */
    private EnemyChoiceStrategyFactory strategyFactory;

    /**
     * Private method for retrieving the updates from a file.
     * @param filePath the file with the defenses.
     * @param level the level of the defense.
     * @param type the type of the defense.
     * @throws IOException in case of missing file.
     * @return all the defenses of type x at level y from a json file.
     */
    private Set<Defense> getDefensesOfLevel(String filePath, DefenseType type, int level) throws IOException {
        /**Read file.*/
        String fileContent = FileUtils.readFile(filePath);
        JSONArray defenses = new JSONArray(fileContent);
        Set<Defense> result = new HashSet<>();
        /**check for valid updates.*/
        defenses.forEach(x -> {
            JSONObject conv = new JSONObject(x.toString());
            if(conv.getInt(DefenseMapKeys.LEVEL) == level &&
            DefenseType.valueOf(conv.getString(DefenseMapKeys.TYPE)) == type) {
                result.add(DefenseImpl.fromJson(conv.toString()));
            }
        });
        return result;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Defense archerTowerFromSaveFile(final String fileName) throws IOException {
        Defense result = new DefenseImpl(fileName);
        result.setStrategy(strategyFactory.closestTargets(1, result.getRange(), result.getPosition()));
        return result;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Defense newArcherTowerFrom(final String fileName, final LogicalPosition defensePosition) throws IOException {
        Defense result = new DefenseImpl(fileName);
        result.setPosition(defensePosition);
        result.setStrategy(strategyFactory.closestTargets(1, result.getRange(), result.getPosition()));
        result.addUpgrades(getDefensesOfLevel(fileName, DefenseType.ARCHERTOWER, result.getLevel()));
        return result;
    }
}
