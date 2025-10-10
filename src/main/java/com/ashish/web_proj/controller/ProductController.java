package com.ashish.web_proj.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ashish.web_proj.model.Product;
import com.ashish.web_proj.service.ProductService;
import com.ashish.web_proj.repo.ProductSummary; // <<< NEW IMPORT

@CrossOrigin(origins = "https://e-com-frontend-eley.onrender.com/")

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;
    
    
    // UPDATED: Returns List<ProductSummary> (lightweight DTO)
    @GetMapping("/products")
    public ResponseEntity<List<ProductSummary>> getAllProducts(){ // <<< CHANGED RETURN TYPE
        // Calls the service method which now returns ProductSummary list
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK); 
    }
    
    
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        // UNCHANGED: This returns the full Product entity (including image BLOB) for the detail view.
        Product product = service.getProductById(id);

        if(product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
            @RequestPart MultipartFile imageFile){
        // UNCHANGED: Full entity logic remains for adding
        try {
            System.out.println(product);
            Product product1 = service.addProduct(product, imageFile); 
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  
    
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId (@PathVariable int productId){
        // UNCHANGED: This is your dedicated image endpoint
        
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageDate();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile); 
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
            @RequestPart MultipartFile imageFile){
        // UNCHANGED: Full entity logic remains for updates
        Product product1 = null;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        }
        catch (IOException e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
        if(product1 != null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }
    
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        // UNCHANGED: Standard deletion
        Product product = service.getProductById(id);
        
        if(product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted ",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        
    }
    
    // UPDATED: Returns List<ProductSummary> (lightweight DTO)
    @GetMapping("/product/search")
    public ResponseEntity<List<ProductSummary>> searchProducts(@RequestParam String keyword){ // <<< CHANGED RETURN TYPE
        
        // The service method now returns the lightweight DTO list
        List<ProductSummary> products = service.searchProducts(keyword); 
        return new ResponseEntity<>(products, HttpStatus.OK);
        
    }
}