package com.targetready.orderService.configTests;
import brave.Tracing;
import brave.kafka.clients.KafkaTracing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TracingConfigTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testTracingBean() {
        Tracing tracing = applicationContext.getBean(Tracing.class);
        assertNotNull(tracing, "Tracing bean should be instantiated");
    }

    @Test
    public void testKafkaTracingBean() {
        KafkaTracing kafkaTracing = applicationContext.getBean(KafkaTracing.class);
        assertNotNull(kafkaTracing, "KafkaTracing bean should be instantiated");
    }
}

