package com.epam.esm.REST_API_Advanced.mapper;

import com.epam.esm.REST_API_Advanced.dto.OrderDto;
import com.epam.esm.REST_API_Advanced.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    Order toEntity(OrderDto orderDto);

    OrderDto toDto(Order order);
    Set<OrderDto> mapToDto(Set<Order> list);
}

