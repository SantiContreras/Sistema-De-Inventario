package com.santilasconi.inventory.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santilasconi.inventory.models.Category;
import com.santilasconi.inventory.response.CategoryResponseRest;
import com.santilasconi.inventory.services.ICategoryService;

@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
	@Autowired
	private ICategoryService service;
     /** 
      * get all the categories
      * **/
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> searchCategory() {

		ResponseEntity<CategoryResponseRest> response = service.search();
		return response;
	}
    
	
	 /** 
     * get all the categories by id
     * **/
	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoryById(@PathVariable Long id) {

		ResponseEntity<CategoryResponseRest> response = service.searchById(id);
		return response;
	}
	 /** 
     * save categories parameter Category
     * **/
	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category) {

		ResponseEntity<CategoryResponseRest> response = service.save(category);
		return response;
	}

}
