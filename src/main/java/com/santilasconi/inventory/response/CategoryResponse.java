package com.santilasconi.inventory.response;

import java.util.List;

import com.santilasconi.inventory.models.Category;

import lombok.Data;

@Data
public class CategoryResponse { //nos retorna una lista

	private List<Category> category;
}
