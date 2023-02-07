package com.epam.esm.REST_API_Advanced.controller;

import com.epam.esm.REST_API_Advanced.dto.OrderDto;
import com.epam.esm.REST_API_Advanced.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "order")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }
    @GetMapping(params = {"page", "size"})
    public PagedModel<OrderDto> getAll(@RequestParam @Valid int page,
                                       @RequestParam @Valid int size){
        return service.getAll(page, size);
    }


    @GetMapping("/{id}")
    OrderDto getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable @Valid Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Order by id " + id + " was deleted!");
    }
    @GetMapping("/user/{id}")
    Set<OrderDto> getOrdersByUserId(@PathVariable Long id){
        return service.getOrdersByUserId(id);
    }
    @GetMapping(params = {"id", "userId"})
    public OrderDto getOneUserOrder(@RequestParam @Valid Long id,
                                       @RequestParam @Valid Long userId){
        return service.getOneUserOrder(id,userId);
    }
}
