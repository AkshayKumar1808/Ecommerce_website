package com.akshay.project.project.service;

import com.akshay.project.project.dto.OrderDTO;

public interface OrderService {

	OrderDTO addProductToOrder(Long userId, Long orderId, Long productId, int quantity);
}
