package org.example.weatheranalyticsservice.configuration;

import jakarta.validation.constraints.NotNull;
import org.example.weatheranalyticsservice.generator.NumericRandomGenerator;
import org.example.weatheranalyticsservice.queue.WeatherQueueProducer;
import org.example.weatheranalyticsservice.scheduler.WeatherSimulationScheduler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Configuration
@ConditionalOnProperty(prefix = "app.scheduler", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(SchedulerConfiguration.SchedulerProperties.class)
@EnableScheduling
public class SchedulerConfiguration {


    @Bean
    public WeatherSimulationScheduler weatherSimulationScheduler(
            WeatherQueueProducer weatherQueueProducer,
            NumericRandomGenerator<Integer> numericRandomGenerator
    ) {
        return new WeatherSimulationScheduler(weatherQueueProducer, numericRandomGenerator);
    }


    @Validated
    @ConfigurationProperties(prefix = "app.scheduler")
    public record SchedulerProperties(
            @NotNull boolean enabled,
            @NotNull Duration interval
    ) {

    }


}
