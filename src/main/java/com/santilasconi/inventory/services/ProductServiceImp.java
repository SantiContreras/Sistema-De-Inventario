package com.santilasconi.inventory.services;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santilasconi.inventory.dao.ICategoryDao;
import com.santilasconi.inventory.dao.IProductDao;
import com.santilasconi.inventory.models.Product;
import com.santilasconi.inventory.models.Category;
import com.santilasconi.inventory.response.ProductResponseRest;
import com.santilasconi.inventory.util.Util;

@Service
public class ProductServiceImp implements IProductService {

	private ICategoryDao categorydao;
	private IProductDao productodao;

	public ProductServiceImp(ICategoryDao categorydao , IProductDao productodao) {
		super();
		this.categorydao = categorydao;
		this.productodao = productodao;
	}

	

	@Override
	//@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {

		ProductResponseRest response = new ProductResponseRest(); // va a ser una lista de producto
		List<Product> list = new ArrayList<>();
		// buscamos la categoria para el producto
		try {
			Optional<Category> category = categorydao.findById(categoryId);
			if (category.isPresent()) {
				product.setCategory(category.get());
			} else {
				response.setMetadata("respuesta NO ok", "-1", "Categoria NO encontrada");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

			// guardamos la categoria encontrada o no
			Product ProductSave = productodao.save(product);

			if (ProductSave != null) {
				list.add(ProductSave);
				response.getPro().setProducts(list);// seteo la lista de producto
				response.setMetadata("respuesta ok", "00", "Categoria  encontrada");
			} else {
				response.setMetadata("respuesta NO ok", "-1", "Producto NO guardado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta NO ok", "-1", "error al guardar el producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}



	@Override
	public ResponseEntity<ProductResponseRest> serachById(Long id) { 
		
		ProductResponseRest response = new ProductResponseRest(); // va a ser una lista de producto
		List<Product> list = new ArrayList<>();
		// buscamos la categoria para el producto
		try {
			Optional<Product> product= productodao.findById(id);
			if (product.isPresent()) {
				byte[] imagedescompressed = Util.decompressZLib(product.get().getPicture());
				product.get().setPicture(imagedescompressed);
				list.add(product.get()); // obtengo el producto en su totalidad y lo agrego a la lista
				response.getPro().setProducts(list);//seteo el producto con las lista.
				response.setMetadata("respuesta ok", "00", "producto  encontrado");
			} else {
				response.setMetadata("respuesta NO ok", "-1", "producto NO encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta NO ok", "-1", "error al guardar el producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> searchByName(String name){
		ProductResponseRest response = new ProductResponseRest(); // va a ser una lista de producto
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();
		// buscamos la categoria para el producto
		try {
			listAux = productodao.findByNameContainingIgnoreCase(name);
			
			if (listAux.size() > 0) {
				listAux.stream().forEach((p)->{
					byte[] imagedescompressed = Util.decompressZLib(p.getPicture());
					p.setPicture(imagedescompressed);
					list.add(p); // obtengo el producto en su totalidad y lo agrego a la lista
					response.getPro().setProducts(list);//seteo el producto con las lista.
					response.setMetadata("respuesta ok", "00", "productos  encontrados");
				});
			} else {
				response.setMetadata("respuesta NO ok", "-1", "productos NO encontrados");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}

			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta NO ok", "-1", "error al buscar un producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}
}
