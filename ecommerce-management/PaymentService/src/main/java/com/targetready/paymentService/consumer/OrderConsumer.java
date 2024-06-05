package com.targetready.paymentService.consumer;


import com.targetready.orderService.model.Order;
import com.targetready.orderService.model.Payment;
import com.targetready.paymentService.helper.transactionIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.targetready.paymentService.producer.PaymentProducer;

@Service
public class OrderConsumer {
    @Autowired
    private PaymentProducer paymentProducer;

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void consume(Order order) {
        Payment payment = new Payment();
        payment.setOrderId(order.getOrderId());
        payment.setAmount(order.getAmount());
        payment.setBank(order.getBank());
        payment.setTransactionId(transactionIdGenerator.generateTransactionId());

        paymentProducer.sendPayment(payment);
    }


}
