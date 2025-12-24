package com.akshay.project.project.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Products extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long productId;

	private String productName;

	private String productDescription;

	private String productImage;

	private boolean active;

	private Double price;

	@JsonBackReference("category-products")
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categories categoryId;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> cartProducts = new ArrayList<>();

	@OneToMany(mappedBy = "product", orphanRemoval = true)
	private List<OrderItem> orderItem = new ArrayList<>();
}
