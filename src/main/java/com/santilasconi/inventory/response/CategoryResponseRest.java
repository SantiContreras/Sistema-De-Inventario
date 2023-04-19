package com.santilasconi.inventory.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseRest extends ResponseRest {
	private CategoryResponse categoryResponse = new CategoryResponse(); // instancia una nueva lista de categorias
}
