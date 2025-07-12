package org.example.weatheranalyticsservice.service;

import lombok.RequiredArgsConstructor;
import org.example.weatheranalyticsservice.dto.City;
import org.example.weatheranalyticsservice.dto.CityWeatherStats;
import org.example.weatheranalyticsservice.dto.CityWeatherStats.TempStats;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.example.weatheranalyticsservice.dto.WeatherStatus;
import org.example.weatheranalyticsservice.repository.WeatherReportRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class WeatherAnalyticService {

    private final WeatherReportRepository weatherReportRepository;


    public List<CityWeatherStats> getUpcomingWeekStats() {
        List<WeatherReport> upcomingWeek = weatherReportRepository.findUpcomingWeek();

        return Arrays.stream(City.values())
                .map(city -> buildCityWeatherStats(city, upcomingWeek))
                .filter(Objects::nonNull)
                .toList();
    }

    private CityWeatherStats buildCityWeatherStats(City city, List<WeatherReport> weatherReports) {
        var cityReports = weatherReports.stream()
                .filter(report -> report.city() == city)
                .toList();

        if (cityReports.isEmpty()) {
            return null;
        }

        long rainyDays = countDays(cityReports, WeatherStatus.RAIN);
        long sunnyDays = countDays(cityReports, WeatherStatus.SUNNY);
        long cloudyDays = countDays(cityReports, WeatherStatus.CLOUDY);

        TempStats hottestDay = cityReports.stream()
                .max(Comparator.comparing(WeatherReport::temperature))
                .map(report -> new TempStats(report.temperature(), report.fixationDate()))
                .get();

        TempStats coldestDay = cityReports.stream()
                .min(Comparator.comparing(WeatherReport::temperature))
                .map(report -> new TempStats(report.temperature(), report.fixationDate()))
                .get();

        Double avgTemperature = cityReports.stream()
                .mapToInt(WeatherReport::temperature)
                .average()
                .orElse(Double.NaN);

        return new CityWeatherStats(city, rainyDays, sunnyDays, cloudyDays, hottestDay, coldestDay, avgTemperature);
    }

    private long countDays(List<WeatherReport> reports, WeatherStatus status) {
        return reports.stream()
                .filter(report -> report.status() == status).count();
    }
}
