package com.santilasconi.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
			response.getCategoryResponse().setCategory(category);
			response.setMetadata("Respuesta ok", "001", "Respuesta con exito");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return new ResponseEntity<CategoryResponseRest>(response , HttpStatus.OK);
	}

	@Override
	@Transactional()
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		ArrayList<Category> list = new ArrayList<>();
		try {
			Optional<Category> category = categorydao.findById(id);
			if (category.isPresent()) {
				list.add(category.get());
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("", "-1", "Respuesta ok");
			} else {
				response.setMetadata("", "-1", "Eror al consultar la categoria");
				return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMetadata("", "-1", "Eror al consultar la categoria");
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response , HttpStatus.OK);
	}

	@Override
	@Transactional()
	public ResponseEntity<CategoryResponseRest> save(Category category) {
		CategoryResponseRest response = new CategoryResponseRest();
		ArrayList<Category> list = new ArrayList<>();
		try {
			Category nuevacategoria = categorydao.save(category);
			if (nuevacategoria != null) {
			   list.add(nuevacategoria);
			   response.getCategoryResponse().setCategory(list);
			}
		} catch (Exception e) {
			response.setMetadata("", "-1", "Eror al guardar la categoria");
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response , HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		ArrayList<Category> list = new ArrayList<>();
		try {
			Optional<Category> categorybuscarda = categorydao.findById(id);
			if(categorybuscarda.isPresent()) {
				categorybuscarda.get().setName(category.getName());
				categorybuscarda.get().setDescripcion(category.getDescripcion());
				
				Category categoryacutalizada = categorydao.save(categorybuscarda.get());
				
				if (categoryacutalizada != null) {
					list.add(categoryacutalizada);
					response.getCategoryResponse().setCategory(list);
					response.setMetadata("Respuesta ok", "-1", "Respuesta ok");
				}else {
					response.setMetadata("", "-1", "Erro al actualizar la categoria");
					return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
					
				}
			}
			
		} catch (Exception e) {
			response.setMetadata("", "-1", "Erro al actualizar la categoria");
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response , HttpStatus.OK);
	}

	@Override
	@Transactional()
	public ResponseEntity<CategoryResponseRest> deleteById(Long id) {
		CategoryResponseRest response = new CategoryResponseRest();
		
		try {
		   categorydao.deleteById(id);
		   response.setMetadata("", "-1", "Se ha eliminado la categoria correctammente");
			
		} catch (Exception e) {
			response.setMetadata("", "-1", "Erro al eliminar  la categoria");
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(response , HttpStatus.OK);
	}
	
	
	
	

}
