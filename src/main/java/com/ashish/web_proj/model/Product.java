package com.ashish.web_proj.model;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
	

	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private String brand;
	private BigDecimal price;
	private String category;
//	@Column(name = "release_date")
	private Date releaseDate;
	
//	@Column(name = "available")
	private Boolean productAvailable;
	
//	@Column(name = "quantity")
	private int stockQuantity;
	 
	private String imageName;
	private String imageType;
	@Lob
//	@Column(name = "image_data", columnDefinition = "MEDIUMBLOB") 
	private byte[] imageDate;

	
}
