package it.unibo.towerdefense.models.enemies;

import it.unibo.towerdefense.controllers.enemies.EnemyArchetype;
import it.unibo.towerdefense.controllers.enemies.EnemyLevel;
import it.unibo.towerdefense.controllers.enemies.EnemyType;

/**
 * Simple factory to build enemytypes for testing.
 */
abstract class TestingEnemyType {
        /**
         * Builds a RichEnemyType with the given characteristics.
         *
         * @param level the level
         * @param type  the type
         * @param hp    the maxHp
         * @param speed the speed
         * @param value the value
         * @return the built RichEnemyType
         */
        static RichEnemyType build(final EnemyLevel level, final EnemyArchetype type, final int hp, final int speed,
                        final int value) {
                /**
                 * Anonymous class which implements RichEnemyType.
                 */
                return new RichEnemyType() {
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
                        public EnemyLevel level() {
                                return level;
                        }

                        /**
                         * {@inheritDoc}.
                         */
                        @Override
                        public EnemyArchetype type() {
                                return type;
                        }
                };
        }

        /**
         * Builds an EnemyType with the given characteristics.
         *
         * @param level the level
         * @param type  the type
         * @return the built EnemyType
         */
        static EnemyType build(final EnemyLevel level, final EnemyArchetype type) {
                /**
                 * Anonymous class which implements RichEnemyType.
                 */
                return new EnemyType() {
                        /**
                         * {@inheritDoc}.
                         */
                        @Override
                        public EnemyLevel level() {
                                return level;
                        }

                        /**
                         * {@inheritDoc}.
                         */
                        @Override
                        public EnemyArchetype type() {
                                return type;
                        }
                };
        }
}