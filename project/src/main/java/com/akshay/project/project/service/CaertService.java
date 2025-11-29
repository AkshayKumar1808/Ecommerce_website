package com.akshay.project.project.service;

import com.akshay.project.project.dto.AddToCartRequest;
import com.akshay.project.project.dto.CartDTO;

public interface CaertService {

	public CartDTO addProductToCart(Long userId, AddToCartRequest request);
}
