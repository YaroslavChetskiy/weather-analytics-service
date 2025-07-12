package org.example.weatheranalyticsservice.service;

import org.example.weatheranalyticsservice.dto.City;
import org.example.weatheranalyticsservice.dto.CityWeatherStats;
import org.example.weatheranalyticsservice.dto.CityWeatherStats.TempStats;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.example.weatheranalyticsservice.dto.WeatherStatus;
import org.example.weatheranalyticsservice.repository.WeatherReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WeatherAnalyticServiceTest {

    private static final LocalDate NOW = LocalDate.now();

    private static final List<WeatherReport> REPORTS = List.of(
            new WeatherReport(
                    0, WeatherStatus.RAIN, City.SAINT_PETERSBURG,
                    NOW.plusDays(1)
            ),
            new WeatherReport(
                    5, WeatherStatus.CLOUDY, City.SAINT_PETERSBURG,
                    NOW.plusDays(2)
            ),
            new WeatherReport(
                    0, WeatherStatus.RAIN, City.CHUKOTKA, NOW.plusDays(1)
            ),
            new WeatherReport(
                    5, WeatherStatus.SUNNY, City.CHUKOTKA, NOW.plusDays(2)
            )
    );

    private static final CityWeatherStats PETERSBURG_WEATHER_STATS = new CityWeatherStats(
            City.SAINT_PETERSBURG,
            1L,
            0L,
            1L,
            new TempStats(5, NOW.plusDays(2)),
            new TempStats(0, NOW.plusDays(1)),
            2.5
    );

    private static final CityWeatherStats CHUKOTKA_WEATHER_STATS = new CityWeatherStats(
            City.CHUKOTKA,
            1L,
            1L,
            0L,
            new TempStats(5, NOW.plusDays(2)),
            new TempStats(0, NOW.plusDays(1)),
            2.5
    );

    @Mock
    private WeatherReportRepository repository;

    @InjectMocks
    private WeatherAnalyticService analyticService;

    @Test
    void getUpcomingWeekStats() {
        Mockito.doReturn(REPORTS).when(repository).findUpcomingWeek();

        List<CityWeatherStats> actualResult = analyticService.getUpcomingWeekStats();

        assertThat(actualResult).hasSize(2);

        checkStats(City.SAINT_PETERSBURG, actualResult, PETERSBURG_WEATHER_STATS);
        checkStats(City.CHUKOTKA, actualResult, CHUKOTKA_WEATHER_STATS);
    }

    private void checkStats(City city,
                            List<CityWeatherStats> allStats,
                            CityWeatherStats expectedStats
    ) {
        var cityStatsOptional = allStats.stream()
                .filter(stats -> stats.city() == city)
                .findFirst();

        assertThat(cityStatsOptional).isPresent();

        var cityStats = cityStatsOptional.get();

        assertThat(cityStats).isEqualTo(expectedStats);
    }
}