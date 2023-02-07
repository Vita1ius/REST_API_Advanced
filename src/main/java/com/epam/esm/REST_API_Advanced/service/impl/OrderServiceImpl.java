package com.epam.esm.REST_API_Advanced.service.impl;

import com.epam.esm.REST_API_Advanced.dto.OrderDto;
import com.epam.esm.REST_API_Advanced.entity.Order;
import com.epam.esm.REST_API_Advanced.exception.ApiException;
import com.epam.esm.REST_API_Advanced.exception.Exceptions;
import com.epam.esm.REST_API_Advanced.hateoas.assembler.OrderAssembler;
import com.epam.esm.REST_API_Advanced.mapper.OrderMapper;
import com.epam.esm.REST_API_Advanced.repository.OrderRepository;
import com.epam.esm.REST_API_Advanced.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;
    private final OrderAssembler orderAssembler;
    @Override
    public PagedModel<OrderDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderCost"));
        Page<Order> orders = orderRepository.findAll(pageable);

        if (page > orders.getTotalPages() - 1) {
            throw new ApiException(Exceptions.PAGE_DOESNT_EXIST);
        }

        return  pagedResourcesAssembler.toModel(orders, orderAssembler);
    }


    @Override
    public OrderDto getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ApiException(Exceptions.ORDER_NOT_FOUND));
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public void deleteById(Long id) {
        if (orderRepository.findById(id).isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new ApiException(Exceptions.ORDER_NOT_FOUND);
        }
    }

    @Override
    public Set<OrderDto> getOrdersByUserId(Long UserId) {
        return OrderMapper.INSTANCE.mapToDto(orderRepository.findAllByUser_Id(UserId));
    }

    @Override
    public OrderDto getOneUserOrder(Long id, Long userId) {
        Order order = orderRepository.getOneUserOrder(id,userId).orElseThrow(() ->
                new ApiException(Exceptions.ORDER_NOT_FOUND));
        return OrderMapper.INSTANCE.toDto(order);
    }
//    @Override
//    @Transactional
//    public OrderDto save(Long idUser,Long idCertificate) {
//        Order newOrder = new Order();
//        User user = UserMapper.INSTANCE.toEntity(userService.findById(idUser));
//        newOrder.setUser(user);
//        newOrder.setCertificate(GiftCertificateMapper.INSTANCE.
//        toEntity(giftCertificateService.getById(idCertificate)));
//        newOrder.setOrderCost(newOrder.getCertificate().getPrice());
//        Order order = orderRepository.save(newOrder);
//        return OrderMapper.INSTANCE.toDto(order);
//
//    }
}