package it.unibo.towerdefense.models.enemies;

import com.google.common.math.IntMath;

import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;

public class ConfigurableEnemyCatalogue implements EnemyCatalogue{

    private final Map<EnemyArchetype, Integer> rateos;
    private final Map<EnemyLevel, Integer> powerlevels;
    private final List<RichEnemyType> availableTypes;

    /**
     * Constructor for the class.
     */
    ConfigurableEnemyCatalogue(final String configFile){
        Pair<Map<EnemyArchetype, Integer>, Map<EnemyLevel, Integer>> configValues = loadConfig(configFile);
        this.rateos = configValues.getLeft();
        this.powerlevels = configValues.getRight();
        availableTypes = Arrays.stream(EnemyLevel.values())
                                .flatMap( l -> Arrays.stream(EnemyArchetype.values()).map( t -> build(l, t)) )
                                .toList();
    }

    private Pair<Map<EnemyArchetype, Integer>, Map<EnemyLevel, Integer>> loadConfig(final String configFile) {
        Map<EnemyArchetype, Integer> r = new HashMap<>();
        Map<EnemyLevel, Integer> pl = new HashMap<>();
        try(InputStream configStream = ClassLoader.getSystemResourceAsStream(configFile)){
            JSONObject config = new JSONObject(new String(configStream.readAllBytes()));
            config.getJSONArray("levels").forEach(
                (Object o) -> {
                    assert o instanceof JSONObject;
                    JSONObject level = (JSONObject)o;
                    pl.put(EnemyLevel.valueOf(level.getString("level")), level.getInt("powerlevel"));
                }
            );
            config.getJSONArray("archetypes").forEach(
                (Object o) -> {
                    assert o instanceof JSONObject;
                    JSONObject level = (JSONObject)o;
                    r.put(EnemyArchetype.valueOf(level.getString("archetype")), level.getInt("rateo"));
                }
            );
        }catch(Exception e){
            throw new RuntimeException("Failed to load enemy types configuration from " + configFile, e);
        }
        return Pair.of(r,pl);
    }

    public List<RichEnemyType> getEnemyTypes(){
        return getEnemyTypes(et -> true);
    }

    public List<RichEnemyType> getEnemyTypes(Predicate<? super RichEnemyType> test){
        return availableTypes.stream().filter(test).toList();
    }

    private record BasicEnemyType(EnemyLevel level, EnemyArchetype type, int getMaxHP, int getSpeed) implements RichEnemyType{};

    /**
     * Builds an EnemyType of given Level and Archetype
     * @param l the Level of the enemy, determines total stats
     * @param t the Archetype of the enemy, determines stats rateo
     * @return the built EnemyType
     */
    private RichEnemyType build(final EnemyLevel l, final EnemyArchetype t){
        final int speed = IntMath.sqrt((powerlevels.get(l) * rateos.get(t)) / 100, RoundingMode.DOWN);
        final int hp = (speed * 100) / rateos.get(t);
        return new BasicEnemyType(l, t, hp, speed);
    }
}
