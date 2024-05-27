package it.unibo.towerdefense.commons.patterns;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for SkipIterator.
 */
class TestSkipIterator {

    /**
     * General test for class functioning.
     */
    @Test
    void testAll() {
        final int skip = 3;
        final int stop = 10;
        final SkipIterator<Integer> tested = new SkipIterator<>(IntStream.range(0, stop).iterator(), skip);
        for (int i = 0; i < stop - 1; i++) {
            for (int j = 0; j < skip; j++) {
                Assertions.assertTrue(tested.hasNext());
                if (j == 0) {
                    Assertions.assertEquals(Optional.of(i), tested.next());
                } else {
                    Assertions.assertEquals(Optional.empty(), tested.next());
                }
            }
        }
        Assertions.assertTrue(tested.hasNext());
        Assertions.assertEquals(Optional.of(stop - 1), tested.next());
        Assertions.assertFalse(tested.hasNext());
    }
}
