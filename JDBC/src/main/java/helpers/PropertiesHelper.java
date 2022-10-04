package helpers;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {

    private static String configPath = "src/test/resources/config.properties";
    private String url;
    private String user;
    private String password;
    private Properties properties;
    private static PropertiesHelper propertiesHelper;

    static{
        try {
            propertiesHelper = new PropertiesHelper();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private PropertiesHelper() throws IOException{
        this.properties = new Properties();
        FileReader fileReader = new FileReader(configPath);
        this.properties.load(fileReader);
        this.url = this.properties.getProperty("url");
        this.user = this.properties.getProperty("user");
        this.password = this.properties.getProperty("password");
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public static PropertiesHelper getInstance() throws IOException {
        return propertiesHelper;
    }
}
