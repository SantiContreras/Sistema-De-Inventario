package com.santilasconi.inventory.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.santilasconi.inventory.models.Product;
import com.santilasconi.inventory.response.ProductResponseRest;
import com.santilasconi.inventory.services.IProductService;
import com.santilasconi.inventory.util.Util;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {
	
	private IProductService productService;
	
	
	public ProductRestController(IProductService productService) {
		super();
		this.productService = productService;
	}

	@PostMapping("/products")
	public ResponseEntity<ProductResponseRest> save(
			
			@RequestParam("name") String name,
			@RequestParam("prince") int prince,
			@RequestParam("account") int account,		
			@RequestParam("picture") MultipartFile picture,
			@RequestParam("categoryId") Long categoryId
			) throws Exception{
				
				
				Product producto = new Product();
				producto.setName(name);
				producto.setPrice(prince);
				producto.setAccount(account);
				producto.setPicture(Util.compressZLib(picture.getBytes()));
				
				ResponseEntity<ProductResponseRest> response = productService.save(producto, categoryId);
				
				return response;
	}
	

}
