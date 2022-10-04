package main;

import dbConnection.DatabaseConnection;

import java.sql.*;

public class TryWithResourceDbConnection {

    public static void main(String[] args) {

        DatabaseConnection databaseConnection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            statement = connection.createStatement();
            String query = "SELECT * FROM customers";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        } finally {
            try {
                statement.close();
                resultSet.close();
                databaseConnection.getConnection().close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    }
