package org.example.weatheranalyticsservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.weatheranalyticsservice.dto.CityWeatherStats;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.example.weatheranalyticsservice.service.WeatherAnalyticService;
import org.example.weatheranalyticsservice.service.WeatherReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherReportService weatherReportService;
    private final WeatherAnalyticService weatherAnalyticService;

    @GetMapping
    public List<WeatherReport> findAll() {
        return weatherReportService.findAll();
    }

    @GetMapping("/upcoming")
    public List<WeatherReport> getUpcomingWeekReports() {
        return weatherReportService.findUpcomingWeek();
    }

    @GetMapping("/upcoming/stats")
    public List<CityWeatherStats> getUpcomingWeekStats() {
        return weatherAnalyticService.getUpcomingWeekStats();
    }
}
