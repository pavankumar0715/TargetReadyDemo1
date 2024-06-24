package com.targetready.orderService.mapperTests;
import com.targetready.orderService.mapper.OrderMapper;

import com.targetready.orderService.dto.OrderDTO;
import com.targetready.orderService.model.Order;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    public void testToDto() {
        // Create an instance of Order
        Order order = new Order();
        order.setOrderId("1");
        order.setAmount(100.0);
        order.setBank("Bank A");
        order.setStock(10);

        // Map Order to OrderDTO
        OrderDTO orderDTO = orderMapper.toDto(order);

        // Assert that mapping was correct
        assertThat(orderDTO.getOrderId()).isEqualTo("1");
        assertThat(orderDTO.getAmount()).isEqualTo(100.0);
        assertThat(orderDTO.getBank()).isEqualTo("Bank A");
        assertThat(orderDTO.getStock()).isEqualTo(10);
    }

    @Test
    public void testToEntity() {
        // Create an instance of OrderDTO
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1");
        orderDTO.setAmount(100.0);
        orderDTO.setBank("Bank A");
        orderDTO.setStock(10);

        // Map OrderDTO to Order
        Order order = orderMapper.toEntity(orderDTO);

        // Assert that mapping was correct
        assertThat(order.getOrderId()).isEqualTo("1");
        assertThat(order.getAmount()).isEqualTo(100.0);
        assertThat(order.getBank()).isEqualTo("Bank A");
        assertThat(order.getStock()).isEqualTo(10);
    }

    // Add more tests as needed for additional mapping scenarios
}

