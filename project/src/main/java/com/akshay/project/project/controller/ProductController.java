package com.akshay.project.project.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.project.project.model.Products;
import com.akshay.project.project.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private Logger log;

	@PostMapping
	public ResponseEntity<Products> addProduct(@RequestBody Products product) {
		try {
			log.info("Request received to controller to add new product :{}", product.getProductName());
			return new ResponseEntity<>(productService.addModel(product), HttpStatus.CREATED);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Products> updateProduct(@RequestBody Products product, @PathVariable("id") Long id) {
		try {
			log.info("Request received to controller update product");
			return new ResponseEntity<>(productService.updateModel(product, id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Products> getProduct(@PathVariable("id") Long id) {
		try {
			log.info("Request received to controller fetch the product by ID :{}", id);
			return new ResponseEntity<>(productService.getModel(id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Products> deleteProduct(@PathVariable("id") Long id) {
		try {
			log.info("Request received to controller delete the product Id :{}", id);
			return new ResponseEntity<>(productService.deleteModel(id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
