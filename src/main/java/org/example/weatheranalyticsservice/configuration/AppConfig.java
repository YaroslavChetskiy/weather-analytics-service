package org.example.weatheranalyticsservice.configuration;

import org.example.weatheranalyticsservice.generator.IntegerRandomGenerator;
import org.example.weatheranalyticsservice.generator.NumericRandomGenerator;
import org.example.weatheranalyticsservice.repository.InMemoryWeatherReportRepository;
import org.example.weatheranalyticsservice.repository.WeatherReportRepository;
import org.example.weatheranalyticsservice.service.WeatherAnalyticService;
import org.example.weatheranalyticsservice.service.WeatherReportService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public NumericRandomGenerator<Integer> numericRandomGenerator() {
        return new IntegerRandomGenerator();
    }

    @Bean
    public WeatherReportRepository weatherReportRepository() {
        return new InMemoryWeatherReportRepository();
    }

    @Bean
    public WeatherAnalyticService weatherAnalyticService(WeatherReportRepository repository) {
        return new WeatherAnalyticService(repository);
    }

    @Bean
    public WeatherReportService weatherReportService(WeatherReportRepository repository) {
        return new WeatherReportService(repository);
    }
}
