package org.example.weatheranalyticsservice.integration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.example.weatheranalyticsservice.configuration.KafkaConfiguration.KafkaProperties;
import org.example.weatheranalyticsservice.dto.WeatherReport;
import org.example.weatheranalyticsservice.dto.WeatherStatus;
import org.example.weatheranalyticsservice.generator.NumericRandomGenerator;
import org.example.weatheranalyticsservice.queue.WeatherQueueProducer;
import org.example.weatheranalyticsservice.scheduler.WeatherSimulationScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest()
@ActiveProfiles("test")
@EmbeddedKafka(topics = "${kafka.topic}", partitions = 1)
public class IntegrationKafkaTest {

    @TestConfiguration
    static class SchedulerTestConfig {
        @Bean
        WeatherSimulationScheduler weatherSimulationScheduler(
                WeatherQueueProducer weatherQueueProducer,
                NumericRandomGenerator<Integer> numericRandomGenerator
        ) {
            return new WeatherSimulationScheduler(weatherQueueProducer, numericRandomGenerator);
        }
    }

    @TestConfiguration
    static class KafkaTestConfig {

        @Bean
        Consumer<String, WeatherReport> consumer(ConsumerFactory<?, ?> consumerFactory) {
            return (Consumer<String, WeatherReport>) consumerFactory.createConsumer();
        }
    }

    @Autowired
    private WeatherSimulationScheduler scheduler;

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private Consumer<String, WeatherReport> consumer;

    @MockitoBean
    private NumericRandomGenerator<Integer> randomGenerator;


    @MockitoSpyBean
    private WeatherQueueProducer weatherQueueProducer;


    @Test
    void shouldGenerateAndSendWeatherReport() {
        doReturn(0).when(randomGenerator).generateNumber(anyInt(), anyInt());
        doReturn(0).when(randomGenerator).generateNumber(WeatherStatus.values().length);

        scheduler.simulate();

        verify(weatherQueueProducer, times(4)).sendWeatherReport(any());

        consumer.subscribe(List.of(kafkaProperties.topic()));
        ConsumerRecords<String, WeatherReport> records = consumer.poll(Duration.ofSeconds(5L));
        assertThat(records.count()).isEqualTo(4);

        records.forEach(record -> {
            assertThat(record.value().temperature()).isZero();
            assertThat(record.value().status())
                    .isEqualByComparingTo(WeatherStatus.values()[0]);
        });
    }
}
