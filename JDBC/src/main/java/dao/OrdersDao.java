package dao;

import dbConnection.DatabaseConnection;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import pojos.Orders;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class OrdersDao implements DAO<Orders>{

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private DatabaseConnection databaseConnection = new DatabaseConnection();



    public OrdersDao() throws SQLException, IOException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Orders orders) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO Orders  (customer_id, is_order_completed, is_order_payed, date_of_order, date_order_completed)  VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1,orders.getCustomer_id());
            preparedStatement.setBoolean(2, orders.is_order_completed());
            preparedStatement.setBoolean(3, orders.is_order_payed());
            preparedStatement.setTimestamp(4, orders.getDate_of_order());
            preparedStatement.setTimestamp(5,orders.getDate_order_completed());
            preparedStatement.executeUpdate();
            System.out.println("Order is saved successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            preparedStatement = connection.prepareStatement("Delete from Orders WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.printf("Order  is deleted");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void deleteAll() {
        try {
            preparedStatement = connection.prepareStatement("Delete from Orders");
            preparedStatement.executeUpdate();
            System.out.println("All records from Orders table are deleted");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public Orders getRandomId() {
        Orders randomOrder = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Orders ORDER BY RANDOM() LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                randomOrder = Orders.builder()
                        .id(resultSet.getInt("id"))
                        .customer_id(resultSet.getInt("customer_id"))
                        .is_order_completed(resultSet.getBoolean("is_order_completed"))
                        .is_order_payed(resultSet.getBoolean("is_order_payed"))
                        .date_of_order(resultSet.getTimestamp("date_of_order"))
                        .date_order_completed(resultSet.getTimestamp("date_order_completed"))
                        .build();
            }
            System.out.println(randomOrder);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return randomOrder;
    }


    @Override
    public List<Integer> getRandomIds(int randomCount) {
        List<Integer> randomOrdersIds = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("Select * from Orders ORDER BY random() limit ?");
            preparedStatement.setInt(1, randomCount);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                randomOrdersIds.add(resultSet.getInt("id"));
            }
            System.out.println(randomOrdersIds);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return randomOrdersIds;
    }



    @Override
    public int getRecordsCount() {
        int idRecords = 0;
        try {
            preparedStatement = connection.prepareStatement("Select COUNT (id) from Orders ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idRecords = resultSet.getInt(1);
                System.out.printf("Orders Id's count is : %d%n", idRecords);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return idRecords;
    }

    @Override
    public Orders getById(int id) {
        Orders order = null;
        try {
            preparedStatement = connection.prepareStatement("Select * from Orders Where id = ?");
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = Orders.builder()
                        .id(resultSet.getInt("id"))
                        .customer_id(resultSet.getInt("customer_id"))
                        .is_order_completed(resultSet.getBoolean("is_order_completed"))
                        .is_order_payed(resultSet.getBoolean("is_order_payed"))
                        .date_of_order(resultSet.getTimestamp("date_of_order"))
                        .date_order_completed(resultSet.getTimestamp("date_order_completed"))
                        .build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println(order);
        return order;
    }

    @Override
    public List<Orders> getByIds(List<Integer> ids) {
        List<Orders> orders = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("Select * from Orders Where id = ?");
            for(int i = 0; i < ids.size(); i++) {
                preparedStatement.setInt(1, ids.get(i));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Orders order = new Orders();
                    order.setId(resultSet.getInt("id"));
                    order.setCustomer_id(resultSet.getInt("customer_id"));
                    order.set_order_completed(resultSet.getBoolean("is_order_completed"));
                    order.set_order_payed(resultSet.getBoolean("is_order_payed"));
                    order.setDate_of_order(resultSet.getTimestamp("date_of_order"));
                    order.setDate_order_completed(resultSet.getTimestamp("date_order_completed"));
                    orders.add(order);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println(orders);
        return orders;
    }

    /**
     * This method return customer orders
     * @param id
     * @return
     */
    public List<Orders> getCustomerOrdersByIdWithDbUtils(int id) {
        List<Orders> customerOrders = new ArrayList<>();
        try {
            ResultSetHandler<List<Orders>> resultSetHandler = new BeanListHandler<Orders>(Orders.class);
            QueryRunner runner = new QueryRunner();
            customerOrders = runner.query(databaseConnection.getConnection(), String.format("Select public.customers.profile_name ,customer_id, is_order_completed , date_of_order, array_agg(product_name || ' ' || product_type || ' ' ||date_of_order || ' '|| is_order_completed) orderedProducts \n" +
                    "from products_inventory \n" +
                    "inner join orders_product_quantities \n" +
                    "on products_inventory.id = orders_product_quantities.product_id \n" +
                    "inner join orders on orders_product_quantities.orders_id = orders.id\n" +
                    "inner join customers on orders.customer_id = customers.id \n" +
                    "Where customer_id = %s\n" +
                    "Group By customer_id , profile_name, is_order_completed, date_of_order;", id), resultSetHandler);
            System.out.println(customerOrders);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return customerOrders;
    }
}
