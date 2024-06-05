package com.targetready.bankService.producer;


import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.kafka.clients.KafkaTracing;
import com.targetready.orderService.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class bankProducer {


    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private KafkaTracing kafkaTracing;

    @Autowired
    private Tracing tracing;


    @Autowired
    public bankProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void sendInvoice(Invoice invoice) {
        Span span = tracing.tracer().nextSpan().name("bank").start();
        try{
            if(Objects.equals(invoice.getBank(), "ICICI")) {
                throw new IllegalArgumentException("Bank server is down");
            }
            else if(Objects.equals(invoice.getBank(), "HDFC")) {
                throw new IllegalArgumentException("Bank not supported");
            }
            span.tag("success" , "Invoice Generated");
            kafkaTemplate.send("invoices",invoice);
        }
        catch (IllegalArgumentException e) {
            span.tag("error", e.getMessage());
        }
        finally {
            span.finish();
        }

    }
}
