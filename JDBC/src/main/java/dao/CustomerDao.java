package dao;

import dbConnection.DatabaseConnection;
import helpers.ResultSetMapper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import pojos.Customers;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * In this class will be stored methods for CRUD operations with Db
 */

public class CustomerDao implements DAO<Customers> {

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private DatabaseConnection databaseConnection = new DatabaseConnection();


    public CustomerDao() throws SQLException, IOException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Customers customer) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO CUSTOMERS  (profile_name, email, phone, age, gdpr_consent,  is_customer_profile_active, profile_created_at,profile_deactivated, reason_for_deactivation, notes, address_id)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, customer.getProfile_name());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setInt(4, customer.getAge());
            preparedStatement.setBoolean(5, customer.isGdpr_consent());
            preparedStatement.setBoolean(6, customer.isIs_customer_profile_active());
            preparedStatement.setTimestamp(7, customer.getProfile_created_at());
            preparedStatement.setTimestamp(8, customer.getProfile_deactivated());
            preparedStatement.setString(9, customer.getReason_for_deactivation());
            preparedStatement.setString(10, customer.getNotes());
            preparedStatement.setInt(11, customer.getAddress_id());
            preparedStatement.executeUpdate();
            System.out.println("Customer is saved successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            preparedStatement = connection.prepareStatement("Delete from CUSTOMERS WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.printf("Customer  is deleted");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void deleteAll() {
        try {
            preparedStatement = connection.prepareStatement("Delete from CUSTOMERS");
            preparedStatement.executeUpdate();
            System.out.println("All records from Customers table are deleted");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public Customers getRandomId() {
        Customers randomCustomer = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS ORDER BY RANDOM() LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                randomCustomer = Customers.builder()
                        .id(resultSet.getInt("id"))
                        .profile_name(resultSet.getString("profile_name"))
                        .email(resultSet.getString("email"))
                        .phone(resultSet.getString("phone"))
                        .age(resultSet.getInt("age"))
                        .Gdpr_consent(resultSet.getBoolean("gdpr_consent"))
                        .Is_customer_profile_active(resultSet.getBoolean("is_customer_profile_active"))
                        .Profile_created_at(resultSet.getTimestamp("Profile_created_at"))
                        .Profile_deactivated(resultSet.getTimestamp("Profile_deactivated"))
                        .reason_for_deactivation(resultSet.getString("reason_for_deactivation"))
                        .notes(resultSet.getString("notes"))
                        .build();
            }
            System.out.println(randomCustomer);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return randomCustomer;
    }

    public int getId() {
        int idRecord = 0;
        try {
            preparedStatement = connection.prepareStatement("SELECT id FROM CUSTOMERS ORDER BY RANDOM() LIMIT 1");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idRecord = resultSet.getInt(1);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return idRecord;
    }


    @Override
    public List<Integer> getRandomIds(int randomCount) {
        List<Integer> randomCustomersIds = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("Select * from Customers ORDER BY random() limit ?");
            preparedStatement.setInt(1, randomCount);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                randomCustomersIds.add(resultSet.getInt("id"));
            }
            System.out.println(randomCustomersIds);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return randomCustomersIds;
    }


    @Override
    public int getRecordsCount() {
        int idRecords = 0;
        try {
            preparedStatement = connection.prepareStatement("Select COUNT (id) from Customers ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idRecords = resultSet.getInt(1);
                System.out.printf("Customers Id's count is : %d%n", idRecords);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return idRecords;
    }

    @Override
    public Customers getById(int id) {
        Customers customer = null;
        try {
            preparedStatement = connection.prepareStatement("Select * from Customers Where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer = Customers.builder()
                        .id(resultSet.getInt("id"))
                        .profile_name(resultSet.getString("profile_name"))
                        .email(resultSet.getString("email"))
                        .phone(resultSet.getString("phone"))
                        .age(resultSet.getInt("age"))
                        .Gdpr_consent(resultSet.getBoolean("gdpr_consent"))
                        .Is_customer_profile_active(resultSet.getBoolean("is_customer_profile_active"))
                        .Profile_created_at(resultSet.getTimestamp("Profile_created_at"))
                        .Profile_deactivated(resultSet.getTimestamp("Profile_deactivated"))
                        .reason_for_deactivation(resultSet.getString("reason_for_deactivation"))
                        .notes(resultSet.getString("notes"))
                        .address_id(resultSet.getInt("address_id"))
                        .build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println(customer);
        return customer;
    }

    @Override
    public List<Customers> getByIds(List<Integer> ids) {
        List<Customers> customers = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("Select * from Customers Where id = ?");
            for (int i = 0; i < ids.size(); i++) {
                preparedStatement.setInt(1, ids.get(i));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Customers customer = new Customers();
                    customer.setId(resultSet.getInt("id"));
                    customer.setProfile_name(resultSet.getString("Profile_name"));
                    customer.setEmail(resultSet.getString("email"));
                    customer.setPhone(resultSet.getString("phone"));
                    customer.setAge(resultSet.getInt("age"));
                    customer.setGdpr_consent(resultSet.getBoolean("gdpr_consent"));
                    customer.setIs_customer_profile_active(resultSet.getBoolean("is_customer_profile_active"));
                    customer.setProfile_created_at(resultSet.getTimestamp("Profile_created_at"));
                    customer.setProfile_deactivated(resultSet.getTimestamp("Profile_deactivated"));
                    customer.setReason_for_deactivation(resultSet.getString("reason_for_deactivation"));
                    customer.setNotes(resultSet.getString("notes"));
                    customer.setAddress_id(resultSet.getInt("address_id"));
                    customers.add(customer);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println(customers);
        return customers;
    }

    /**
     * This method return a Customer by id using ResultSetMapper
     * @param id
     * @return
     */
    public List<Customers> getByIdWithResultSetMapper(int id) {
        ResultSetMapper resultSetMapper = new ResultSetMapper();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Customers> searchedCustomers = resultSetMapper.mapResultSetToObject(resultSet, Customers.class);
        System.out.println("Searched customer is:" + searchedCustomers);

        return searchedCustomers;
    }

    /**
     * This method return a list of customers by id's using ResultSetMapper
     * @param ids
     * @return
     */
    public List<Customers> getByIdsWithResultSetMapper(List<Integer> ids) {
        ResultSetMapper resultSetMapper = new ResultSetMapper();
        try {
            preparedStatement = connection.prepareStatement
                    (String.format("SELECT * FROM customers WHERE id IN (%s)",
                            ids.stream().map(id -> "?").collect(Collectors.joining(", "))));

            for (int i = 0; i < ids.size(); i++) {
                preparedStatement.setInt(i + 1, ids.get(i));
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Customers> searchedCustomers = resultSetMapper.mapResultSetToObject(resultSet, Customers.class);
        System.out.println("Searched customers are:");
        for (Customers customer : searchedCustomers) {
            System.out.println(customer);
        }
        return searchedCustomers;
    }

    /**
     * This method return a Customer by id using ApacheDbUtils
     * @param id
     * @return
     */
    public List<Customers> getByIdWithApacheDbUtils(int id) {
        List<Customers> customersList = new ArrayList<>();
        try {
            ResultSetHandler<List<Customers>> resultSetHandler = new BeanListHandler<Customers>(Customers.class);
            QueryRunner runner = new QueryRunner();
            customersList = runner.query(databaseConnection.getConnection(), String.format("Select * from Customers Where id = %s", id), resultSetHandler);
            System.out.println("Searched customer is:" + customersList);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return customersList;
    }

    /**
     * This method return a list of Customers by id's using ApacheDbUtils
     * @param ids
     * @return
     */
    public List<Customers> getByIdsWithApacheDbUtils(List<Integer> ids) {
        List<Customers> customersList = new ArrayList<>();
        try {
            ResultSetHandler<List<Customers>> resultSetHandler = new BeanListHandler<Customers>(Customers.class);
            QueryRunner runner = new QueryRunner();
            Array idsArray = connection.createArrayOf("int", ids.toArray());
            customersList = runner.query(databaseConnection.getConnection(), "Select * from Customers Where id = any(?)", idsArray, resultSetHandler);
            System.out.println("Searched customers are:");
            for (Customers customer : customersList) {
                System.out.println(customer);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return customersList;
    }

    /**
     * This method return customer address using ApacheDbUtils
     * @param id
     * @return
     */
    public List<Customers> getCustomerAddressByIdWithDbUtils(int id) {
        List<Customers> customerAddressList = new ArrayList<>();
        try {
            ResultSetHandler<List<Customers>> resultSetHandler = new BeanListHandler<Customers>(Customers.class);
            QueryRunner runner = new QueryRunner();
            customerAddressList = runner.query(databaseConnection.getConnection(), String.format("Select profile_name,id , email , phone , age , array_agg(public.customer_addresses.address_id || ' ' || address ||' '||city || ' ' || province || ' ' || postal_code || ' ' || country) customerAddress From public.customers inner join public.customer_addresses on public.customers.address_id  = public.customer_addresses.address_id Where id = %s group by id", id), resultSetHandler);
            System.out.println(customerAddressList);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return customerAddressList;
    }
}



