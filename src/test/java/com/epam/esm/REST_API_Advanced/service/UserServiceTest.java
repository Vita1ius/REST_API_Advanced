package com.epam.esm.REST_API_Advanced.service;

import com.epam.esm.REST_API_Advanced.dto.GiftCertificateDto;
import com.epam.esm.REST_API_Advanced.dto.OrderDto;
import com.epam.esm.REST_API_Advanced.dto.UserDto;
import com.epam.esm.REST_API_Advanced.entity.Order;
import com.epam.esm.REST_API_Advanced.entity.User;
import com.epam.esm.REST_API_Advanced.exception.ApiException;
import com.epam.esm.REST_API_Advanced.repository.OrderRepository;
import com.epam.esm.REST_API_Advanced.repository.UserRepository;
import com.epam.esm.REST_API_Advanced.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PagedResourcesAssembler<User> pagedResourcesAssembler;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    User user;
    UserDto userDto;
    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setName("namexxx");
        userDto= new UserDto();
        userDto.setId(1L);
        userDto.setName("namexxx");
    }
    @Test
    public void shouldReturnUserIfExistsById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        UserDto savedTag = userService.findById(1L);
        Assertions.assertNotNull(savedTag);
        verify(userRepository).findById(1L);
    }
    @Test
    public void shouldAddUser() {
        when(userRepository.save(user)).thenReturn(user);
        Assertions.assertEquals(userDto, userService.createUser(userDto));
        verify(userRepository).save(user);
    }
    @Test
    void shouldCreateOrderMain() {
        GiftCertificateDto giftCertificate = new GiftCertificateDto();
        giftCertificate.setPrice(BigDecimal.valueOf(100));
        OrderDto orderDto = new OrderDto();
        orderDto.setCertificate(giftCertificate);
        orderDto.setUser(userDto);
        userService.createMain(orderDto);
        verify(orderRepository).save(any());
    }
    @Test
    void getOrdersById() {
        List<Object> list = Arrays.asList(new Order[]{new Order()});
        PageImpl<Object> objects = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cost"));

        when(orderRepository.queryAllByUser_Id(1L, pageable)).thenReturn((Page) objects);
        when(orderRepository.queryAllByUser_Id(2L, pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(pagedResourcesAssembler.toModel(any(), (RepresentationModelAssembler) any())).thenReturn(PagedModel
                .empty());

        PagedModel<OrderDto> ordersById = userService.getOrdersById(1L, 0, 10);

        verify(orderRepository).queryAllByUser_Id(any(), any());

        Assertions.assertNotNull(ordersById);
        assertThatThrownBy(() -> userService.getOrdersById(2L, 0, 10)).isInstanceOf(ApiException.class);
    }
    @Test
    void shouldDeleteById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.deleteById(1L);
        userService.deleteById(2L);
        verify(userRepository, times(2)).deleteById(anyLong());
    }
}
