package com.targetready.bankService;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.kafka.clients.KafkaTracing;

import static org.mockito.Mockito.*;

import com.targetready.bankService.producer.BankProducer;
import com.targetready.orderService.model.Invoice;
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

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BankProducerTest {


    private Invoice invoice = new Invoice();

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private Tracing tracing;

    @Mock
    private Tracer tracer;

    @Mock
    private Span span;

    @InjectMocks
    private BankProducer bankProducer;

    @Autowired
    private KafkaTracing kafkaTracing;

    @BeforeEach
    public void setUp() {
        when(tracing.tracer()).thenReturn(tracer);
        when(tracer.nextSpan()).thenReturn(span);
        when(span.name(anyString())).thenReturn(span);
        when(span.start()).thenReturn(span);
        invoice.setOrderId("100");
        invoice.setAmount(1000);
        invoice.setStatus(true);
        invoice.setTransactionId(UUID.randomUUID().toString());
    }

    @Test
    public void testSendInvoice_success() throws InterruptedException {
        invoice.setBank("AXIS");

        bankProducer.sendInvoice(invoice);

        verify(kafkaTemplate, never()).send(anyString(), any(Invoice.class));
        verify(span, times(1)).tag("success", "Invoice Generated");
        verify(span, times(1)).finish();

    }


    @Test
    public void testSendInvoice_supported() throws InterruptedException {
        invoice.setBank("ICICI");


        bankProducer.sendInvoice(invoice);

        verify(kafkaTemplate, never()).send(anyString(), any(Invoice.class));
        verify(span, times(1)).tag("error", "Bank server is down");
        verify(span, times(1)).finish();

    }


    @Test
    public void testSendInvoice_serverDown() throws InterruptedException {

        invoice.setBank("HDFC");


        bankProducer.sendInvoice(invoice);

        verify(kafkaTemplate, never()).send(anyString(), any(Invoice.class));
        verify(span, times(1)).tag("error", "Bank not supported");
        verify(span, times(1)).finish();

    }
}
