package com.epam.esm.REST_API_Advanced.hateoas.assembler;

import com.epam.esm.REST_API_Advanced.controller.OrderController;
import com.epam.esm.REST_API_Advanced.dto.OrderDto;
import com.epam.esm.REST_API_Advanced.entity.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderAssembler extends RepresentationModelAssemblerSupport<Order, OrderDto> {

    public OrderAssembler() {
        super(OrderController.class, OrderDto.class);
    }

    @Override
    public OrderDto toModel(Order entity) {
        OrderDto model = new OrderDto();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
