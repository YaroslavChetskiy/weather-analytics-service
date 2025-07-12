package org.example.weatheranalyticsservice.service;

import lombok.RequiredArgsConstructor;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.example.weatheranalyticsservice.repository.WeatherReportRepository;

import java.util.List;

@RequiredArgsConstructor
public class WeatherReportService {

    private final WeatherReportRepository weatherReportRepository;

    public void saveReport(WeatherReport report) {
        weatherReportRepository.addWeatherReport(report);
    }

    public List<WeatherReport> findAll() {
        return weatherReportRepository.findAll();
    }

    public List<WeatherReport> findUpcomingWeek() {
        return weatherReportRepository.findUpcomingWeek();
    }
}
