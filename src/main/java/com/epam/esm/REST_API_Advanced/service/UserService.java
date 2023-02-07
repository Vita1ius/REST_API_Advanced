package com.epam.esm.REST_API_Advanced.service;

import com.epam.esm.REST_API_Advanced.dto.OrderDto;
import com.epam.esm.REST_API_Advanced.dto.UserDto;
import org.springframework.hateoas.PagedModel;

public interface UserService {
    /**
     * retrieves the list of orders of specified User
     * @param id id of the User
     * @return list of orders of specified User
     */
    PagedModel<OrderDto> getOrdersById (Long id, int page, int size);

    /**
     * <p>
     * Creates a new User
     * </p>
     */
    UserDto createUser(UserDto user);
    /**
     * <p>
     * Deletes a User by id
     * </p>
     *  @param id the User that needs to be deleted
     */
    void deleteById(Long id);
    UserDto findById(Long id);

    OrderDto createMain(OrderDto orderDto);

}
