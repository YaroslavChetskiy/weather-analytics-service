package org.example.weatheranalyticsservice.dto;

import java.time.LocalDate;

public record WeatherReport(
        Integer temperature,
        WeatherStatus status,
        City city,
        LocalDate fixationDate
) {

}
