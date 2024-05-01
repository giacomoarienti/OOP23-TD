package it.unibo.towerdefense.models.defenses;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.defenses.DefenseType;
import it.unibo.towerdefense.models.defenses.costants.DefenseMapKeys;
import it.unibo.towerdefense.utils.file.FileUtils;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Implementation of the defenseFactory interface.
 */
public class DefenseFactoryImpl implements DefenseFactory {

    /**
     * an internal factory for the strategies.
     */
    private EnemyChoiceStrategyFactory strategyFactory = new EnemyChoiceStrategyFactoryImpl();

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
        for(int i = 0;i <defenses.length(); i++) {
            JSONObject conv = defenses.getJSONObject(i);
            if(conv.getInt(DefenseMapKeys.LEVEL) == level + 1 &&
            DefenseType.valueOf(conv.getString(DefenseMapKeys.TYPE)) == type) {
                result.add(DefenseImpl.fromJson(conv.toString()));
            }
        }
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
    public Defense newArcherTowerFrom(final String fileName, final Optional<String> upgradesFileName,
    final LogicalPosition defensePosition) throws IOException {
        Defense result = new DefenseImpl(fileName);
        result.setPosition(defensePosition);
        result.setStrategy(strategyFactory.closestTargets(1, result.getRange(), result.getPosition()));
        if (upgradesFileName.isPresent()) {
            result.addUpgrades(getDefensesOfLevel(upgradesFileName.get(), DefenseType.ARCHERTOWER, result.getLevel()));
        }
        return result;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Defense bomberTowerFromSaveFile(String fileName) throws IOException {
        Defense result = new DefenseImpl(fileName);
        result.setStrategy(strategyFactory.closestTargetWithAreaDamage(0, result.getRange(), result.getPosition()));
        return result;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Defense newBomberTower(String fileName, Optional<String> upgradesFileName,
    LogicalPosition buildPosition) throws IOException {
        Defense result = new DefenseImpl(fileName);
        result.setStrategy(strategyFactory.closestTargetWithAreaDamage(0, result.getRange(), result.getPosition()));
        if (upgradesFileName.isPresent()) {
            result.addUpgrades(getDefensesOfLevel(upgradesFileName.get(), DefenseType.BOMBTOWER, result.getLevel()));
        }
        return result;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Defense wizardTowerToSaveFile(String fileName) throws IOException {
        Defense result = new DefenseImpl(fileName);
        result.setStrategy(strategyFactory.closestTargets(5, result.getRange(), result.getPosition()));
        return result;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Defense newWizardTower(String fileName, Optional<String> upgradesFileName,
    LogicalPosition buildPosition) throws IOException {
        Defense result = new DefenseImpl(fileName);
        result.setStrategy(strategyFactory.closestTargets(5, result.getRange(), result.getPosition()));
        if (upgradesFileName.isPresent()) {
            result.addUpgrades(getDefensesOfLevel(upgradesFileName.get(), DefenseType.WIZARDTOWER, result.getLevel()));
        }
        return result;
    }
}
