package com.epam.esm.REST_API_Advanced.service;

import com.epam.esm.REST_API_Advanced.dto.OrderDto;
import com.epam.esm.REST_API_Advanced.entity.Order;
import com.epam.esm.REST_API_Advanced.exception.ApiException;
import com.epam.esm.REST_API_Advanced.hateoas.assembler.OrderAssembler;
import com.epam.esm.REST_API_Advanced.repository.OrderRepository;
import com.epam.esm.REST_API_Advanced.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Mock
    private OrderAssembler orderAssembler;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldGetAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("orderCost"));
        PageImpl<Order> page = new PageImpl<>(List.of(new Order()));
        when(orderRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, orderAssembler)).thenReturn(PagedModel.empty());

        PagedModel<OrderDto> orderDtos = orderService.getAll(0, 10);

        verify(orderRepository).findAll(pageable);

        Assertions.assertNotNull(orderDtos);
    }

    @Test
    void shouldGetById() {
        Order orderToPass = new Order();
        orderToPass.setOrderCost(BigDecimal.valueOf(2));
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(orderToPass));

        OrderDto byId = orderService.getById(1L);

        verify(orderRepository).findById(1L);

        Assertions.assertEquals(byId.getOrderCost(),orderToPass.getOrderCost());

        assertThatThrownBy(() -> orderService.getById(2L)).isInstanceOf(ApiException.class);
    }

    @Test
    void shouldDeleteById() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(new Order()));
        orderService.deleteById(1L);
        orderService.deleteById(2L);
        verify(orderRepository, times(2)).deleteById(anyLong());
    }
}