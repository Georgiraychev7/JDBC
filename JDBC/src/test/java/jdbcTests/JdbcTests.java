package jdbcTests;

import dao.CustomerAddressesDao;
import dao.CustomerDao;
import dao.OrdersDao;
import dbConnection.DatabaseConnection;
import helpers.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojos.Customers;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class JdbcTests {

     DatabaseConnection databaseConnection = null;
     private static Connection connection;
     Statement statement = null;
     PreparedStatement preparedStatement = null;
     ResultSet resultSet = null;
     List<String> names = new ArrayList<>();
     List<String> fakerNames = new ArrayList<>();

     private String searchedName = "Georgi";
     private String query = "SELECT * FROM customers where profile_name = '%s'";
     private String searchingFakeUserQuery = "SELECT * FROM customers where id = 15";

     CustomerDao customerDao = new CustomerDao();
     CustomerAddressesDao customerAddressesDao = new CustomerAddressesDao();
     OrdersDao ordersDao = new OrdersDao();

     public JdbcTests() throws SQLException, IOException {
     }

     @BeforeEach
     public void setUp() {
          try {
               connection = DatabaseConnection.getInstance().getConnection();
               statement = connection.createStatement();
               resultSet = statement.executeQuery(String.format(query, searchedName));
               while (resultSet.next()) {
                    names.add(resultSet.getString("profile_name"));
               }
          } catch (Exception exception) {
               System.out.println(exception.getMessage());
          }
     }

     @AfterEach
     public void closeConnection() {
          try {
               statement.close();
               resultSet.close();
          } catch (Exception e) {
               e.printStackTrace();
          }
     }


     @Test
     public void checkForCurrentName() {
          for (String name : names) {
               Assertions.assertEquals(name, searchedName);
          }
          System.out.println(names);
     }

     @Test
     public void createCustomerWithFakeData() {
          Assertions.assertNotNull(Utils.createCustomerWithFakeData());
     }

     @Test
     public void createListOfCustomers() {
          Assertions.assertNotNull(Utils.createListOfCustomers());
     }

     @Test
     public void testSaveCrudOperation() {
          int idRecords = 0;
          customerDao.save(Utils.createCustomerWithFakeData());
          try {
               preparedStatement = connection.prepareStatement("Select COUNT (id) from Customers ");
               resultSet = preparedStatement.executeQuery();
               while (resultSet.next()) {
                    idRecords = resultSet.getInt(1);
                    System.out.printf("Customers Id's count is : %d%n", idRecords);
               }
               Assertions.assertEquals(idRecords, 16);
          } catch (Exception exception) {
               System.out.println(exception.getMessage());
          }
     }

     @Test
     public void testDeleteById() {
          int idRecords = 0;
          customerDao.deleteById(39);
          try {
               preparedStatement = connection.prepareStatement("Select COUNT (id) from Customers ");
               resultSet = preparedStatement.executeQuery();
               while (resultSet.next()) {
                    idRecords = resultSet.getInt(1);
                    System.out.printf("Customers Id's count is : %d%n", idRecords);
                    Assertions.assertEquals(idRecords, 15);
               }
          } catch (Exception exception) {
               System.out.println(exception.getMessage());
          }
     }

     //execute this tests at the END !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     @Test
     public void testDeleteAll() {
          customerDao.deleteAll();
          try {
               statement = connection.createStatement();
               resultSet = statement.executeQuery("Select * from customers");
               Assertions.assertNull(resultSet);
          } catch (Exception exception) {
               System.out.println(exception.getMessage());
          }
     }

     @Test
     public void testGetRandomId() {
          Assertions.assertNotNull(customerDao.getRandomId());
     }

     @Test
     public void createListWithRandomIds() {
          Assertions.assertNotNull(customerDao.getRandomIds(8));
     }

     @Test
     public void getCountOfCustomerIds() {
          Assertions.assertNotNull(customerDao.getRecordsCount());
     }

     @Test
     public void getByIdTest() {
          Assertions.assertNotNull(customerDao.getById(2));
     }

     @Test
     public void getByIdsTest() {
          List<Integer> ids = new ArrayList<>();
          ids.add(4);
          ids.add(5);
          List<Customers> customers = customerDao.getByIds(ids);
          for (Customers customer : customers) {
               Assertions.assertEquals(customers.size(), ids.size());
               Assertions.assertNotNull(customers.get(0).getProfile_name());
               Assertions.assertNotNull(customers.get(1).getProfile_name());
          }
     }

     @Test
     public void testResultMapperWithId() {
          Assertions.assertNotNull(customerDao.getByIdWithResultSetMapper(2).get(0).getProfile_name());
     }

     @Test
     public void testResultMapperWithListOfIds() {
          List<Integer> ids = new ArrayList<>();
          ids.add(4);
          ids.add(5);
          List<Customers> customers = customerDao.getByIdsWithResultSetMapper(ids);
          for (Customers customer : customers) {
               Assertions.assertEquals(customers.size(), ids.size());
               Assertions.assertNotNull(customers.get(0).getProfile_name());
               Assertions.assertNotNull(customers.get(1).getProfile_name());
          }
     }

     @Test
     public void testDbUtilsGetById() {
          Assertions.assertNotNull(customerDao.getByIdWithApacheDbUtils(2).get(0).getProfile_name());
     }

     @Test
     public void testDbUtilsGetByIds() {
          List<Integer> ids = new ArrayList<>();
          ids.add(4);
          ids.add(5);
          List<Customers> customers = customerDao.getByIdsWithApacheDbUtils(ids);
          for (Customers customer : customers) {
               Assertions.assertEquals(customers.size(), ids.size());
               Assertions.assertNotNull(customers.get(0).getProfile_name());
               Assertions.assertNotNull(customers.get(1).getProfile_name());
          }
     }

     @Test
     public void getCustomerAddressById() {
          Assertions.assertNotNull(customerDao.getCustomerAddressByIdWithDbUtils(257));
     }

     @Test
     public void getCustomerOrdersById() {
          ordersDao.getCustomerOrdersByIdWithDbUtils(257);
     }

}

