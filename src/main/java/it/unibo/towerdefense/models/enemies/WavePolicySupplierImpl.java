package it.unibo.towerdefense.models.enemies;

import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.towerdefense.commons.dtos.enemies.EnemyArchetype;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyLevel;
import it.unibo.towerdefense.commons.dtos.enemies.EnemyType;

/**
 * {@inheritDoc}.
 */
class WavePolicySupplierImpl implements WavePolicySupplier {

    /**
     * This data is stored in separate maps so that different
     * criteria can be specified independently.
     */
    private final SortedMap<Integer, Predicate<EnemyType>> predicates;
    private final SortedMap<Integer, Integer> powers;
    private final SortedMap<Integer, Integer> rates;

    /**
     * The constructor for the class.
     *
     * @param configFile the file from which to read the configuration.
     */
    WavePolicySupplierImpl(final String configFile) {
        Triple<SortedMap<Integer, Predicate<EnemyType>>,
            SortedMap<Integer, Integer>,
            SortedMap<Integer, Integer>> configValues = loadConfig(configFile);

        checkConstraints(configValues);

        this.predicates = configValues.getLeft();
        this.powers = configValues.getMiddle();
        this.rates = configValues.getRight();
    }

    /**
     * Method which separates the loading logic from the rest of the class.
     *
     * Changes to configuration file format only affect this part of the class.
     *
     * @param configString String containing configuration values.
     * @return a Triple containing the three sorted maps which represent the
     *         information stored
     *         in the file.
     */
    Triple<SortedMap<Integer, Predicate<EnemyType>>, SortedMap<Integer, Integer>, SortedMap<Integer, Integer>> loadConfig(
            final String configString) {

        SortedMap<Integer, Predicate<EnemyType>> p = new TreeMap<>();
        SortedMap<Integer, Integer> l = new TreeMap<>();
        SortedMap<Integer, Integer> r = new TreeMap<>();
        try {
            JSONArray config = new JSONArray(configString);
            config.forEach(
                    (Object o) -> {
                        assert o instanceof JSONObject;
                        JSONObject wave = (JSONObject) o;
                        int waveNumber = wave.getInt("wave");
                        Optional.ofNullable(wave.optIntegerObject("power", null))
                                .ifPresent((Integer i) -> l.put(waveNumber, i));
                        Optional.ofNullable(wave.optIntegerObject("rate", null))
                                .ifPresent((Integer i) -> r.put(waveNumber, i));
                        Optional.ofNullable(wave.optJSONArray("types", null)).ifPresent(
                                (JSONArray a) -> {
                                    p.put(waveNumber,
                                            translate(IntStream.range(0, a.length()).mapToObj(i -> a.getString(i))
                                                    .toList()));
                                });
                    });
            return Triple.of(p, l, r);
        } catch (Throwable t) {
            throw new RuntimeException("Failed to load waves configuration from given String", t);
        }
    }

    /**
     * Method which incapsulates all the constraints a given configuration has to
     * respect.
     *
     * Will throw a RuntimeException if constraints are not respected.
     *
     * @param values the triple containing the configuration to check.
     */
    private void checkConstraints(
            final Triple<SortedMap<Integer, Predicate<EnemyType>>, SortedMap<Integer, Integer>, SortedMap<Integer, Integer>> values) {

        final SortedMap<Integer, Predicate<EnemyType>> p = values.getLeft();
        final SortedMap<Integer, Integer> l = values.getMiddle();
        final SortedMap<Integer, Integer> r = values.getRight();

        try {
            /*
             * Wave 1 is mandatory.
             */
            assert p.containsKey(1) && l.containsKey(1) && r.containsKey(1);

            /*
             * Powerlevels can't be < 0.
             */
            p.keySet().forEach(i -> {
                assert i > 0;
            });

            /*
             * Cant have length <= 0.
             */
            l.entrySet().forEach(e -> {
                assert e.getKey() > 0;
                assert e.getValue() > 0;
            });

            /*
             * Cant have rate <= 0.
             */
            r.entrySet().forEach(e -> {
                assert e.getKey() > 0;
                assert e.getValue() > 0;
            });
        } catch (Throwable t) {
            throw new RuntimeException("Values contained in configuration file for wave policies are not permitted.",
                    t);
        }
    }

    /**
     * Returns a predicate which tests true for any of the types which textual
     * representation e.g "IA" for level I type A are contained in list.
     *
     * @param list the list containing String representation of accepted types
     * @return the corresponding predicate
     */
    private static Predicate<EnemyType> translate(final List<String> list) {
        Predicate<EnemyType> ret = et -> false;
        for (String type : list) {
            EnemyLevel l = EnemyLevel.valueOf(type.substring(0, type.length() - 1));
            EnemyArchetype t = EnemyArchetype.valueOf(type.substring(type.length() - 1));
            ret = ret.or(
                    et -> et.level().equals(l) && et.type().equals(t));
        }
        return ret;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Predicate<EnemyType> getPredicate(final Integer wave) {
        check(wave);
        return predicates.headMap(wave + 1).values().stream().reduce(et -> false, (p1, p2) -> p1.or(p2));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Integer getPower(final Integer wave) {
        check(wave);
        return powers.get(powers.headMap(wave + 1).lastKey());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Integer getCyclesPerSpawn(final Integer wave) {
        check(wave);
        return rates.get(rates.headMap(wave + 1).lastKey());
    }

    /**
     * Checks for the correctness of wave parameter.
     *
     * @param wave the integer to check, must be > 1
     */
    private void check(final Integer wave) {
        if (wave < 1) {
            throw new IllegalArgumentException("Wave numbers < 1 are not allowed.");
        }
    }
}
