package com.targetready.orderService.modelTests;
import com.targetready.orderService.model.Invoice;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvoiceTest {

    @Test
    void testGetAndSetOrderId() {
        Invoice invoice = new Invoice();
        invoice.setOrderId("12345");
        assertEquals("12345", invoice.getOrderId());
    }

    @Test
    void testGetAndSetAmount() {
        Invoice invoice = new Invoice();
        invoice.setAmount(100.0);
        assertEquals(100.0, invoice.getAmount());
    }

    @Test
    void testGetAndSetTransactionId() {
        Invoice invoice = new Invoice();
        invoice.setTransactionId("txn-12345");
        assertEquals("txn-12345", invoice.getTransactionId());
    }

    @Test
    void testGetAndSetStatus() {
        Invoice invoice = new Invoice();
        invoice.setStatus(true);
        assertTrue(invoice.isStatus());
    }

    @Test
    void testGetAndSetBank() {
        Invoice invoice = new Invoice();
        invoice.setBank("Test Bank");
        assertEquals("Test Bank", invoice.getBank());
    }
}

