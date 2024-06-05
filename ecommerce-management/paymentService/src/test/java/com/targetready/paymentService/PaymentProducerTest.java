package com.targetready.paymentService;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.kafka.clients.KafkaTracing;
import com.targetready.orderService.model.Payment;
import com.targetready.paymentService.producer.paymentProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PaymentProducerTest {

    private Payment payment = new Payment();

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private Tracing tracing;

    @Mock
    private Tracer tracer;

    @Mock
    private Span span;

    //    #TODO change the naming convention
    @InjectMocks
    private paymentProducer obj;


    @Autowired
    private KafkaTracing kafkaTracing;

    @BeforeEach
    public void setUp() {
        when(tracing.tracer()).thenReturn(tracer);
        when(tracer.nextSpan()).thenReturn(span);
        when(span.name(anyString())).thenReturn(span);
        when(span.start()).thenReturn(span);
        payment.setOrderId("100");
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setBank("AXIS");

    }

    @Test
    public void testSendPayment_success() throws InterruptedException {
        payment.setAmount(100);

        obj.sendPayment(payment);


        verify(span, times(1)).tag("success", "Payment initiated");
        verify(kafkaTemplate, never()).send(anyString(), any(Payment.class));
        verify(span, times(1)).finish();

    }


    @Test
    public void testSendPayment_timeout() throws InterruptedException {
        payment.setAmount(10000);


        obj.sendPayment(payment);

        verify(kafkaTemplate, never()).send(anyString(), any(Payment.class));
        verify(span, times(1)).tag("error", "Payment gateway timed out");
        verify(span, times(1)).finish();

    }


}

