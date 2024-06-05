package com.targetready.orderService.configTests;


import brave.kafka.clients.KafkaTracing;
import com.targetready.orderService.config.KafkaProducerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;



import static org.assertj.core.api.Assertions.assertThat;

class KafkaProducerConfigTest {

    private KafkaProducerConfig kafkaProducerConfig;
    private KafkaTracing kafkaTracing;

    @BeforeEach
    void setUp() {
        kafkaProducerConfig = new KafkaProducerConfig();
        kafkaTracing = Mockito.mock(KafkaTracing.class);
    }

    @Test
    void producerFactory_ShouldReturnProducerFactory() {
        ProducerFactory<String, Object> producerFactory = kafkaProducerConfig.producerFactory(kafkaTracing);
        assertThat(producerFactory).isInstanceOf(DefaultKafkaProducerFactory.class);

        Map<String, Object> configProps = ((DefaultKafkaProducerFactory<String, Object>) producerFactory).getConfigurationProperties();
        assertThat(configProps.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)).isEqualTo("localhost:9092");
        assertThat(configProps.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)).isEqualTo(StringSerializer.class);
        assertThat(configProps.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)).isEqualTo(JsonSerializer.class);
    }

    @Test
    void kafkaTemplate_ShouldReturnKafkaTemplate() {
        ProducerFactory<String, Object> producerFactory = kafkaProducerConfig.producerFactory(kafkaTracing);
        KafkaTemplate<String, Object> kafkaTemplate = kafkaProducerConfig.kafkaTemplate(producerFactory);

        assertThat(kafkaTemplate).isNotNull();
        assertThat(kafkaTemplate.getProducerFactory()).isEqualTo(producerFactory);
    }
}


