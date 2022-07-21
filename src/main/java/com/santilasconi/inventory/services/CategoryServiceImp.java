package com.santilasconi.inventory.services;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.santilasconi.inventory.dao.ICategoryDao;
import com.santilasconi.inventory.models.Category;
import com.santilasconi.inventory.response.CategoryResponseRest;

@Service
public class CategoryServiceImp implements ICategoryService{
	@Autowired
	private ICategoryDao categorydao;

	@Override
	@Transactional()
	public ResponseEntity<CategoryResponseRest> search() {
	     
		CategoryResponseRest response = new CategoryResponseRest();
		try {
			List<Category> category = (List<Category>) categorydao.findAll();
			response.getCategoryresponse().setCategorys(category);
			response.setMetadata("Respuesta ok", "001", "Respuesta con exito");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ResponseEntity<CategoryResponseRest>(response , HttpStatus.OK);
	}

}
