package com.jw.shopping.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jw.shopping.dto.Product;

public interface ProductDAO {

	public boolean addProduct(final Product product, final String addedBy);
	public boolean updateImagePath(final Product product);
	public boolean deleteProduct(final int productId);
	public Product getProductById(final int productId);
	public List<Product> getProducts(
			@Param("categoryId")final int categoryId, 
			@Param("page") final Integer page, 
			@Param("pageSize") final int pageSize,
			@Param("offset") final int offset);
	public int getTotalPages(
			@Param("categoryId") final int categoryId, 
			@Param("pageSize") final int pageSize);
	
}
