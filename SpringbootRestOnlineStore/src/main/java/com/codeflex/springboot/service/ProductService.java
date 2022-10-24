package com.codeflex.springboot.service;


import java.util.List;

import com.codeflex.springboot.model.Product;

public interface ProductService {
	
	Product findById(long id);
	
	Product findByName(String name);
	
	void saveProduct(Product product);
	
	void updateProduct(Product product);
	
	void deleteProductById(long id);

	List<Product> findAllProducts();
	
	void deleteAllProducts();
	
	boolean isProductExist(Product product);
	
}
