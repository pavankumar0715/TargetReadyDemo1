package com.targetready.bankService;



import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;


import com.targetready.bankService.consumer.BankConsumer;
import com.targetready.bankService.producer.BankProducer;
import com.targetready.orderService.model.Invoice;
import com.targetready.orderService.model.Payment;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BankConsumerTest {


    private Invoice invoice;
    private Payment payment;

    @Captor
    ArgumentCaptor<Invoice> invoiceArgumentCaptor;

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private BankProducer bankProducer;

    @InjectMocks
    private BankConsumer bankConsumer;



    @BeforeEach
    public void setUp() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);

        payment = new Payment();

        payment.setOrderId("123");
        payment.setAmount(100.0);
        payment.setTransactionId("UUID");
        payment.setBank("AXIS");

        kafkaTemplate.send("payments", payment);
    }

    @Test
    public void testConsume() throws InterruptedException {

        bankConsumer.consume(payment);


        verify(bankProducer).sendInvoice(invoiceArgumentCaptor.capture());
        invoice = invoiceArgumentCaptor.getValue();

        assertThat(invoice).isNotNull();
        assertThat(invoice.getOrderId()).isEqualTo("123");
        assertThat(invoice.getAmount()).isEqualTo(100.0);
        assertThat(invoice.getTransactionId()).isEqualTo("UUID");
        assertThat(invoice.getBank()).isEqualTo("AXIS");


    }



}
