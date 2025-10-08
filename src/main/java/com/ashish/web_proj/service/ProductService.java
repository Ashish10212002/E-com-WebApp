package com.ashish.web_proj.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ashish.web_proj.model.Product;
import com.ashish.web_proj.repo.ProductRepo;

@Service
public class ProductService {

	
	@Autowired
	private ProductRepo repo;
	
	public List <Product> getAllProducts(){
		 return repo.findAll();
		  
	}

	public Product getProductById(int id) {
		
		return repo.findById(id).orElse(null);
	}

	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		
		product.setImageDate(imageFile.getBytes());
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		return repo.save(product);
	}

	
	

	public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
		
		product.setImageDate(imageFile.getBytes());
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		return repo.save(product);
		
	}

	public void deleteProduct(int id) {
		
		repo.deleteById(id);
		
	}

	public List<Product> searchProducts(String keyword) {
	
		
		return repo.searchProducts(keyword);
	}
  
}
