package it.unibo.towerdefense.models.enemies;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONArray;
import org.json.JSONObject;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;
import it.unibo.towerdefense.controllers.enemies.EnemyType;

/**
 * {@inheritDoc}
 */
public class WavePolicySupplierImpl implements WavePolicySupplier {

    /**
     * This data is stored in separate maps so that different
     * criteria can be specified independently.
     */
    private final SortedMap<Integer, Predicate<EnemyType>> predicates;
    private final SortedMap<Integer, Integer> lengths;
    private final SortedMap<Integer, Integer> rates;

    /**
     * The constructor for the class.
     *
     * @param configFile the file from which to read the configuration.
     */
    WavePolicySupplierImpl(final String configFile) {
        Triple<SortedMap<Integer, Predicate<EnemyType>>, SortedMap<Integer, Integer>, SortedMap<Integer, Integer>> configValues = loadConfig(
                configFile);
        this.predicates = configValues.getLeft();
        this.lengths = configValues.getMiddle();
        this.rates = configValues.getRight();
    }

    /**
     * Method which separates the loading logic from the rest of the class.
     *
     * Changes to configuration file format only affect this part of the class.
     *
     * @param configFile name of the file from which to load configurations.
     * @return a Triple containing the three sorted maps which represent the
     *         information stored
     *         in the file.
     */
    Triple<SortedMap<Integer, Predicate<EnemyType>>, SortedMap<Integer, Integer>, SortedMap<Integer, Integer>> loadConfig(
            final String configFile) {
        SortedMap<Integer, Predicate<EnemyType>> p = new TreeMap<>();
        SortedMap<Integer, Integer> l = new TreeMap<>();
        SortedMap<Integer, Integer> r = new TreeMap<>();
        try (InputStream configStream = ClassLoader.getSystemResourceAsStream(configFile)) {
            JSONArray config = new JSONArray(new String(configStream.readAllBytes()));
            config.forEach(
                    (Object o) -> {
                        assert o instanceof JSONObject;
                        JSONObject wave = (JSONObject) o;
                        int waveNumber = wave.getInt("wave");
                        Optional.ofNullable(wave.optIntegerObject("length", null))
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

            assert p.containsKey(1) && l.containsKey(1) && r.containsKey(1);
            return Triple.of(p, l, r);
        } catch (Throwable t) {
            throw new RuntimeException("Failed to load waves configuration from " + configFile, t);
        }
    }

    /**
     * Returns a predicate which tests true for any of the types which textual
     * representation e.g "IA" for level I type A are contained in list.
     *
     * @param list the list containing String representation of accepted types
     * @return the corresponding predicate
     */
    private static Predicate<EnemyType> translate(List<String> list) {
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
     * {@inheritDoc}
     */
    @Override
    public Predicate<EnemyType> getPredicate(Integer wave) {
        return predicates.headMap(wave + 1).values().stream().reduce(et -> false, (p1, p2) -> p1.or(p2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getLength(Integer wave) {
        return lengths.get(lengths.headMap(wave + 1).lastKey());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getCyclesPerSpawn(Integer wave) {
        return rates.get(rates.headMap(wave + 1).lastKey());
    }

}
