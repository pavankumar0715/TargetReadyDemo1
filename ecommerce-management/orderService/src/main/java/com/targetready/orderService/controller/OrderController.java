package com.targetready.orderService.controller;


import com.targetready.orderService.model.Order;
import com.targetready.orderService.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/target/orders")
public class OrderController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        kafkaProducerService.sendOrder(order);
        return ResponseEntity.ok("Order sent to Kafka topic");
    }
}

