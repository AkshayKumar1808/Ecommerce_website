package com.akshay.project.project.controller;

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
import com.akshay.project.project.service.CategoryServiceImpl;

@RestController
@RequestMapping("categoryservice/category")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl categoryServiceImpl;

	@PostMapping
	public ResponseEntity<Categories> addCategory(@RequestBody Categories category) {
		try {
			return new ResponseEntity<>(categoryServiceImpl.addModel(category), HttpStatus.CREATED);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("{Id}")
	public ResponseEntity<Categories> updateCategory(@RequestBody Categories category, @PathVariable("Id") Long Id) {
		try {
			return new ResponseEntity<>(categoryServiceImpl.updateModel(category, Id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("{Id}")
	public ResponseEntity<Categories> getCategory(@PathVariable("Id") Long Id) {
		try {
			return new ResponseEntity<>(categoryServiceImpl.getModel(Id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{Id}")
	public ResponseEntity<Categories> deleteCategory(@PathVariable("Id") Long Id) {
		try {
			return new ResponseEntity<>(categoryServiceImpl.deleteModel(Id), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
