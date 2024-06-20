package com.targetready.orderService.service;


import brave.Span;
import brave.Tracing;
import brave.kafka.clients.KafkaTracing;
import com.targetready.orderService.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {


    @Autowired
    private KafkaTracing kafkaTracing;

    @Autowired
    private Tracing tracing;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate=kafkaTemplate;
    }

//    @PostMapping
    public void sendOrder(Order order) {
        Span span = tracing.tracer().nextSpan().name("order").start();
        try{
            validateOrder(order);
            span.tag("success", "Valid order");
            kafkaTemplate.send("orders", order);
        }
        catch (IllegalArgumentException e) {
            span.tag("error",e.getMessage());
        }
        catch(Exception e) {
            span.tag("error", "An error occurred while processing the order: " + e.getMessage());

        }
        finally {
            span.finish();
        }
    }

    private void validateOrder(Order order) {
        if(order==null || order.getOrderId()==null) {
            throw new IllegalArgumentException("Invalid order data");
        }
        if(order.getAmount()<0) {
            throw new IllegalArgumentException("Price should be greater than 0");
        }
        if(order.getId()<=0) {
            throw new IllegalArgumentException("Product is out of Stock");
        }
    }
}

