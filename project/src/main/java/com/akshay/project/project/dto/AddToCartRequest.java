package com.akshay.project.project.dto;

import lombok.Data;

@Data
public class AddToCartRequest {

	private Long productId;

	private Integer quantity;
}
