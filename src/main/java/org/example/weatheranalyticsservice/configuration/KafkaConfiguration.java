package org.example.weatheranalyticsservice.configuration;

import jakarta.validation.constraints.NotNull;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.example.weatheranalyticsservice.queue.WeatherQueueConsumer;
import org.example.weatheranalyticsservice.queue.WeatherQueueProducer;
import org.example.weatheranalyticsservice.service.WeatherReportService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;

@Configuration
@EnableConfigurationProperties(KafkaConfiguration.KafkaProperties.class)
public class KafkaConfiguration {

    @Bean
    public WeatherQueueProducer weatherQueueProducer(KafkaTemplate<String, WeatherReport> kafkaTemplate,
                                                     KafkaProperties kafkaProperties) {
        return new WeatherQueueProducer(kafkaTemplate, kafkaProperties);
    }

    @Bean
    public WeatherQueueConsumer weatherQueueConsumer(WeatherReportService weatherReportService) {
        return new WeatherQueueConsumer(weatherReportService);
    }


    @Validated
    @ConfigurationProperties(prefix = "kafka")
    public record KafkaProperties(
            @NotNull String groupId,
            @NotNull String topic
    ) {

    }
}
