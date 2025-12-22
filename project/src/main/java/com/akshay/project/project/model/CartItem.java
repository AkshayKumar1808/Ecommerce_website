package com.akshay.project.project.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
public class CartItem {

	@EmbeddedId
	private CartItemId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("cartId")
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Products product;

	private Integer quantity;

	public CartItem(Cart cart, Products product, Integer qty) {
		this.cart = cart;
		this.product = product;
		this.id = new CartItemId(cart.getCartId(), product.getProductId());
		this.quantity = qty;
	}
}
