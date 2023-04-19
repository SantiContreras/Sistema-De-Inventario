package com.santilasconi.inventory.Controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.santilasconi.inventory.models.Product;
import com.santilasconi.inventory.response.CategoryResponseRest;
import com.santilasconi.inventory.response.ProductResponseRest;
import com.santilasconi.inventory.services.IProductService;
import com.santilasconi.inventory.util.CategoryExelExport;
import com.santilasconi.inventory.util.Util;
import com.santilasconi.inventory.util.productExcelExport;

import net.bytebuddy.asm.Advice.Return;

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
			@RequestParam("price") int price,
			@RequestParam("account") int account,		
			@RequestParam("picture") MultipartFile picture,
			@RequestParam("categoryId") Long categoryId
			) throws Exception{
				
				
				Product producto = new Product();
				producto.setName(name);
				producto.setPrice(price);
				producto.setAccount(account);
				producto.setPicture(Util.compressZLib(picture.getBytes()));
				
				ResponseEntity<ProductResponseRest> response = productService.save(producto, categoryId);
				
				return response;
	}
	
	/** 
	 * @param id
	 * {@link Return}
	 * 
	 * */
	@GetMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> buscarPorId(@PathVariable Long  id){
		ResponseEntity<ProductResponseRest> response = productService.serachById(id);
		return response;
	}
	
	@GetMapping("/products/filter/{name}")
	public ResponseEntity<ProductResponseRest> buscarPorNombre(@PathVariable String name){
		ResponseEntity<ProductResponseRest> response = productService.searchByName(name);
		return response;
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<ProductResponseRest> deleteById(@PathVariable Long  id){
		ResponseEntity<ProductResponseRest> response = productService.deleteById(id);
		return response;
	}
	
	@GetMapping("/products")
	public ResponseEntity<ProductResponseRest> search(){
		ResponseEntity<ProductResponseRest> response = productService.search();
		return response;
	}
	
	@PutMapping("/Products/{id}")
	public ResponseEntity<ProductResponseRest> update(
			
			@RequestParam("name") String name,
			@RequestParam("price") int price,
			@RequestParam("account") int account,		
			@RequestParam("picture") MultipartFile picture,
			@RequestParam("categoryId") Long categoryId,
			@PathVariable Long id
			) throws Exception{
				
				
				Product producto = new Product();
				producto.setName(name);
				producto.setPrice(price);
				producto.setAccount(account);
				//comprimo la foto
				producto.setPicture(Util.compressZLib(picture.getBytes()));
				
				ResponseEntity<ProductResponseRest> response = productService.update(producto, categoryId , id);
				
				return response;
	}
	
	@GetMapping("/products/exportexcel")
	public void exportToExcel(HttpServletResponse response) throws Exception {
		response.setContentType("aplication/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=result_product";
		response.setHeader(headerKey, headerValue);
		
		ResponseEntity<ProductResponseRest> productResponse = productService.search();
		productExcelExport excelExport = new productExcelExport(productResponse.getBody().getProduct().getProducts());
		excelExport.export(response);
	}
		
	

}
