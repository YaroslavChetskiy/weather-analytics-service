package org.example.weatheranalyticsservice.generator;


import java.util.concurrent.ThreadLocalRandom;

public class IntegerRandomGenerator implements NumericRandomGenerator<Integer> {


    @Override
    public Integer generateNumber(Integer origin, Integer bound) {
        if (origin == null || bound == null) {
            throw new NullPointerException("Origin and bound must be non-null");
        }
        return ThreadLocalRandom.current().nextInt(origin, bound);
    }

    @Override
    public Integer generateNumber(Integer bound) {
        if (bound == null) {
            throw new NullPointerException("Bound must be non-null");
        }
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
