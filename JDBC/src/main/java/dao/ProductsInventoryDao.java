package dao;

import dbConnection.DatabaseConnection;
import helpers.ResultSetMapper;
import pojos.ProductsInventory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsInventoryDao implements DAO<ProductsInventory> {

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public ProductsInventoryDao() throws SQLException, IOException {
        connection = DatabaseConnection.getInstance().getConnection();
    }
    @Override
    public void save(ProductsInventory productInventory) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO products_inventory  (product_name, quantity, product_type, price_without_vat, price_with_vat,  is_product_in_stock, warehouse, supplier_id)  VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, productInventory.getProduct_name());
            preparedStatement.setInt(2, productInventory.getQuantity());
            preparedStatement.setString(3, productInventory.getProduct_type());
            preparedStatement.setInt(4, productInventory.getPrice_without_vat());
            preparedStatement.setInt(5, productInventory.getPrice_with_vat());
            preparedStatement.setBoolean(6, productInventory.is_product_in_stock());
            preparedStatement.setString(7, productInventory.getWarehouse());
            preparedStatement.setInt(8, productInventory.getSupplier_id());
            preparedStatement.executeUpdate();
            System.out.println("Product is saved successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            preparedStatement = connection.prepareStatement("Delete from products_inventory WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.printf("Product with id %d is deleted.%n");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void deleteAll() {
        try {
            preparedStatement = connection.prepareStatement("Delete from products_inventory");
            preparedStatement.executeUpdate();
            System.out.println("All records from Product inventory table are deleted");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public ProductsInventory getRandomId() {
        ProductsInventory randomProductsInventory = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM products_inventory ORDER BY RANDOM() LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                randomProductsInventory = ProductsInventory.builder()
                        .id(resultSet.getInt("id"))
                        .product_name(resultSet.getString("product_name"))
                        .quantity(resultSet.getInt("quantity"))
                        .product_type(resultSet.getString("product_type"))
                        .price_without_vat(resultSet.getInt("price_without_vat"))
                        .price_with_vat(resultSet.getInt("price_with_vat"))
                        .is_product_in_stock(resultSet.getBoolean("is_product_in_stock"))
                        .warehouse(resultSet.getString("warehouse"))
                        .supplier_id(resultSet.getInt("supplier_id"))
                        .build();
            }
            System.out.println(randomProductsInventory);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return randomProductsInventory;
    }


    @Override
    public List<Integer> getRandomIds(int randomCount) {
        List<Integer> randomProductsInventoryIds = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("Select * from products_inventory ORDER BY random() limit ?");
            preparedStatement.setInt(1, randomCount);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                randomProductsInventoryIds.add(resultSet.getInt("id"));
            }
            System.out.println(randomProductsInventoryIds);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return randomProductsInventoryIds;
    }


    @Override
    public int getRecordsCount() {
        int idRecords = 0;
        try {
            preparedStatement = connection.prepareStatement("Select COUNT (id) from products_inventory");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idRecords = resultSet.getInt(1);
                System.out.printf("Product Id's count is : %d%n", idRecords);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return idRecords;
    }

    @Override
    public ProductsInventory getById(int id) {
        ProductsInventory productsIvetory = null;
        try {
            preparedStatement = connection.prepareStatement("Select * from products_inventory Where address_id = ?");
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productsIvetory = ProductsInventory.builder()
                        .id(resultSet.getInt("id"))
                        .product_name(resultSet.getString("product_name"))
                        .quantity(resultSet.getInt("quantity"))
                        .product_type(resultSet.getString("product_type"))
                        .price_without_vat(resultSet.getInt("price_without_vat"))
                        .price_with_vat(resultSet.getInt("price_with_vat"))
                        .is_product_in_stock(resultSet.getBoolean("is_product_in_stock"))
                        .warehouse(resultSet.getString("warehouse"))
                        .supplier_id(resultSet.getInt("supplier_id"))
                        .build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println(productsIvetory);
        return productsIvetory;
    }

    @Override
    public List<ProductsInventory> getByIds(List<Integer> ids) {
        ResultSetMapper resultSetMapper = new ResultSetMapper();
        try {
            preparedStatement = connection.prepareStatement
                    (String.format("SELECT * FROM products_inventory WHERE id IN (%s)",
                            ids.stream().map(id -> "?").collect(Collectors.joining(", "))));

            for (int i = 0; i < ids.size(); i++) {
                preparedStatement.setInt(i + 1, ids.get(i));
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
            List<ProductsInventory> searchedProductIds = resultSetMapper.mapResultSetToObject(resultSet, ProductsInventory.class);
            System.out.println("Searched products are:");
                for (ProductsInventory products : searchedProductIds) {
                System.out.println(products);
        }
        return searchedProductIds;
    }
}
