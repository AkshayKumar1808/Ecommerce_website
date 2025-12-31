package com.akshay.project.project.model;

import java.util.ArrayList;
import java.util.List;

import com.akshay.project.project.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;

	private Double totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status = OrderStatus.PENDING;

	@JsonBackReference("user-orders")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	private Address address;

	@JsonManagedReference("order-orderitem")
	@OneToMany(mappedBy = "order", orphanRemoval = true)
	private List<OrderItem> orderItem = new ArrayList<>();
}
