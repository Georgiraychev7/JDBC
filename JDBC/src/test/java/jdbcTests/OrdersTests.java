package jdbcTests;

import dao.CustomerAddressesDao;
import dao.OrdersDao;
import dbConnection.DatabaseConnection;
import helpers.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojos.CustomerAddresses;
import pojos.Orders;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersTests {

    DatabaseConnection databaseConnection = null;
    private static Connection connection;
    Statement statement = null;
    PreparedStatement preparedStatement= null;
    ResultSet resultSet = null;
    List<String> names = new ArrayList<>();

    private int id = 1;
    private String query = "SELECT * FROM orders where id = '%s'";


    OrdersDao ordersDao = new OrdersDao();

    public OrdersTests() throws SQLException, IOException {
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
    public void CustomerAddressSaveTests(){
        int idRecords = 0;
        ordersDao.save(Utils.createOrderWithFakeData());
        try {
            preparedStatement = connection.prepareStatement("Select COUNT (id) from Orders ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idRecords = resultSet.getInt(1);
                System.out.printf("Orders Id's count is : %d%n", idRecords);
            }
            Assertions.assertEquals(idRecords,9);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void testDeleteById() {
        int idRecords = 0;
        ordersDao.deleteById(21);
            try {
                preparedStatement = connection.prepareStatement("Select COUNT (id) from Orders ");
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    idRecords = resultSet.getInt(1);
                    System.out.printf("Orders Id's count is : %d%n", idRecords);
                }
                Assertions.assertEquals(idRecords,8);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void testDeleteAll() {
        ordersDao.deleteAll();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from Orders");
            Assertions.assertNull(resultSet);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void testGetRandomId() {
        Assertions.assertNotNull(ordersDao.getRandomId());
    }

    @Test
    public void createListWithRandomIds () {
        Assertions.assertNotNull(ordersDao.getRandomIds(8));
    }

    @Test
    public void getCountOfCustomerIds() {
        Assertions.assertNotNull(ordersDao.getRecordsCount());
    }

    @Test
    public void getByIdTest() {
        Assertions.assertNotNull(ordersDao.getById(2));
    }

    @Test
    public void getByIdsTest() {
        List<Integer> ids = new ArrayList<>();
        ids.add(4);
        ids.add(5);
        List <Orders> order  = ordersDao.getByIds(ids);
        for (Orders orders : order) {
            Assertions.assertEquals(order.size(),ids.size());
            Assertions.assertNotNull(order.get(0).getId());
            Assertions.assertNotNull(order.get(1).getCustomer_id());
        }
    }
}
