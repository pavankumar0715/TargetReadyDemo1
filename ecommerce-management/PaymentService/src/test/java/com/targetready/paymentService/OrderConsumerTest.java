package com.targetready.paymentService;

import com.targetready.orderService.model.Order;
import com.targetready.orderService.model.Payment;
import com.targetready.paymentService.consumer.OrderConsumer;
import com.targetready.paymentService.producer.PaymentProducer;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OrderConsumerTest {


    private Payment payment;
    private Order order;

    @Captor
    ArgumentCaptor<Payment> paymentArgumentCaptor;

    private KafkaTemplate<String, Object> kafkaTemplate;


    @Mock
    PaymentProducer paymentProducer;

    @InjectMocks
    private OrderConsumer orderConsumer;



    @BeforeEach
    public void setUp() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(configProps);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);

        order = new Order();

        order.setOrderId("123");
        order.setAmount(100.0);
        order.setStock(50);
        order.setBank("SBI");

        kafkaTemplate.send("orders", order);
    }

    @Test
    public void testConsume() throws InterruptedException {

        orderConsumer.consume(order);

        verify(paymentProducer).sendPayment(paymentArgumentCaptor.capture());
        payment = paymentArgumentCaptor.getValue();

        assertThat(payment).isNotNull();
        assertThat(payment.getOrderId()).isEqualTo("123");
        assertThat(payment.getAmount()).isEqualTo(100.0);
        assertThat(order.getBank()).isEqualTo("SBI");


    }
}
