package com.jw.shopping.dto;

import java.math.BigDecimal;
import org.springframework.stereotype.Repository;

/*
CREATE TABLE `products` (
	`product_id` INT NOT NULL AUTO_INCREMENT,
	`product_name` VARCHAR(100) NOT NULL COLLATE 'utf8mb3_general_ci',
	`product_price` BIGINT NULL DEFAULT NULL,
	`product_number` INT NULL DEFAULT NULL,
	`product_category` TINYINT UNSIGNED NOT NULL,
	`added_by` VARCHAR(12) NOT NULL COLLATE 'utf8mb3_general_ci',
	`image_path` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	PRIMARY KEY (`product_id`) USING BTREE,
	INDEX `FK_products_users` (`added_by`) USING BTREE,
	CONSTRAINT `FK_products_users` FOREIGN KEY (`added_by`) REFERENCES `users` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
AUTO_INCREMENT=25
;
 */

@Repository
public class Product {
    private int id;
    private String name;
    private BigDecimal price;
    private int number;
    private int category;
    private String imagePath;
    
	public Product() {}

	public Product(int id, String name, BigDecimal price, int number, int category, String imagePath) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.number = number;
		this.category = category;
		this.imagePath = imagePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getCategory() {
	    return category;
	}
	
	public void setCategory(int category) {
	    this.category = category;
	}
	
	public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
