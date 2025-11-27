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

    @Autowired
    private S3Service s3Service; // Inject S3 Service
    
    public List<Product> getAllProducts(){ 
         return repo.findAll(); 
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        // 1. Upload to S3
        String imageUrl = s3Service.uploadFile(imageFile);
        
        // 2. Set the URL in the database entity
        product.setImageUrl(imageUrl);
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        
        return repo.save(product);
    }
    
    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        Product existingProduct = repo.findById(id).orElse(null);
        if(existingProduct == null) return null;

        // If a new file is provided, upload it and update URL
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = s3Service.uploadFile(imageFile);
            existingProduct.setImageUrl(imageUrl);
            existingProduct.setImageName(imageFile.getOriginalFilename());
            existingProduct.setImageType(imageFile.getContentType());
        }

        // Update other fields
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setReleaseDate(product.getReleaseDate());
        existingProduct.setProductAvailable(product.getProductAvailable());
        existingProduct.setStockQuantity(product.getStockQuantity());

        return repo.save(existingProduct);
    }

    public void deleteProduct(int id) {
        // Optional: Add logic here to delete from S3 as well using s3Service
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}