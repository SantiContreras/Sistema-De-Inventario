package com.santilasconi.inventory.response;

import java.util.List;

import com.santilasconi.inventory.models.Product;

import lombok.Data;

@Data
public class ProductResponse {
    
	List<Product> products;
}
