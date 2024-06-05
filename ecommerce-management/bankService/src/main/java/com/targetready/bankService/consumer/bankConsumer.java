package com.targetready.bankService.consumer;

import com.targetready.orderService.model.Invoice;
import com.targetready.orderService.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.targetready.bankService.producer.bankProducer;
@Service
public class bankConsumer {

    @Autowired
    private bankProducer bankProducer;

    private Invoice invoice;

    @KafkaListener(topics = "payments", groupId = "payment-group")
    public void consume(Payment payment) {
        invoice = new Invoice();
        invoice.setOrderId(payment.getOrderId());
        invoice.setAmount(payment.getAmount());
        invoice.setTransactionId(payment.getTransactionId());
        invoice.setBank(payment.getBank());
        invoice.setStatus(true);

        bankProducer.sendInvoice(invoice);


    }

}
