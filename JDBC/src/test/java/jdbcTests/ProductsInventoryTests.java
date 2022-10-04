package jdbcTests;

import dao.CustomerAddressesDao;
import dao.ProductsInventoryDao;
import dbConnection.DatabaseConnection;
import helpers.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojos.CustomerAddresses;
import pojos.ProductsInventory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsInventoryTests {

    private static Connection connection;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<String> names = new ArrayList<>();

    private int id = 1;
    private String query = "SELECT * FROM products_inventory where id = '%s'";


    ProductsInventoryDao productsInventoryDao = new ProductsInventoryDao();

    public ProductsInventoryTests() throws SQLException, IOException {
    }


    @BeforeEach
    public void setUp(){
        try  {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(query, id));
            while (resultSet.next()){
                names.add(resultSet.getString("id"));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @AfterEach
    public void closeConnection() {
        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ProductInventorySaveTests(){
        int idRecords = 0;
        productsInventoryDao.save(Utils.createProductWithFakeData());
        try {
            preparedStatement = connection.prepareStatement("Select COUNT (id) from products_inventory ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idRecords = resultSet.getInt(1);
                System.out.printf("Products Id's count is : %d%n", idRecords);
            }
            Assertions.assertEquals(idRecords,18);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void testDeleteById() {
        int idRecords = 0;
        productsInventoryDao.deleteById(19);
        try {
            preparedStatement = connection.prepareStatement("Select COUNT (id) from products_inventory ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idRecords = resultSet.getInt(1);
                System.out.printf("Products Id's count is : ", idRecords);
            }
            Assertions.assertEquals(idRecords,17);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void testDeleteAll() {
        productsInventoryDao.deleteAll();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from products_inventory");
            Assertions.assertNull(resultSet);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void testGetRandomId() {
        Assertions.assertNotNull(productsInventoryDao.getRandomId());
    }

    @Test
    public void createListWithRandomIds () {
        Assertions.assertNotNull(productsInventoryDao.getRandomIds(8));
    }

    @Test
    public void getCountOfCustomerIds() {
        Assertions.assertNotNull(productsInventoryDao.getRecordsCount());
    }

    @Test
    public void getByIdTest() {
        Assertions.assertNotNull(productsInventoryDao.getById(2));
    }

    @Test
    public void getByIdsTest() {
        List<Integer> ids = new ArrayList<>();
        ids.add(4);
        ids.add(5);
        List <ProductsInventory> productsInventories  = productsInventoryDao.getByIds(ids);
        for (ProductsInventory products : productsInventories) {
            Assertions.assertEquals(productsInventories.size(),ids.size());
            Assertions.assertNotNull(productsInventories.get(0).getProduct_name());
        }
    }
}
