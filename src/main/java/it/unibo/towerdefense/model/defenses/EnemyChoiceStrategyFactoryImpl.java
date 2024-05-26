package it.unibo.towerdefense.model.defenses;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import java.util.Map;


import it.unibo.towerdefense.commons.engine.LogicalPosition;
import it.unibo.towerdefense.model.enemies.Enemy;

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
    final Function<Map<Integer, ? extends Enemy>, Map<Integer, ? extends Enemy>> mapValidTargets,
    final BiFunction<Integer, Map<Integer, ? extends Enemy>, Map<Integer, Integer>> mapDamage,
    final LogicalPosition basePosition) {
        return new EnemyChoiceStrategy() {
            /**
             * {@inheritDoc}
             */
            @Override
            public Map<Integer, Integer> execute(final List<? extends Enemy> availableTargets,
            final int baseDamage) {
                /**No need for calculations if list is empty.*/
                if (availableTargets.size() == 0) {
                    return Map.of();
                }
                /*map entities to list index.*/
                Map<Integer, ? extends Enemy> mappedAvailableTargets = IntStream.range(0, availableTargets.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> i, i -> availableTargets.get(i)));
                    /**get valid targets */
                Map<Integer, ? extends Enemy> validTargets = mappedAvailableTargets.entrySet()
                        .stream()
                        .filter(x -> isTargetValid.test(x.getValue().getPosition(), basePosition))
                        .collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue()));
                    /**get targets that are going to be hit and return mapped damage. */
                Map<Integer, ? extends Enemy> finalTargets = mapValidTargets.apply(validTargets);
                return mapDamage.apply(baseDamage, finalTargets);
            }
        };
    }

    /**@return the closest entitie to a defined position.
     * @param entities the entities to check.
     * @param point the position we want to see distance to.
    */
    private LogicalPosition getClosestTo(final Map<Integer, ? extends Enemy> entities,
    final LogicalPosition point) {
        return entities.entrySet()
        .stream()
        .sorted((p1, p2) ->
            Double.compare(p1.getValue().getPosition().distanceTo(point),
            p2.getValue().getPosition().distanceTo(point)))
        .findFirst()
        .get()
        .getValue().getPosition();
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
                Double.compare(p1.getValue().getPosition().distanceTo(position),
                               p2.getValue().getPosition().distanceTo(position)))
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
        return genericModel((x1, x2) -> x1.distanceTo(x2) <= range,
        map -> map.entrySet()
        .stream()
        .filter(ent -> ent.getValue().getPosition().distanceTo(getClosestTo(map, position)) <= damageRange)
        .collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue())),
        (damage, map) -> map.entrySet()
        .stream()
        .collect(Collectors.toMap(m -> m.getKey(), m -> damage)),
        position);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public EnemyChoiceStrategy closestToEndMap(final int range,
    final LogicalPosition position) {
        return genericModel((x1, x2) -> x1.distanceTo(x2) > range,
        map -> map.entrySet()
        .stream()
        .sorted((ent1, ent2) -> Long.compare(
            ent2.getValue().getPosition().getDistanceWalked(), ent1.getValue().getPosition().getDistanceWalked()))
        .limit(1)
        .collect(Collectors.toMap(m -> m.getKey(), m -> m.getValue())),
        (damage, map) -> map.entrySet()
        .stream()
        .collect(Collectors.toMap(m -> m.getKey(), m -> damage)),
        position);
    }
}
