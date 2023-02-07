package com.epam.esm.REST_API_Advanced.repository;

import com.epam.esm.REST_API_Advanced.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Set<Order> findAllByUser_Id(Long id);

    Page<Order> queryAllByUser_Id(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM orders where id= ? and user_id = ?;",
            nativeQuery = true)
    Optional<Order> getOneUserOrder(Long id, Long userId);
}

