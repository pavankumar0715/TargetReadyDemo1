package com.targetready.orderService.controllerTests;
import com.targetready.orderService.controller.OrderController;

import com.targetready.orderService.dto.OrderDTO;
import com.targetready.orderService.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testCreateOrder() {
        OrderDTO orderDTO = new OrderDTO();
        OrderDTO createdOrderDTO = new OrderDTO();

        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(createdOrderDTO);

        ResponseEntity<OrderDTO> response = orderController.createOrder(orderDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdOrderDTO, response.getBody());

        verify(orderService, times(1)).createOrder(orderDTO);
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        OrderDTO orderDTO = new OrderDTO();

        when(orderService.getOrderById(orderId)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDTO, response.getBody());

        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    void testGetAllOrders() {
        List<OrderDTO> orders = Arrays.asList(new OrderDTO(), new OrderDTO());

        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void testUpdateOrder() {
        Long orderId = 1L;
        OrderDTO orderDTO = new OrderDTO();
        OrderDTO updatedOrderDTO = new OrderDTO();

        when(orderService.updateOrder(anyLong(), any(OrderDTO.class))).thenReturn(updatedOrderDTO);

        ResponseEntity<OrderDTO> response = orderController.updateOrder(orderId, orderDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrderDTO, response.getBody());

        verify(orderService, times(1)).updateOrder(orderId, orderDTO);
    }

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;

        doNothing().when(orderService).deleteOrder(orderId);

        ResponseEntity<Void> response = orderController.deleteOrder(orderId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(orderService, times(1)).deleteOrder(orderId);
    }
}
