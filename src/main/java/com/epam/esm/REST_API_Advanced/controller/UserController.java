package com.epam.esm.REST_API_Advanced.controller;

import com.epam.esm.REST_API_Advanced.dto.OrderDto;
import com.epam.esm.REST_API_Advanced.dto.UserDto;
import com.epam.esm.REST_API_Advanced.service.OrderService;
import com.epam.esm.REST_API_Advanced.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "user")
public class UserController {
    private final UserService service;
    public final OrderService orderService;
    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.service = userService;
        this.orderService = orderService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable @Valid Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("User by id " + id + " was deleted!");
    }

    @PostMapping()
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        return service.createUser(userDto);
    }
    @GetMapping("/{id}")
    UserDto getById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping("/order")
    public OrderDto create(@Validated @RequestBody OrderDto orderDto) {
        return service.createMain(orderDto);
    }
}
