package com.targetready.orderService.mapper;


import com.targetready.orderService.dto.OrderDTO;
import com.targetready.orderService.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDto(Order order);
    Order toEntity(OrderDTO orderDTO);
}
