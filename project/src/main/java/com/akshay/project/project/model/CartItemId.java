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
public class CartItemId implements Serializable {

	/**
	 * composite key for cartItem
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "cart_id")
	private Long cartId;

	@Column(name = "product_id")
	private Long productId;
}
