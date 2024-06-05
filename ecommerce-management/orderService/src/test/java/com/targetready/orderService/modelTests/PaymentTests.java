package com.targetready.orderService.modelTests;
import com.targetready.orderService.model.Payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

 class PaymentTest {

    private Payment payment;

    @BeforeEach
    public void setUp() {
        payment = new Payment();
    }

    @Test
    public void testSetAndGetOrderId() {
        String orderId = "12345";
        payment.setOrderId(orderId);
        assertEquals(orderId, payment.getOrderId());
    }

    @Test
    public void testSetAndGetAmount() {
        double amount = 250.75;
        payment.setAmount(amount);
        assertEquals(amount, payment.getAmount());
    }

    @Test
    public void testSetAndGetTransactionId() {
        String transactionId = "trans123";
        payment.setTransactionId(transactionId);
        assertEquals(transactionId, payment.getTransactionId());
    }

    @Test
    public void testSetAndGetBank() {
        String bank = "Bank of Test";
        payment.setBank(bank);
        assertEquals(bank, payment.getBank());
    }
}

