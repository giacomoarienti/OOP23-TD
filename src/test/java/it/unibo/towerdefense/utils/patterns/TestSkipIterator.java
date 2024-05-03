package it.unibo.towerdefense.utils.patterns;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSkipIterator {

    @Test
    void testAll(){
        int skip = 3;
        int stop = 10;
        SkipIterator<Integer> tested = new SkipIterator<>(IntStream.range(0, stop).iterator(), skip);
        for(int i = 0; i < stop - 1; i++){
            for(int j = 0; j < skip; j++){
                Assertions.assertTrue(tested.hasNext());
                if(j==0){
                    Assertions.assertEquals(Optional.of(i), tested.next());
                }else{
                    Assertions.assertEquals(Optional.empty(), tested.next());
                }
            }
        }
        Assertions.assertTrue(tested.hasNext());
        Assertions.assertEquals(Optional.of(stop-1), tested.next());
        Assertions.assertFalse(tested.hasNext());
    }
}
