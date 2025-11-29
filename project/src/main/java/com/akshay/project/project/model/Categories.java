package com.akshay.project.project.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Categories extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long categoryId;

	private String categoryName;

	private boolean active;

	@JsonManagedReference("category-products")
	@OneToMany(mappedBy = "categoryId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Products> products = new ArrayList<>();

}
