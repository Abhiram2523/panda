package com.panda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.panda.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


//    CartItem findByFoodIsContaining

}
