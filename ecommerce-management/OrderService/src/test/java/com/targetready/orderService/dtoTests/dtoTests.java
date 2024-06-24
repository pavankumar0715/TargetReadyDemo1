package com.targetready.orderService.dtoTests;
import com.targetready.orderService.dto.OrderDTO;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OrderDTOTest {

    @Test
    public void testGetterAndSetters() {
        // Create an instance of OrderDTO
        OrderDTO orderDTO = new OrderDTO();

        // Set values using setter methods
        orderDTO.setOrderId("ORD123");
        orderDTO.setAmount(100.50);
        orderDTO.setBank("Bank A");
        orderDTO.setStock(10);

        // Verify values using getter methods
        assertThat(orderDTO.getOrderId()).isEqualTo("ORD123");
        assertThat(orderDTO.getAmount()).isEqualTo(100.50);
        assertThat(orderDTO.getBank()).isEqualTo("Bank A");
        assertThat(orderDTO.getStock()).isEqualTo(10);
    }

    // Add more tests as needed for additional functionality or edge cases
}

