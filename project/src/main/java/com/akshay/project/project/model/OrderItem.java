package com.akshay.project.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends EntityBase {

	@EmbeddedId
	private OrderItemId id;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private double unitPrice;

	@JsonBackReference("order-orderitem")
	@MapsId("orderId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;

	@JsonBackReference("product-orderitem")
	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Products product;

}
