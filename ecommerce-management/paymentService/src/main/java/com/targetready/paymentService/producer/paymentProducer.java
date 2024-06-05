package com.targetready.paymentService.producer;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.kafka.clients.KafkaTracing;
import com.targetready.orderService.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.lang.Math.min;

@Service
public class paymentProducer {

    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaTracing kafkaTracing;

    @Autowired
    private Tracing tracing;


    @Autowired
    public paymentProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPayment(Payment payment) {
        Span span = tracing.tracer().nextSpan().name("payment").start();
        try {
            if (paymentSimulation(payment.getAmount())) {
                span.tag("success", "Payment initiated");
                kafkaTemplate.send("payments", payment);
            } else {
                throw new PaymentGatewayTimeoutException("Payment gateway timed out");
            }
        } catch (PaymentGatewayTimeoutException e) {
            span.tag("error", e.getMessage());
        }
        finally {
            span.finish();
        }

    }

    public boolean paymentSimulation(double amount) {
        if(amount>1000) return false;
        Random random = new Random();
        int processingTime = random.nextInt(4)+1;

        System.out.println("Simulating payment processing with a delay of " + processingTime + "seconds");
        int sleepSeconds = min(2, processingTime);

        try {
            Thread.sleep(sleepSeconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        return true;
    }
}
