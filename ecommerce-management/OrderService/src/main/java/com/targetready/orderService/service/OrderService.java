package com.targetready.orderService.service;

import brave.Span;
import brave.Tracing;
import brave.kafka.clients.KafkaTracing;
import com.targetready.orderService.dto.OrderDTO;
import com.targetready.orderService.mapper.OrderMapper;
import com.targetready.orderService.model.Order;
import com.targetready.orderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private KafkaTracing kafkaTracing;

    @Autowired
    private Tracing tracing;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public OrderService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate=kafkaTemplate;
    }


    public OrderDTO createOrder(OrderDTO orderDTO){
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        sendOrder(order);
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





    public void sendOrder(Order order) {
        Span span = tracing.tracer().nextSpan().name("order").start();

        try{
            validateOrder(order);
            span.tag("success", "Valid order");
            kafkaTemplate.send("orders", order);
        }
        catch (IllegalArgumentException e) {
            span.tag("error",e.getMessage());
        }
        catch(Exception e) {
            span.tag("error", "An error occurred while processing the order: " + e.getMessage());

        }
        finally {
            span.finish();
        }
    }

    private void validateOrder(Order order) {
        if(order==null || order.getOrderId()==null) {
            throw new IllegalArgumentException("Invalid order data");
        }
        if(order.getAmount()<0) {
            throw new IllegalArgumentException("Price should be greater than 0");
        }
        if(order.getStock()<=0) {
            throw new IllegalArgumentException("Product is out of Stock");
        }
    }
}
