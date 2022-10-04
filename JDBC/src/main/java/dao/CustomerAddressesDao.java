package dao;

import dbConnection.DatabaseConnection;
import helpers.ResultSetMapper;
import pojos.CustomerAddresses;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerAddressesDao implements DAO<CustomerAddresses> {

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public CustomerAddressesDao() throws SQLException, IOException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(CustomerAddresses customerAddress) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO customer_addresses  (address, city, province, state, postal_code,  country)  VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, customerAddress.getAddress());
            preparedStatement.setString(2, customerAddress.getCity());
            preparedStatement.setString(3, customerAddress.getProvince());
            preparedStatement.setString(4, customerAddress.getState());
            preparedStatement.setInt(5, customerAddress.getPostal_code());
            preparedStatement.setString(6, customerAddress.getCountry());
            preparedStatement.executeUpdate();
            System.out.println("Customer address is saved successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteById(int address_id) {
        try {
            preparedStatement = connection.prepareStatement("Delete from customer_addresses WHERE address_id = ?");
            preparedStatement.setInt(1, address_id);
            preparedStatement.executeUpdate();
            System.out.printf("customerAddresses is deleted");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try {
            preparedStatement = connection.prepareStatement("Delete from customer_addresses");
            preparedStatement.executeUpdate();
            System.out.println("All records from CustomerAddresses table are deleted");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public CustomerAddresses getRandomId() {
        CustomerAddresses randomCustomerAddress = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM customer_addresses ORDER BY RANDOM() LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                randomCustomerAddress = CustomerAddresses.builder()
                        .address_id(resultSet.getInt("address_id"))
                        .address(resultSet.getString("address"))
                        .city(resultSet.getString("city"))
                        .province(resultSet.getString("province"))
                        .state(resultSet.getString("state"))
                        .postal_code(resultSet.getInt("postal_code"))
                        .country(resultSet.getString("country"))
                        .build();
            }
            System.out.println(randomCustomerAddress);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return randomCustomerAddress;
    }


    @Override
    public List<Integer> getRandomIds(int randomCount) {
        List<Integer> randomCustomerAddressIds = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("Select * from customer_addresses ORDER BY random() limit ?");
            preparedStatement.setInt(1, randomCount);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                randomCustomerAddressIds.add(resultSet.getInt("address_id"));
            }
            System.out.println(randomCustomerAddressIds);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return randomCustomerAddressIds;
    }


    @Override
    public int getRecordsCount() {
        int idRecords = 0;
        try {
            preparedStatement = connection.prepareStatement("Select COUNT (address_id) from customer_addresses ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idRecords = resultSet.getInt(1);
                System.out.printf("CustomerAddresses Id's count is : %d%n", idRecords);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return idRecords;
    }

    @Override
    public CustomerAddresses getById(int id) {
        CustomerAddresses customerAddresses = null;
        try {
            preparedStatement = connection.prepareStatement("Select * from customer_addresses Where address_id = ?");
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customerAddresses = CustomerAddresses.builder()
                        .address_id(resultSet.getInt("address_id"))
                        .address(resultSet.getString("address"))
                        .city(resultSet.getString("city"))
                        .province(resultSet.getString("province"))
                        .state(resultSet.getString("state"))
                        .postal_code(resultSet.getInt("postal_code"))
                        .country(resultSet.getString("country"))
                        .build();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println(customerAddresses);
        return customerAddresses;
    }

    @Override
    public List<CustomerAddresses> getByIds(List<Integer> ids) {
        ResultSetMapper resultSetMapper = new ResultSetMapper();
        try {
            preparedStatement = connection.prepareStatement
                    (String.format("SELECT * FROM customer_addresses WHERE address_id IN (%s)",
                            ids.stream().map(id -> "?").collect(Collectors.joining(", "))));

            for (int i = 0; i < ids.size(); i++) {
                preparedStatement.setInt(i + 1, ids.get(i));
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<CustomerAddresses> searchedCustomerAddresses = resultSetMapper.mapResultSetToObject(resultSet, CustomerAddresses.class);
        System.out.println("Searched customer addresses are:");
        for (CustomerAddresses customerAddresses : searchedCustomerAddresses) {
            System.out.println(customerAddresses);
        }
        return searchedCustomerAddresses;
    }

    public int getAddressId() {
        int addressId = 0;

        try {
            preparedStatement = connection.prepareStatement("Select address_id from customer_addresses order by random () limit 1");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                addressId = resultSet.getInt(1);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return addressId;
    }
}

