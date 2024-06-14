package com.targetready.orderService.service;

import com.targetready.orderService.dto.OrderDTO;
import com.targetready.orderService.mapper.OrderMapper;
import com.targetready.orderService.model.Order;
import com.targetready.orderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;


    public OrderDTO createOrder(OrderDTO orderDTO){
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);

    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDto(order);
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderId(orderDTO.getOrderId());
        order.setBank(orderDTO.getBank());
        order.setAmount(orderDTO.getAmount());
        order.setStock(orderDTO.getStock());
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
