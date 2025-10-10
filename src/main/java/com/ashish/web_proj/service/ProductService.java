package com.ashish.web_proj.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ashish.web_proj.model.Product;
import com.ashish.web_proj.repo.ProductRepo;
import com.ashish.web_proj.repo.ProductSummary; // <<< NEW IMPORT for the DTO

@Service
public class ProductService {
    
    @Autowired
    private ProductRepo repo;
    
    // UPDATED METHOD: Returns the lightweight DTO list (ProductSummary)
    // This calls the new method you added to the repository: findAllProductSummaries()
    public List <ProductSummary> getAllProducts(){ 
         return repo.findAllProductSummaries(); 
    }

    public Product getProductById(int id) {
        // UNCHANGED: This still returns the full Product entity for the detail page
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        
        product.setImageDate(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }
    
    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        // UNCHANGED: Full entity logic remains for updates
        product.setImageDate(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        // UNCHANGED: Standard deletion
        repo.deleteById(id);
    }

    // UPDATED METHOD: Returns the lightweight DTO list (ProductSummary)
    // This calls the modified repository method: searchProducts(keyword)
    public List<ProductSummary> searchProducts(String keyword) {
        
        // The return type is now List<ProductSummary>
        return repo.searchProducts(keyword);
    }
 
}