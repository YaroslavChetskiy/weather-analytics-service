package org.example.weatheranalyticsservice.dto;

import java.time.LocalDate;

public record CityWeatherStats(
        City city,
        Long rainyDays,
        Long sunnyDays,
        Long cloudyDays,

        TempStats hottestDay,
        TempStats coldestDay,

        Double avgTemperature
) {


    public record TempStats(
            Integer temperature,
            LocalDate day
    ) {

    }
}
