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

	public ProductServiceImp(ICategoryDao categorydao, IProductDao productodao) {
		super();
		this.categorydao = categorydao;
		this.productodao = productodao;
	}

	@Override
	// @Transactional(readOnly = true)
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
				response.getProduct().setProducts(list);// seteo la lista de producto
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
			Optional<Product> product = productodao.findById(id);
			if (product.isPresent()) {
				byte[] imagedescompressed = Util.decompressZLib(product.get().getPicture());
				product.get().setPicture(imagedescompressed);
				list.add(product.get()); // obtengo el producto en su totalidad y lo agrego a la lista
				response.getProduct().setProducts(list);// seteo el producto con las lista.
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
	public ResponseEntity<ProductResponseRest> searchByName(String name) {
		ProductResponseRest response = new ProductResponseRest(); // va a ser una lista de producto
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();
		// buscamos la categoria para el producto
		try {
			listAux = productodao.findByNameContainingIgnoreCase(name);

			if (listAux.size() > 0) {
				listAux.stream().forEach((p) -> {
					byte[] imagedescompressed = Util.decompressZLib(p.getPicture());
					p.setPicture(imagedescompressed);
					list.add(p); // obtengo el producto en su totalidad y lo agrego a la lista
					response.getProduct().setProducts(list);// seteo el producto con las lista.
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

	@Override
	@Transactional()
	public ResponseEntity<ProductResponseRest> deleteById(Long id) {
		ProductResponseRest response = new ProductResponseRest(); // va a ser una lista de producto

		// buscamos la categoria para el producto
		try {
			productodao.deleteById(id);
			response.setMetadata("respuesta ok", "00", "producto  eliminado");

		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta NO ok", "-1", "error al eliminar el producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> search() {
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();
		//search producto
		listAux = (List<Product>) productodao.findAll();
		
		try {
			
			
			
			
			if( listAux.size() > 0) {
				
				listAux.stream().forEach( (p) -> {
					//byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
					//p.setPicture(imageDescompressed);
					list.add(p);
				});
				
				
				response.getProduct().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Productos encontrados");
				
			} else {
				response.setMetadata("respuesta nok", "-1", "Productos no encontrados ");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok", "-1", "Error al buscar productos");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductResponseRest> update(Product product, Long categoryId, Long id) {
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

			// buscamos el producto por id 
			Optional<Product> ProductSeach = productodao.findById(id);

			if (ProductSeach.isPresent()) {
				// procedemos a actualizar el producto
				;
				ProductSeach.get().setAccount(product.getAccount());
				ProductSeach.get().setPrice(product.getPrice());
				ProductSeach.get().setName(product.getName());
				ProductSeach.get().setPicture(product.getPicture());
				ProductSeach.get().setCategory(product.getCategory());
				//guardo el producto
				Product productoActualizado = productodao.save(ProductSeach.get());
				
				if (productoActualizado != null) {
					list.add(productoActualizado);
					response.getProduct().setProducts(list);;
					response.setMetadata("respuesta ok", "00", "producto actualizado");
				}else {
					response.setMetadata("respuesta NO ok", "-1", "Producto NO actualizado");
					return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
				
				
			} else {
				response.setMetadata("respuesta NO ok", "-1", "Producto NO actualizado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta NO ok", "-1", "error al guardar el producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}
}
