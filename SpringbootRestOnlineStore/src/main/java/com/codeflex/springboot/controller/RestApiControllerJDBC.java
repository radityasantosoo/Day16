package com.codeflex.springboot.controller;

import com.codeflex.springboot.model.Product;
import com.codeflex.springboot.repository.ProductRepository;
import com.codeflex.springboot.service.ProductService;
import com.codeflex.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiControllerJDBC {


    public static final Logger logger = LoggerFactory.getLogger(RestApiControllerJDBC.class);

    @Autowired
    ProductService productService; //Service which will do all data retrieval/manipulation work

    @Autowired
    @Qualifier("jdbcProductRepository")
    private ProductRepository productRepository;

    // -------------------Retrieve All Products--------------------------------------------

    @RequestMapping(value = "/product/", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<List<Product>> listAllProducts() {
        List<Product> products = productService.findAllProducts();
//        List<Product> products = productRepository.findAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // -------------------Retrieve Single Product------------------------------------------

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable("id") long id) {
        logger.info("Fetching Product with id {}", id);
//        Product product = productService.findById(id);
        Product product = productRepository.findById(id);
//        if (product == null) {
//            logger.error("Product with id {} not found.", id);
//            return new ResponseEntity<>(new CustomErrorType("Product with id " + id  + " not found"), HttpStatus.NOT_FOUND);
//        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // -------------------Create a Product-------------------------------------------

    @RequestMapping(value = "/product/", method = RequestMethod.POST, produces="application/json")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        logger.info("Creating Product : {}", product);

//        if (productService.isProductExist(product)) {
//            logger.error("Unable to create. A Product with name {} already exist", product.getName());
//            return new ResponseEntity<>(new CustomErrorType("Unable to create. A Product with name " +
//                    product.getName() + " already exist."), HttpStatus.CONFLICT);
//        }

        productRepository.saveProduct(product);

//        productService.saveProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        logger.info("Updating Product with id {}", id);
        System.out.println(product.getName());

        Product currentProduct = new Product();
//        if (currentProduct == null) {
//            logger.error("Unable to update. Product with id {} not found.", id);
//            return new ResponseEntity<>(new CustomErrorType("Unable to upate. Product with id " + id + " not found."),
//                    HttpStatus.NOT_FOUND);
//        }
        currentProduct.setName(product.getName());
        currentProduct.setCategoryId(product.getCategoryId());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setId(id);

        productRepository.updateProduct(currentProduct);
//        productService.updateProduct(currentProduct);
        return new ResponseEntity<>(currentProduct, HttpStatus.OK);
    }

    // ------------------- Delete a Product-----------------------------------------

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Product with id {}", id);

//        Product product = productService.findById(id);
//        if (product == null) {
//            logger.error("Unable to delete. Product with id {} not found.", id);
//            return new ResponseEntity<>(new CustomErrorType("Unable to delete. Product with id " + id + " not found."),
//                    HttpStatus.NOT_FOUND);
//        }
        productRepository.deleteProductById(id);
//        productService.deleteProductById(id);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Products-----------------------------

    @RequestMapping(value = "/product/", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteAllProducts() {
        logger.info("Deleting All Products");

        productService.deleteAllProducts();
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

}