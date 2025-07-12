package org.example.weatheranalyticsservice.repository;

import org.example.weatheranalyticsservice.dto.City;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.example.weatheranalyticsservice.dto.WeatherStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryWeatherReportRepositoryTest {

    private static final LocalDate NOW = LocalDate.now();

    private static final WeatherReport REPORT_IN_UPCOMING_WEEK = new WeatherReport(
            15,
            WeatherStatus.RAIN,
            City.SAINT_PETERSBURG,
            NOW.plusDays(2)
    );

    private static final WeatherReport REPORT_AFTER_ONE_WEEK = new WeatherReport(
            15,
            WeatherStatus.RAIN,
            City.SAINT_PETERSBURG,
            LocalDate.now().plusDays(10)
    );


    @Test
    void shouldFindWeekWeatherReportSlice() {
        InMemoryWeatherReportRepository repository = new InMemoryWeatherReportRepository();

        repository.addWeatherReport(REPORT_AFTER_ONE_WEEK);
        repository.addWeatherReport(REPORT_IN_UPCOMING_WEEK);

        List<WeatherReport> actualResult = repository.findUpcomingWeek();

        assertThat(actualResult).hasSize(1);
        assertThat(actualResult.get(0)).isEqualTo(REPORT_IN_UPCOMING_WEEK);
    }

}