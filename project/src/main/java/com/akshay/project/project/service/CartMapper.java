package com.akshay.project.project.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.akshay.project.project.dto.CartDTO;
import com.akshay.project.project.dto.CartItemDTO;
import com.akshay.project.project.model.Cart;
import com.akshay.project.project.model.CartItem;

@Component
public class CartMapper {

	public CartDTO toDTO(Cart cart) {
		CartDTO dto = new CartDTO();
		dto.setCartId(cart.getCartId());
		dto.setUserId(cart.getUser().getUserId());

		List<CartItemDTO> items = cart.getCartItem().stream().map(this::toItemDTO).toList();

		dto.setItems(items);
		return dto;
	}

	public CartItemDTO toItemDTO(CartItem item) {
		CartItemDTO dto = new CartItemDTO();
		dto.setProductId(item.getProduct().getProductId());
		dto.setProductName(item.getProduct().getProductName());
		dto.setPrice(item.getProduct().getPrice());
		dto.setQuantity(item.getQuantity());
		return dto;
	}
}
