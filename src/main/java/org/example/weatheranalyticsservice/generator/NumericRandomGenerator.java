package org.example.weatheranalyticsservice.generator;

public interface NumericRandomGenerator<T extends Number> {

    T generateNumber(T origin, T bound);

    T generateNumber(T bound);
}
