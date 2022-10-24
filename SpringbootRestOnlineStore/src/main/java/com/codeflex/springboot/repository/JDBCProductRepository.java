package com.codeflex.springboot.repository;

import com.codeflex.springboot.model.Product;
import com.codeflex.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository

public class JDBCProductRepository implements ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    LobHandler lobHandler;

    @Override
    public int saveProduct(Product product) {
        return jdbcTemplate.update("INSERT INTO product (name, categoryId, price) VALUES  (?, ?, ?)",
                product.getName(), product.getCategoryId(), product.getPrice());
    }

    @Override
    public int updateProduct(Product product) {
        return jdbcTemplate.update("UPDATE product SET name=?, categoryId=?, price=? where id = ?;",
                product.getName(), product.getCategoryId(), product.getPrice(), product.getId());
    }

    @Override
    public List<Product> findAllProducts() {
        return jdbcTemplate.query(
                "select * from product",
                (rs, rowNum) ->
                        new Product(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getInt("categoryId"),
                                rs.getDouble("price")
                        )
        );
    }

    @Override
    public int deleteProductById(long id) {
        return jdbcTemplate.update(
                "delete from product where id = ?",
                id);
    }


    @Override
    public Product findById(long id) {
        return jdbcTemplate.queryForObject(
                "select * from books where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        new Product(
                                rs.getString("name"),
                                rs.getInt("categoryId"),
                                rs.getLong("price")
                        )
        );
    }
}
