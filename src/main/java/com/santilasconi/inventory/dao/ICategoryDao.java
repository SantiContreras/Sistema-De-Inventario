package com.santilasconi.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.santilasconi.inventory.models.Category;

public interface ICategoryDao extends CrudRepository<Category, Long>{

}
