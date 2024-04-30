package it.unibo.towerdefense.models.enemies;

import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;

public class WavePolicySupplierImpl implements WavePolicySupplier{

    private static SortedMap<Integer,Predicate<EnemyType>> predicates;
    private static SortedMap<Integer, Integer> lengths;
    private static SortedMap<Integer, Integer> rates;

    private static final Pattern linePattern = Pattern.compile("^(\\d+) (\\d+) (\\d+) ([A-Z]+( [A-Z]+)*)");

    //"it/unibo/towerdefense/models/enemies/waves.config"
    WavePolicySupplierImpl(String configFile){
        try(Scanner config = new Scanner(new BufferedInputStream(ClassLoader.getSystemResourceAsStream(configFile)))){
            predicates = new TreeMap<>();
            lengths = new TreeMap<>();
            rates = new TreeMap<>();
            try {
                while(config.hasNextLine()){
                    Matcher m = linePattern.matcher(config.nextLine());
                    Boolean match = m.matches();
                    assert match; //got a problem without using this middle step
                    try{
                        int wave = Integer.parseInt(m.group(1));
                        int length = Integer.parseInt(m.group(2));
                        int rate = Integer.parseInt(m.group(3));
                        Predicate<EnemyType> acceptedTypes = translate(m.group(4));
                        predicates.put(wave, acceptedTypes);
                        lengths.put(wave, length);
                        rates.put(wave, rate);
                    }catch(Exception e){
                        throw e;
                    }
                }
            }catch (Exception e) {
                throw new RuntimeException("waves.config is ill-formatted", e);
            }
        }catch (Exception e) {
            throw new RuntimeException("Failed to load waves configuration", e);
        }
    }

    /**
     * Returns a predicate which tests true for any of the types contained in s.
     * @param s the string containing accepted types
     * @return the corresponding predicate
     */
    private static Predicate<EnemyType> translate(String s){
        Predicate<EnemyType> ret = et -> false;
        for (String type : s.split(" ")){
            EnemyLevel l = EnemyLevel.valueOf(type.substring(0, type.length()-1));
            EnemyArchetype t = EnemyArchetype.valueOf(type.substring(type.length()-1));
            ret = ret.or(
                et -> et.getEnemyLevel().equals(l) && et.getEnemyArchetype().equals(t)
            );
        }
        return ret;
    }

    @Override
    public Predicate<EnemyType> getPredicate(Integer wave) {
        return predicates.headMap(wave + 1).values().stream().reduce(et -> false, (p1, p2) -> p1.or(p2));
    }

    @Override
    public Integer getLength(Integer wave) {
        return lengths.get(lengths.headMap(wave + 1).lastKey());
    }

    @Override
    public Integer getCyclesPerSpawn(Integer wave) {
        return rates.get(rates.headMap(wave + 1).lastKey());
    }

}
