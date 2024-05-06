package it.unibo.towerdefense.models.defenses;

import it.unibo.towerdefense.commons.LogicalPosition;
import it.unibo.towerdefense.controllers.defenses.DefenseType;
import it.unibo.towerdefense.models.defenses.costants.DefenseFormulas;
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
    private Set<Defense> getDefensesOfLevel(final String filePath, final DefenseType type, final int level)
    throws IOException {
        /**Read file.*/
        String fileContent = FileUtils.readFile(filePath);
        JSONArray defenses = new JSONArray(fileContent);
        Set<Defense> result = new HashSet<>();
        /**check for valid updates.*/
        for (int i = 0; i < defenses.length(); i++) {
            JSONObject conv = defenses.getJSONObject(i);
            if (conv.getInt(DefenseMapKeys.LEVEL) == level + 1
            && DefenseType.valueOf(conv.getString(DefenseMapKeys.TYPE)) == type) {
                result.add(DefenseImpl.fromJson(conv.toString()));
            }
        }
        return result;
    }

    /**sets the strategy for a defense based on its type.
     * @param def the defense to set the strategy to.
     * @param customPos used for Thunder summoner,and any other possible defense that uses a custom position.
     * @throws IllegalStateException if defense type is none
    */
    private void setStrategyFor(final Defense def, final Optional<LogicalPosition> customPos) {
        switch (def.getType()) {
            case ARCHERTOWER:
                def.setStrategy(strategyFactory.closestTargets(1, def.getRange(), def.getPosition()));
            break;
            case BOMBTOWER:
                def.setStrategy(strategyFactory.closestTargetWithAreaDamage(DefenseFormulas.bombTowerDamageAreaFormula(def),
                def.getRange(), def.getPosition()));
            break;
            case WIZARDTOWER:
                def.setStrategy(strategyFactory.closestTargets(DefenseFormulas.wizaredTowerTargetsFormula(def),
                def.getRange(), def.getPosition()));
            break;
            case THUNDERINVOKER:
                if (customPos.isEmpty()) {
                    throw new IllegalStateException();
                }
                def.setStrategy(strategyFactory.closestToCustomPointNotInRange(
                def.getRange(), customPos.get(), def.getPosition()));
            break;
            case NOTOWER:
            default:
                throw new IllegalStateException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Defense defenseFromSaveFile(final String saveFile) throws IOException {
        Defense result = new DefenseImpl(saveFile);
        setStrategyFor(result, Optional.empty());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Defense levelOneDefense(final String statFile, final LogicalPosition buildPosition,
    final Optional<String> upgradesFileName) throws IOException {
        Defense result = new DefenseImpl(statFile);
        result.setPosition(buildPosition);
        setStrategyFor(result, Optional.empty());
        if (upgradesFileName.isPresent()) {
            result.addUpgrades(getDefensesOfLevel(upgradesFileName.get(), result.getType(), result.getLevel()));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Defense defenseFromSaveFileWithCustomPoint(final String saveFile, final LogicalPosition customPosition)
    throws IOException {
        Defense result = new DefenseImpl(saveFile);
        setStrategyFor(result, Optional.of(customPosition));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Defense levelOneDefenseWithCustomPosition(final String statFile, final LogicalPosition buildPosition,
            final LogicalPosition customPosition, final Optional<String> upgradesFileName) throws IOException {
                Defense result = new DefenseImpl(statFile);
                result.setPosition(buildPosition);
                setStrategyFor(result, Optional.of(customPosition));
                if (upgradesFileName.isPresent()) {
                    result.addUpgrades(getDefensesOfLevel(statFile, result.getType(), result.getLevel()));
                }
                return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upgrade(Defense current, final int upgradeIndex, final Optional<String> upgradesFileName) throws IOException {
        Optional<LogicalPosition> optionalCustomPos = current.getStrategy().getCustomPosition();
        LogicalPosition defPosition = current.getPosition();
        current = current.getPossibleUpgrades().stream().toList().get(upgradeIndex);
        current.setPosition(defPosition);
        setStrategyFor(current, optionalCustomPos);
        if (upgradesFileName.isPresent()) {
            current.addUpgrades(getDefensesOfLevel(upgradesFileName.get(), current.getType(), current.getLevel()));
        }
    }
}
