package com.ashish.web_proj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ashish.web_proj.model.Product;


@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> 
{
    // --- 1. NEW METHOD: For the /api/products endpoint (getting all products) ---
    // This is replacing the default findAll() to ensure only lightweight data is returned.
    @Query("SELECT NEW com.ashish.web_proj.repo.ProductSummary(" +
            "p.id, p.name, p.description, p.brand, p.price, " +
            "p.category, p.releaseDate, p.productAvailable, " +
            "p.stockQuantity, p.imageName) " +
            "FROM Product p")
    List<ProductSummary> findAllProductSummaries(); // <<< Returns the lightweight DTO
    
    
    // --- 2. UPDATED METHOD: For the /api/product/search endpoint ---
    // The query is updated to use SELECT NEW, and the return type is changed.
    @Query("SELECT NEW com.ashish.web_proj.repo.ProductSummary(" +
            "p.id, p.name, p.description, p.brand, p.price, " +
            "p.category, p.releaseDate, p.productAvailable, " +
            "p.stockQuantity, p.imageName) " + // <<< Explicitly list all non-BLOB fields
            "FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProductSummary> searchProducts(String keyword); // <<< Changed return type to ProductSummary
}