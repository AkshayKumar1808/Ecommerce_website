package com.akshay.project.project.service;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.project.project.Repository.CategoriesRepository;
import com.akshay.project.project.exception.RecordNotFoundException;
import com.akshay.project.project.model.Categories;
import com.akshay.project.project.model.Products;

@Service
public class CategoryServiceImpl implements CRUDOperation<Categories> {

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Autowired
	private Logger log;

	@Override
	public Categories addModel(Categories model) {
		try {
			log.info("adding the category inside the database");
			Categories savedScategory = categoriesRepository.save(model);
			log.info("category saved successfully");
			return savedScategory;
		} catch (Exception ex) {
			log.info("Exception occured adding the Category check logs");
			throw ex;
		}
	}

	@Override
	public Categories updateModel(Categories model, Long ID) {

		log.info("find the category by Id :{}", ID);
		Categories existingCategory = categoriesRepository.findById(ID)
				.orElseThrow(() -> new RecordNotFoundException("Category is not found ID " + ID));
		existingCategory.setCategoryName(model.getCategoryName());
		existingCategory.setActive(model.isActive());

		Categories savedCat = categoriesRepository.save(existingCategory);
		log.info("Categry updated successfully");
		return savedCat;
	}

	@Override
	public Categories getModel(Long Id) {

		Categories category = categoriesRepository.findById(Id)
				.orElseThrow(() -> new RecordNotFoundException("Category is not found Id :" + Id));
		log.info("categoty found send back");
		return category;
	}

	@Override
	public Categories deleteModel(Long Id) {
		Categories category = categoriesRepository.findById(Id)
				.orElseThrow(() -> new RecordNotFoundException("Category is not found Id :" + Id));
		log.info("category delete successfully");
		category.setActive(false);
		Categories deleteCat = categoriesRepository.save(category);
		return deleteCat;
	}

	public List<Categories> getAllActiveCategory() {
		List<Categories> activeCategories = categoriesRepository.findByActiveTrue();
		if (activeCategories.isEmpty()) {
			log.info("categories are empty");
			return Collections.emptyList();
		}
		return activeCategories;
	}

	public List<Products> getAllActiveProduct(Long id) {

		log.info("validating the category Id :{}", id);
		Categories category = categoriesRepository.findById(id).orElseThrow(() -> {
			log.warn("category not found the Id :{}", id);
			return new RecordNotFoundException("Category not found Id :" + id);
		});
		List<Products> products = category.getProducts();
		if (products.isEmpty()) {
			log.warn("products not present against the categoryId :{}", id);
			return Collections.emptyList();
		}
		return products;
	}
}
