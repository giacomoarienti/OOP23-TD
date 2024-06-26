package it.unibo.towerdefense.model.defenses;

import it.unibo.towerdefense.commons.dtos.defenses.DefenseType;
import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.commons.utils.file.FileUtils;
import it.unibo.towerdefense.model.defenses.costants.DefenseFormulas;
import it.unibo.towerdefense.model.defenses.costants.DefenseMapKeys;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *Implementation of the defenseFactory interface.
*/
public class DefenseFactoryImpl implements DefenseFactory {

    /**
     * an internal factory for the strategies.
     */
    private final EnemyChoiceStrategyFactory strategyFactory = new EnemyChoiceStrategyFactoryImpl();

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
        final String fileContent = FileUtils.readResource(filePath);
        final JSONArray defenses = new JSONArray(fileContent);
        final Set<Defense> result = new HashSet<>();
        /**check for valid updates.*/
        for (int i = 0; i < defenses.length(); i++) {
            final JSONObject conv = defenses.getJSONObject(i);
            if (conv.getInt(DefenseMapKeys.LEVEL) == level + 1
            && DefenseType.valueOf(conv.getString(DefenseMapKeys.TYPE)) == type) {
                result.add(DefenseImpl.fromJson(conv.toString()));
            }
        }
        return result;
    }

    /**sets the strategy for a defense based on its type.
     * @param def the defense to set the strategy to.
     * @throws IllegalStateException if defense type is none
    */
    private void setStrategyFor(final Defense def) {
        switch (def.getType()) {
            case ARCHERTOWER:
                def.setStrategy(strategyFactory.closestTargets(1, def.getRange(), def.getPosition().get()));
            break;
            case BOMBTOWER:
                def.setStrategy(strategyFactory.closestTargetWithAreaDamage(DefenseFormulas.bombTowerDamageAreaFormula(def),
                def.getRange(), def.getPosition().get()));
            break;
            case WIZARDTOWER:
                def.setStrategy(strategyFactory.closestTargets(DefenseFormulas.wizaredTowerTargetsFormula(def),
                def.getRange(), def.getPosition().get()));
            break;
            case THUNDERINVOKER:
                def.setStrategy(strategyFactory.closestToEndMap(
                def.getRange(), def.getPosition().get()));
            break;
            case NOTOWER:
            default:
                throw new IllegalStateException();
        }
    }

    /**
     * Some data is not serializable in the json file,this
     * method clones such data.
     * @param defense the defense to pass data to.
     */
    private void cloneNonSerializableDataInUpdates(final Defense defense) {
        for (final Defense def : defense.getPossibleUpgrades()) {
            def.setPosition(defense.getPosition().get());
            def.setStrategy(defense.getStrategy());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Defense defenseFromJsonSave(final String jsonData) {
        final Defense result = Defense.fromJson(jsonData);
        setStrategyFor(result);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Defense levelOneDefense(final String statFile, final LogicalPosition buildPosition,
    final Optional<String> upgradesFileName) throws IOException {
        final Defense result = new DefenseImpl(statFile);
        result.setPosition(buildPosition);
        setStrategyFor(result);
        if (upgradesFileName.isPresent()) {
            result.addUpgrades(getDefensesOfLevel(upgradesFileName.get(), result.getType(), result.getLevel()));
        }
        cloneNonSerializableDataInUpdates(result);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Defense upgrade(final Defense current, final int upgradeIndex, final Optional<String> upgradesFileName)
    throws IOException {
        final LogicalPosition defPosition = current.getPosition().get();
        final Defense upgradedVersion = current.getPossibleUpgrades().stream().toList().get(upgradeIndex);
        upgradedVersion.setPosition(defPosition);
        setStrategyFor(upgradedVersion);
        if (upgradesFileName.isPresent()) {
            upgradedVersion.addUpgrades(getDefensesOfLevel(
            upgradesFileName.get(),
            upgradedVersion.getType(),
            upgradedVersion.getLevel()));
        }
        return upgradedVersion;
    }
}
