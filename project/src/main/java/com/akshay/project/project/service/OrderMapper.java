package com.akshay.project.project.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.akshay.project.project.dto.OrderDTO;
import com.akshay.project.project.dto.OrderItemDTO;
import com.akshay.project.project.model.Order;
import com.akshay.project.project.model.OrderItem;

@Component
public class OrderMapper {

	public OrderDTO getOrderDetail(Order order) {
		OrderDTO orderDto = new OrderDTO();
		orderDto.setOrderId(order.getOrderId());
		orderDto.setUserId(order.getUser().getUserId());
		orderDto.setTotalAmount(order.getTotalAmount());

		List<OrderItemDTO> list = order.getOrderItem().stream().map(this::getOrderItem).toList();
		orderDto.setOrderItem(list);
		return orderDto;
	}

	public OrderItemDTO getOrderItem(OrderItem item) {
		OrderItemDTO orderItemDto = new OrderItemDTO();
		orderItemDto.setOrderId(item.getOrder().getOrderId());
		orderItemDto.setProductId(item.getProduct().getProductId());
		orderItemDto.setUnitPrice(item.getUnitPrice());
		orderItemDto.setQuantity(item.getQuantity());

		return orderItemDto;
	}
}
