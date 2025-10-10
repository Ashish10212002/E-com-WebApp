package com.ashish.web_proj.repo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ProductSummary {
    private final int id;
    private final String name;
    private final String description;
    private final String brand;
    private final BigDecimal price;
    private final String category;
    private final Date releaseDate;
    private final Boolean productAvailable;
    private final int stockQuantity;
    private final String imageName; // Keep this, as it's small metadata

    // IMPORTANT: Constructor must match the order of fields selected in the Repository @Query
    public ProductSummary(int id, String name, String description, String brand, BigDecimal price, 
                          String category, Date releaseDate, Boolean productAvailable, 
                          int stockQuantity, String imageName) {
        // Initialize all fields...
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.category = category;
        this.releaseDate = releaseDate;
        this.productAvailable = productAvailable;
        this.stockQuantity = stockQuantity;
        this.imageName = imageName;
    }
    
    // Getters for all fields... (Lombok @Data on the class is an alternative)
    // ...
}