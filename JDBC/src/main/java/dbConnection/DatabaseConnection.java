package dbConnection;

import helpers.PropertiesHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection = null;
    private PropertiesHelper propertiesHelper;

    /**
     * This method create a Database connection
     * @return
     */
    public Connection getConnection(){
        try {
            propertiesHelper = PropertiesHelper.getInstance();
            connection = DriverManager.getConnection(
                    propertiesHelper.getUrl(),
                    propertiesHelper.getUser(),
                    propertiesHelper.getPassword());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return connection;
    }

    /**
     * This method get an instance of database connection
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static DatabaseConnection getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
