package com.akshay.project.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akshay.project.project.model.CartItem;
import com.akshay.project.project.model.CartItemId;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

}
