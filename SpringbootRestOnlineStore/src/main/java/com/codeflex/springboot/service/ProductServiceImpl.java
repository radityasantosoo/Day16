package com.codeflex.springboot.service;

import com.codeflex.springboot.model.Product;
import com.codeflex.springboot.util.JDBCUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service("productService")
public class ProductServiceImpl implements ProductService {


    //  Using two hashmaps in order to provide performance of O(1) while fetching products
    private static HashMap<Long, Product> products = new HashMap<>();
    private static HashMap<String, Long> idNameHashMap = new HashMap<>();


    public List<Product> findAllProducts() {
        // Pagination should be added...
        return new ArrayList<>(products.values());
    }

    public Product findById(long id) {
        ResultSet carinama;
        if (idNameHashMap.get(id) != null){
            return products.get(idNameHashMap.get(id));
        }
        Product product = new Product();
        String query = "SELECT * FROM penjualan WHERE id = '"+id+"';";
        int rs = 0;
        try (Connection connection = JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, product.getId());
            preparedStatement.execute();
            // Step 3: Execute the query or update query
            carinama = preparedStatement.executeQuery();
            System.out.println();
            while(carinama.next())
                System.out.println(carinama.getLong(1)+
                        "  "+carinama.getString(2)+
                        "  "+carinama.getInt(3)+
                        "  "+carinama.getInt(4));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return products.get(id);
    }

    public Product findByName(String name) {
        ResultSet carinama;
        if (idNameHashMap.get(name) != null){
            return products.get(idNameHashMap.get(name));
        }
        Product product = new Product();
        String query = "SELECT FROM penjualan WHERE name = '"+name+"';";
        int rs = 0;
        try (Connection connection = JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, product.getId());
            preparedStatement.execute();
            // Step 3: Execute the query or update query
            carinama = preparedStatement.executeQuery();
            System.out.println();
            while(carinama.next())
                System.out.println(carinama.getLong(1)+
                        "  "+carinama.getString(2)+
                        "  "+carinama.getInt(3)+
                        "  "+carinama.getInt(4));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void saveProduct(Product product) {
        synchronized (this) {    //  Critical section synchronized
            products.put(product.getId(), product);
            idNameHashMap.put(product.getName(), product.getId());
            String query = "INSERT  INTO penjualan (id, name, categoryId, price) VALUES (?, ?, ?, ?);";
            int result = 0;
            try (Connection connection = JDBCUtils.getConnection();
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, product.getId());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setInt(3, product.getCategoryId());
                preparedStatement.setDouble(4, product.getPrice());
                System.out.println(preparedStatement);
                preparedStatement.execute();
                // Step 3: Execute the query or update query
               // result = preparedStatement.executeUpdate();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void updateProduct(Product product) {
        synchronized (this) {    //  Critical section synchronized
            products.put(product.getId(), product);
            idNameHashMap.put(product.getName(), product.getId());
            String query = "UPDATE penjualan set name = ? WHERE id = ?;";
            int result = 0;
            try (Connection connection = JDBCUtils.getConnection();
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setLong(2, product.getId());
                preparedStatement.executeUpdate();
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                result = preparedStatement.executeUpdate();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public void deleteProductById(long id) {
        synchronized (this) {    //  Critical section synchronized
            idNameHashMap.remove(products.get(id));
            products.remove(id);
            System.out.println(id);
            Product product = new Product();
            String query = "Delete FROM penjualan WHERE id = '"+id+"';";
            int result = 0;
            try (Connection connection = JDBCUtils.getConnection();
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setLong(1, product.getId());
                preparedStatement.executeUpdate();
                System.out.println(product.getId());
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                result = preparedStatement.executeUpdate();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public boolean isProductExist(Product product) {
        return findByName(product.getName()) != null;
    }

    public void deleteAllProducts() {
        products.clear();
    }

}
