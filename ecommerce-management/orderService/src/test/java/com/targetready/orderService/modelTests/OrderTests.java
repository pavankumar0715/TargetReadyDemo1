package com.targetready.orderService.modelTests;

import com.targetready.orderService.model.Order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    @Test
    void testGetAndSetOrderId() {
        Order order = new Order();
        order.setOrderId("12345");
        assertEquals("12345", order.getOrderId());
    }

    @Test
    void testGetAndSetAmount() {
        Order order = new Order();
        order.setAmount(150.0);
        assertEquals(150.0, order.getAmount());
    }

    @Test
    void testGetAndSetBank() {
        Order order = new Order();
        order.setBank("Test Bank");
        assertEquals("Test Bank", order.getBank());
    }

    @Test
    void testGetAndSetStock() {
        Order order = new Order();
        order.setStock(50);
        assertEquals(50, order.getStock());
    }


}

