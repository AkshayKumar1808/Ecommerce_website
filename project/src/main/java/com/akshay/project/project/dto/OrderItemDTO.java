package com.akshay.project.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
	
	private int quantity;
	
	private double unitPrice;

	private Long orderId;

	private Long productId;
}
