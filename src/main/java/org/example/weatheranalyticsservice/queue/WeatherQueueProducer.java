package org.example.weatheranalyticsservice.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weatheranalyticsservice.configuration.KafkaConfiguration.KafkaProperties;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j
public class WeatherQueueProducer {

    private final KafkaTemplate<String, WeatherReport> kafkaTemplate;

    private final KafkaProperties kafkaProperties;


    public void sendWeatherReport(WeatherReport weatherReport) {
        log.info("Sending message: {}", weatherReport);
        kafkaTemplate.send(kafkaProperties.topic(), weatherReport);
    }
}
