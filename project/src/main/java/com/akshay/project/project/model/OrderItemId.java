package com.akshay.project.project.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemId implements Serializable {

	/**
	 * composite key for the order
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "product_id")
	private Long productId;
}
