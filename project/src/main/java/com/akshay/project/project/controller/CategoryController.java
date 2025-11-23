package com.akshay.project.project.controller;

import java.util.List;

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

import com.akshay.project.project.model.Categories;
import com.akshay.project.project.model.Products;
import com.akshay.project.project.service.CRUDOperation;
import com.akshay.project.project.service.CategoryServiceImpl;

@RestController
@RequestMapping("categoryservice/category")
public class CategoryController {

	@Autowired
	private CRUDOperation<Categories> categoryService;

	@Autowired
	private Logger log;

	@PostMapping
	public ResponseEntity<?> addCategory(@RequestBody Categories category) {
		try {

			return new ResponseEntity<>(categoryService.addModel(category), HttpStatus.CREATED);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("{Id}")
	public ResponseEntity<?> updateCategory(@RequestBody Categories category, @PathVariable("Id") Long Id) {
		try {
			return new ResponseEntity<>(categoryService.updateModel(category, Id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{Id}")
	public ResponseEntity<Categories> getCategory(@PathVariable("Id") Long Id) {
		try {
			return new ResponseEntity<>(categoryService.getModel(Id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{Id}")
	public ResponseEntity<Categories> deleteCategory(@PathVariable("Id") Long Id) {
		try {
			return new ResponseEntity<>(categoryService.deleteModel(Id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<Categories>> getAllActiveCategory() {
		try {
			// downCast
			if (categoryService instanceof CategoryServiceImpl categoryImpl) {
				return new ResponseEntity<>(categoryImpl.getAllActiveCategory(), HttpStatus.OK);
			} else {
				log.info("in the downCasting issue");
				throw new RuntimeException("downCasting error");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/active/{id}")
	public ResponseEntity<List<Products>> getAllActiveProductsAgainstCategory(@PathVariable("id") Long id) {
		try {
			// downCast
			if (categoryService instanceof CategoryServiceImpl categoryImpl) {
				return new ResponseEntity<>(categoryImpl.getAllActiveProduct(id), HttpStatus.OK);
			} else {
				log.info("in the downCasting issue");
				throw new RuntimeException("downCasting error");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
