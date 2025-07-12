package org.example.weatheranalyticsservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.weatheranalyticsservice.dto.City;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.example.weatheranalyticsservice.dto.WeatherStatus;
import org.example.weatheranalyticsservice.generator.NumericRandomGenerator;
import org.example.weatheranalyticsservice.queue.WeatherQueueProducer;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

@RequiredArgsConstructor
public class WeatherSimulationScheduler {

    private static final Integer MIN_TEMPERATURE = 0;
    private static final Integer MAX_TEMPERATURE = 35;

    private final WeatherQueueProducer weatherProducer;

    private final NumericRandomGenerator<Integer> numericRandomGenerator;

    private LocalDate currentDate = LocalDate.now();

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void simulate() {
        for (City city : City.values()) {
            Integer randomTemperature = numericRandomGenerator.generateNumber(MIN_TEMPERATURE, MAX_TEMPERATURE + 1);
            Integer randomStatusIndex = numericRandomGenerator.generateNumber(WeatherStatus.values().length);
            WeatherReport report = new WeatherReport(
                    randomTemperature,
                    WeatherStatus.values()[randomStatusIndex],
                    city,
                    currentDate
            );
            weatherProducer.sendWeatherReport(report);
        }
        currentDate = currentDate.plusDays(1L);
    }
}
