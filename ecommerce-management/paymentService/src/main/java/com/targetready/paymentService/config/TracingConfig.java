package com.targetready.paymentService.config;

import brave.Tracing;
import brave.kafka.clients.KafkaTracing;
import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
public class TracingConfig {

    @Bean
    public Tracing tracing() {
        OkHttpSender sender = OkHttpSender.create("http://localhost:9411/api/v2/spans");
        var zipkinSpanHandler = AsyncZipkinSpanHandler.create(sender);
        return Tracing.newBuilder()
                .localServiceName("payment-service")
                .addSpanHandler(zipkinSpanHandler)
                .sampler(Sampler.ALWAYS_SAMPLE)
                .build();
    }

    @Bean
    public KafkaTracing kafkaTracing(Tracing tracing){
        return KafkaTracing.newBuilder(tracing).build();
    }
}