package com.santilasconi.inventory.services;

import org.springframework.http.ResponseEntity;

import com.santilasconi.inventory.response.CategoryResponseRest;

public interface ICategoryService {
  public ResponseEntity<CategoryResponseRest> search();
}
