package com.panda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panda.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
