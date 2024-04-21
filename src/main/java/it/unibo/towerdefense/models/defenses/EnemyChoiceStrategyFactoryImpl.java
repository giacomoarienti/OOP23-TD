package it.unibo.towerdefense.models.defenses;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import it.unibo.towerdefense.commons.LogicalPosition;

/**
 * Implementation of EnemyChoiceStrategyFactory.
 */
public class EnemyChoiceStrategyFactoryImpl implements EnemyChoiceStrategyFactory {
    /**
     * Generic method for Strategy creation.
     * @return the built enemyChoiceStrategy.
     * @param isTargetValid checks for every target if it is a eligible hit.
     * @param mapValidTargets maps wich valid targets are going to be hit.
     * @param mapDamage maps the filtered entities for damage based on a passed integer.
     * @param basePosition the position used for checking validity of targets
     */
    private EnemyChoiceStrategy genericModel(final BiPredicate<LogicalPosition, LogicalPosition> isTargetValid, 
    final Function<Map<Integer, Pair<LogicalPosition, Integer>>, Map<Integer, Pair<LogicalPosition, Integer>>> mapValidTargets,
    final BiFunction<Integer, Map<Integer, Pair<LogicalPosition, Integer>>, Map<Integer, Integer>> mapDamage, 
    final LogicalPosition basePosition) {
        return new EnemyChoiceStrategy() {
            /**
             * {@inheritDoc}
             */
            @Override
            public Map<Integer, Integer> execute(final List<Pair<LogicalPosition, Integer>> availableTargets,
            final int baseDamage) {
                /*map entities to list index.*/
                Map<Integer, Pair<LogicalPosition, Integer>> mappedAvailableTargets = IntStream.range(0, availableTargets.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> i, i -> availableTargets.get(i))); 
                    /**get valid targets */
                Map<Integer, Pair<LogicalPosition, Integer>> validTargets = mappedAvailableTargets.entrySet()
                        .stream()
                        .filter(x -> isTargetValid.test(x.getValue().getKey(), basePosition))
                        .collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue()));
                    /**get targets that are going to be hit and return mapped damage. */
                Map<Integer, Pair<LogicalPosition, Integer>> finalTargets = mapValidTargets.apply(validTargets);
                return mapDamage.apply(baseDamage, finalTargets);
            } 
        };
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public EnemyChoiceStrategy closestTargets(final int maxTargets, final int range, final LogicalPosition position) {
        return genericModel((x1, x2) -> x1.distanceTo(x2) <= range,
            map -> map.entrySet()
            .stream() 
            .sorted((p1, p2) -> 
                Double.compare(p1.getValue().getKey().distanceTo(position), p2.getValue().getKey().distanceTo(position)))
            .limit(maxTargets)
            .collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue())),
            (damage, map) -> map.entrySet()
            .stream()
            .collect(Collectors.toMap(m -> m.getKey(), m -> damage)),
            position
        );
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public EnemyChoiceStrategy closestTargetWithAreaDamage(final int damageRange, final int range, 
    final LogicalPosition position) {
        return null;
    }
}
