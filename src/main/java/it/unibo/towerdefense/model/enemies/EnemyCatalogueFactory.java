package it.unibo.towerdefense.model.enemies;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType.EnemyLevel;
import it.unibo.towerdefense.commons.exceptions.ConfigurationLoadingException;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType;

/**
 * {@inheritDoc}.
 */
class EnemyCatalogueFactory {

    private final Double valueFactor; // scaling factor to adjust value from powerlevel
    private final Map<EnemyArchetype, Double> rateos; // speed = hp * rateo
    private final Map<EnemyLevel, Integer> powerlevels; // powerlevel = speed * hp

    /**
     * Constructor for the class.
     *
     * @param configFile name of the file from which to load configurations.
     */
    EnemyCatalogueFactory(final String configFile) {
        final Triple<Double, Map<EnemyArchetype, Double>, Map<EnemyLevel, Integer>> configValues = loadConfig(
                configFile);

        checkConstraints(configValues);

        this.valueFactor = configValues.getLeft();
        this.rateos = configValues.getMiddle();
        this.powerlevels = configValues.getRight();
    }

    /**
     * Compiles a Catalogue with the information and returns it.
     *
     * @return the created EnemyCatalogue.
     */
    EnemyCatalogue compile() {
        return new EnemyCatalogue() {
            private final Set<RichEnemyType> types = EnemyType.getEnemyTypes().stream().map(et -> build(et))
                    .collect(Collectors.toUnmodifiableSet());

            /**
             * {@inheritDoc}
             */
            @Override
            public Set<RichEnemyType> getEnemyTypes() {
                return getEnemyTypes(et -> true);
            }

            /**
             * {@inheritDoc}.
             */
            @Override
            public Set<RichEnemyType> getEnemyTypes(final Predicate<? super RichEnemyType> test) {
                return types.stream().filter(test).collect(Collectors.toUnmodifiableSet());
            }
        };
    }

    /**
     * Method which separates the loading logic from the rest of the class.
     *
     * Changes to configuration file format only affect this part of the class.
     *
     * @param configString String containing configurations.
     * @return a Pair containing the two maps which represent the information stored
     *         in the file.
     */
    private Triple<Double, Map<EnemyArchetype, Double>, Map<EnemyLevel, Integer>> loadConfig(
            final String configString) {
        try {
            final JSONObject config = new JSONObject(configString);

            final Double vf = config.getDouble("vf");
            final Map<EnemyArchetype, Double> r = new HashMap<>();
            final Map<EnemyLevel, Integer> pl = new HashMap<>();

            config.getJSONArray("archetypes").forEach(
                    (Object o) -> {
                        assert o instanceof JSONObject;
                        final JSONObject level = (JSONObject) o;
                        r.put(EnemyArchetype.valueOf(level.getString("archetype")), level.getDouble("rateo"));
                    });
            config.getJSONArray("levels").forEach(
                    (Object o) -> {
                        assert o instanceof JSONObject;
                        final JSONObject level = (JSONObject) o;
                        pl.put(EnemyLevel.valueOf(level.getString("level")), level.getInt("powerlevel"));
                    });

            return Triple.of(vf, ImmutableMap.copyOf(r), ImmutableMap.copyOf(pl));

        } catch (JSONException e) {
            throw new ConfigurationLoadingException(
                    "Configuration string for enemy catalogue is syntactically incorrect.", e);
        }
    }

    /**
     * Method which incapsulates all the constraints a given configuration has to
     * respect.
     *
     * Will throw a RuntimeException if constraints are not respected.
     *
     * @param config the triple containing the configuration to check.
     */
    private void checkConstraints(
            final Triple<Double, Map<EnemyArchetype, Double>, Map<EnemyLevel, Integer>> config) {

        final Double valueFactor = config.getLeft();
        if (valueFactor <= 0) {
            throw new ConfigurationLoadingException("valueFactor must be > 0.");
        }

        final Map<EnemyArchetype, Double> r = config.getMiddle();
        if (!r.keySet().containsAll(Set.of(EnemyArchetype.values()))) {
            throw new ConfigurationLoadingException("Rates not supplied for every archetype.");
        }
        if (!r.values().stream().allMatch(v -> v > 0)) {
            throw new ConfigurationLoadingException("Rates must be > 0.");
        }

        final Map<EnemyLevel, Integer> pl = config.getRight();
        if (!pl.keySet().containsAll(Set.of(EnemyLevel.values()))) {
            throw new ConfigurationLoadingException("Power levels not supplied for every level.");
        }
        if (!pl.values().stream().allMatch(v -> v > 0)) {
            throw new ConfigurationLoadingException("Power levels must be > 0.");
        }
    }

    /**
     * Builds an implementation EnemyType from the given "high level" enemy type.
     *
     * @param enemyType the "high level" enemytype
     * @return the built RichEnemyType
     */
    private RichEnemyType build(final EnemyType enemyType) {
        final EnemyLevel l = enemyType.level();
        final EnemyArchetype t = enemyType.type();
        final int powerLevel = powerlevels.get(l);
        final int hp = (int) Math.sqrt(powerLevel / rateos.get(t));
        final int speed = (int) (hp * rateos.get(t));
        final int value = (int) (powerlevels.get(l) * valueFactor);
        /**
         * Anonymous class which implements a RichEnemyType.
         */
        return new RichEnemyType() {
            /**
             * {@inheritDoc}.
             */
            @Override
            public EnemyLevel level() {
                return l;
            }

            /**
             * {@inheritDoc}.
             */
            @Override
            public EnemyArchetype type() {
                return t;
            }

            /**
             * {@inheritDoc}.
             */
            @Override
            int getMaxHP() {
                return hp;
            }

            /**
             * {@inheritDoc}.
             */
            @Override
            int getSpeed() {
                return speed;
            }

            /**
             * {@inheritDoc}.
             */
            @Override
            int getValue() {
                return value;
            }

            /**
             * {@inheritDoc}.
             */
            @Override
            int getPowerLevel() {
                return powerLevel;
            }
        };
    }
}
