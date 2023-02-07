package com.epam.esm.REST_API_Advanced.service.impl;

import com.epam.esm.REST_API_Advanced.dto.OrderDto;
import com.epam.esm.REST_API_Advanced.entity.Order;
import com.epam.esm.REST_API_Advanced.entity.User;
import com.epam.esm.REST_API_Advanced.dto.UserDto;
import com.epam.esm.REST_API_Advanced.exception.ApiException;
import com.epam.esm.REST_API_Advanced.exception.Exceptions;
import com.epam.esm.REST_API_Advanced.hateoas.assembler.OrderAssembler;
import com.epam.esm.REST_API_Advanced.mapper.OrderMapper;
import com.epam.esm.REST_API_Advanced.mapper.UserMapper;
import com.epam.esm.REST_API_Advanced.repository.OrderRepository;
import com.epam.esm.REST_API_Advanced.repository.UserRepository;
import com.epam.esm.REST_API_Advanced.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderAssembler orderAssembler;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;
    @Override
    public PagedModel<OrderDto> getOrdersById(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("cost"));

        Page<Order> byUser_id = orderRepository.queryAllByUser_Id(id, pageable);
        if (byUser_id.getTotalElements() == 0) {
            throw new ApiException(Exceptions.ORDER_NOT_FOUND);
        }
        if (page > byUser_id.getTotalPages()) {
            throw new ApiException(Exceptions.PAGE_DOESNT_EXIST);
        }
        return pagedResourcesAssembler.toModel(byUser_id, orderAssembler);
    }
    @Override
    public UserDto createUser(UserDto user) {
        if (userRepository.existsByName(user.getName())){
            throw new ApiException(Exceptions.USER_ALREADY_EXIST);
        }
        User newUser = UserMapper.INSTANCE.toEntity(user);
        return UserMapper.INSTANCE.toDto(userRepository.save(newUser));
    }
    @Override
    public UserDto findById(Long id) {
        User user= userRepository.findById(id).orElseThrow(() -> new ApiException(Exceptions.USER_NOT_FOUND));
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        userDto.setOrders(OrderMapper.INSTANCE.mapToDto(orderRepository.findAllByUser_Id(id)));
        return userDto;
    }
    @Override
    public OrderDto createMain(OrderDto orderDto) {
        orderDto.setOrderCost(orderDto.getCertificate().getPrice());
        Order order = OrderMapper.INSTANCE.toEntity(orderDto);
        orderRepository.save(order);
        return OrderMapper.INSTANCE.toDto(order);
    }

    @Override
    public void deleteById(Long id) {
        if (userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }else throw new ApiException(Exceptions.USER_NOT_FOUND);}
}