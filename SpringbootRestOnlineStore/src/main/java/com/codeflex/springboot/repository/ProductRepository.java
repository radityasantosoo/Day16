package com.codeflex.springboot.repository;


import java.util.List;
import java.util.Optional;

import com.codeflex.springboot.model.Product;

public interface ProductRepository {

    int saveProduct(Product product);
    int updateProduct(Product product);

    List<Product> findAllProducts();

    Product findById(long id);

    int deleteProductById(long id);

}
