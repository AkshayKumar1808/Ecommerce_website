package com.akshay.project.project.service;

import com.akshay.project.project.dto.AddToCartRequest;
import com.akshay.project.project.dto.CartDTO;

public interface CartService {

	public CartDTO addProductToCart(Long userId, AddToCartRequest request);

	public CartDTO getProductOfCart(Long cartId);

	public CartDTO updateCartProduct(Long cartId, Long productId, int quantity);

	public CartDTO deleteCart(Long cartId);
}
