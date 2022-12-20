package com.santilasconi.inventory.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.santilasconi.inventory.models.Product;

public interface IProductDao extends CrudRepository<Product, Long> {
	
	@Query("select p from Product p where p.name like %?1%")
	public List<Product> findByNameLike(String name);
	
	public List<Product> findByNameContainingIgnoreCase(String name);

}
