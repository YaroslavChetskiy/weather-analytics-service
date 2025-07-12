package org.example.weatheranalyticsservice.repository;

import lombok.RequiredArgsConstructor;
import org.example.weatheranalyticsservice.dto.WeatherReport;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
public class InMemoryWeatherReportRepository implements WeatherReportRepository {

    private static final List<WeatherReport> WEATHER_REPORT_LIST = new CopyOnWriteArrayList<>();

    @Override
    public void addWeatherReport(WeatherReport weatherReport) {
        WEATHER_REPORT_LIST.add(weatherReport);
    }

    @Override
    public List<WeatherReport> findAll() {
        return WEATHER_REPORT_LIST;
    }

    @Override
    public List<WeatherReport> findUpcomingWeek() {
        LocalDate now = LocalDate.now();
        LocalDate weekAhead = now.plusDays(7L);
        return WEATHER_REPORT_LIST.stream()
                .filter(report ->
                        report.fixationDate().isAfter(now)
                        && !report.fixationDate().isAfter(weekAhead))
                .toList();
    }
}
