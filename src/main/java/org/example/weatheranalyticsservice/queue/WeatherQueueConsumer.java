package org.example.weatheranalyticsservice.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.example.weatheranalyticsservice.service.WeatherReportService;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
@Slf4j
public class WeatherQueueConsumer {

    private final WeatherReportService weatherReportService;


    @KafkaListener(topics = "${kafka.topic}")
    public void listenWeatherReportMessage(WeatherReport weatherReport) {
        log.info("Consumed message: {}", weatherReport);
        weatherReportService.saveReport(weatherReport);
    }
}
