package com.akshay.project.project.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.project.project.Repository.CategoriesRepository;
import com.akshay.project.project.Repository.ProductRepository;
import com.akshay.project.project.exception.RecordNotFoundException;
import com.akshay.project.project.model.Categories;
import com.akshay.project.project.model.Products;

@Service
public class ProductService implements CRUDOperation<Products> {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Autowired
	private Logger log;

	@Override
	public Products addModel(Products model) {

		log.info("Request Recieved to add new product :{}", model.getProductName());

		// validating the catgory Id
		Long categoryId = model.getCategoryId().getCategoryId();
		log.info("validating category with Id :{}", categoryId);
		Categories category = categoriesRepository.findById(categoryId).orElseThrow(() -> {
			log.warn("Category not found for ID: {}", categoryId);
			return new RecordNotFoundException("Category not present for ID: " + categoryId);
		});
		try {
			Products savedProduct = productRepository.save(model);
			log.info("Product saved successfully with ID: {}", savedProduct.getProductId());

			return savedProduct;
		} catch (Exception ex) {
			log.error("Failed to save product. Error: {}", ex.getMessage(), ex);

			throw ex;
		}
	}

	@Override
	public Products updateModel(Products model, Long ID) {
		log.info("Request received for updating extisting product");

		log.info("finf the product with productId :{}", ID);
		Products product = productRepository.findById(ID).orElseThrow(() -> {
			log.warn("Product not found for Id :{}", ID);
			return new RecordNotFoundException("Product not present with Id :" + ID);
		});
		// validating the category
		Long categoryId = model.getCategoryId().getCategoryId();
		log.info("validating category Id :{}", categoryId);
		Categories category = categoriesRepository.findById(categoryId).orElseThrow(() -> {
			log.warn("category not found with Id :{}", categoryId);
			return new RecordNotFoundException("Category not found with Id :" + categoryId);
		});
		product.setProductName(model.getProductName());
		product.setProductDescription(model.getProductDescription());
		product.setPrice(model.getPrice());
		product.setProductImage(model.getProductImage());
		product.setActive(model.isActive());
		product.setCategoryId(category);
		try {
			Products updateProduct = productRepository.save(product);
			log.info("product update successfully");
			return updateProduct;
		} catch (Exception ex) {
			log.error("failed to save the product Error :{}", ex.getMessage(), ex);
			throw ex;
		}
	}

	@Override
	public Products getModel(Long Id) {
		log.info("Request recived to fetch product of Id :{}", Id);
		Products product = productRepository.findById(Id).orElseThrow(() -> {
			log.warn("Product not found with Id :{}", Id);
			return new RecordNotFoundException("Product not found with Id :" + Id);
		});
		return product;
	}

	@Override
	public Products deleteModel(Long Id) {
		log.info("Request received delete product ID :{}", Id);
		Products product = productRepository.findById(Id).orElseThrow(() -> {
			log.warn("Product not found with product Id :", Id);
			return new RecordNotFoundException("Product not found with Id :" + Id);
		});
		product.setActive(false);
		Products newProduct = productRepository.save(product);
		log.info("Product deletd successfully with productId :{}", product.getProductId());
		return newProduct;
	}

}
