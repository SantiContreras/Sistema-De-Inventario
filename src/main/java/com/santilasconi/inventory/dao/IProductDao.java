package com.santilasconi.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.santilasconi.inventory.models.Product;

public interface IProductDao extends CrudRepository<Product, Long> {

}
