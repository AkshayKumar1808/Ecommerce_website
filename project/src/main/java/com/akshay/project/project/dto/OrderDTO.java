package com.akshay.project.project.dto;

import java.util.ArrayList;
import java.util.List;

import com.akshay.project.project.model.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private Long orderId;

	private Long userId;

	private Double totalAmount;

	private List<OrderItemDTO> orderItem = new ArrayList<>();
}
