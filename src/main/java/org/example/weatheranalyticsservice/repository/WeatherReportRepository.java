package org.example.weatheranalyticsservice.repository;

import org.example.weatheranalyticsservice.dto.WeatherReport;

import java.util.List;

public interface WeatherReportRepository {

    void addWeatherReport(WeatherReport weatherReport);

    List<WeatherReport> findAll();

    List<WeatherReport> findUpcomingWeek();
}
