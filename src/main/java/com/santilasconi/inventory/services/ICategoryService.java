package com.santilasconi.inventory.services;

import org.springframework.http.ResponseEntity;

import com.santilasconi.inventory.models.Category;
import com.santilasconi.inventory.response.CategoryResponseRest;

public interface ICategoryService {
  public ResponseEntity<CategoryResponseRest> search();
  public ResponseEntity<CategoryResponseRest> searchById(Long id);
  public ResponseEntity<CategoryResponseRest> save(Category category);
  public ResponseEntity<CategoryResponseRest> update(Category category, Long id);
}
