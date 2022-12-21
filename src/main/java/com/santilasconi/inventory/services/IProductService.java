package com.santilasconi.inventory.services;

import org.springframework.http.ResponseEntity;

import com.santilasconi.inventory.models.Product;
import com.santilasconi.inventory.response.ProductResponseRest;

public interface IProductService {

	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId);
	
	public ResponseEntity<ProductResponseRest> serachById(Long id);

	public ResponseEntity<ProductResponseRest> searchByName(String name);
	
	public ResponseEntity<ProductResponseRest> deleteById(Long id);
	
	

}
