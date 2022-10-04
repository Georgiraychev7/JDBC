package cucumber.stepDefinitions;

import com.github.javafaker.Faker;
import dao.CustomerAddressesDao;
import dao.CustomerDao;
import dbConnection.DatabaseConnection;
import helpers.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import pojos.CustomerAddresses;
import pojos.Customers;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerDbStepDefinitions {

    private Faker faker;
    private DatabaseConnection databaseConnection = null;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet = null;
    CustomerDao customerDao = new CustomerDao();
    CustomerAddressesDao customerAddressesDao = new CustomerAddressesDao();


    List<CustomerAddresses> customerAddressesList = new ArrayList<>();
    List<Integer> randomCustomerIds = new ArrayList<>();
    List<Customers> customersList = new ArrayList<>();
    List<Integer> randomAddressIds = new ArrayList<>();

    public CustomerDbStepDefinitions() throws SQLException, IOException {
    }


    @BeforeEach
    public void setUp() {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @AfterEach
    public void closeConnection() {
        try {
            preparedStatement.close();
            resultSet.close();
            databaseConnection.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("Delete all records from {word} and {word} tables")
    public void deleteAllRecordsFromCustomersAndCustomerAddressesTable(String table ,String secondTable) {
        customerDao.deleteAll();
        customerAddressesDao.deleteAll();
    }

    @When("Create {int} new records in Customers and Customer_addresses tables")
    public void createNewRecordsInTables(int recordsNumber) {
        faker = new Faker();
        try {
            for (int i = 0; i < recordsNumber; i++) {
                CustomerAddresses customerAddresses = CustomerAddresses.builder()
                        .address(faker.address().streetAddress())
                        .city(faker.address().city())
                        .province(faker.bothify("no province"))
                        .state(faker.address().state())
                        .postal_code(faker.random().nextInt(1000))
                        .country(faker.address().country())
                        .build();
                customerAddressesDao.save(customerAddresses);
                int addressId = customerAddressesDao.getAddressId();

                Customers customer = Customers.builder()
                        .profile_name(faker.name().firstName())
                        .email(faker.bothify("????##@gmail.com"))
                        .phone(faker.numerify("##########"))
                        .age(faker.random().nextInt(18, 99))
                        .Gdpr_consent(faker.random().nextBoolean())
                        .Is_customer_profile_active(faker.random().nextBoolean())
                        .Profile_created_at(Timestamp.valueOf(LocalDateTime.now()))
                        .Profile_deactivated(Timestamp.valueOf(LocalDateTime.now()))
                        .reason_for_deactivation("No reason")
                        .notes("Some text")
                        .address_id(addressId)
                        .build();

                customerDao.save(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

  //          for (int i = 0; i < recordsNumber; i++) {
//                customerAddressesDao.save(Utils.createCustomerAddressWithFakeData());
//                customerDao.save(Utils.createCustomerWithFakeData());
  //          }
        }

    @Then("Verify tables row count is {int}")
    public void verifyTableAreNotEmpty(int rowsCount) {
       Assertions.assertEquals(customerDao.getRecordsCount(),rowsCount);
       Assertions.assertEquals(customerAddressesDao.getRecordsCount(),rowsCount);
    }

    @When("Create new Customer and save it")
    public void createNewCustomerAndSaveIt() {
        try {
            int addressId = customerAddressesDao.getAddressId();
            Customers customer = Customers.builder()
                    .profile_name(faker.name().firstName())
                    .email(faker.bothify("????##@gmail.com"))
                    .phone(faker.numerify("##########"))
                    .age(faker.random().nextInt(18, 99))
                    .Gdpr_consent(faker.random().nextBoolean())
                    .Is_customer_profile_active(faker.random().nextBoolean())
                    .Profile_created_at(Timestamp.valueOf(LocalDateTime.now()))
                    .Profile_deactivated(Timestamp.valueOf(LocalDateTime.now()))
                    .reason_for_deactivation("No reason")
                    .notes("Some text")
                    .address_id(addressId)
                    .build();

            customerDao.save(customer);
        } catch (Exception e) {
        e.printStackTrace();
    }
        }


    @Then("Verify customer count is {int}")
    public void verifyCustomerIsSaved(int count) {
        Assertions.assertEquals(customerDao.getRecordsCount(), count);
    }

    @When("Delete Customer")
    public void deleteCustomer() {
        int id = customerDao.getId();
        customerDao.deleteById(id);
    }

    @Then("Verify that customer count is {int}")
    public void verifyThatCustomerWasDeleted(int count) {
        Assertions.assertEquals(customerAddressesDao.getRecordsCount(), count);
    }


    @When("Get random Customers by random id's")
    public void getRandomCustomersByRandomIds() {
        randomCustomerIds = customerDao.getRandomIds(3);
        customersList = customerDao.getByIds(randomCustomerIds);
    }
    @Then("Verify customer fields and customers table count is {int}")
    public void verifyCustomerFieldsAndCustomersTableCount(int count) {
        for (Customers customer : customersList) {
            Assertions.assertTrue(!customer.getProfile_name().isEmpty() && !customer.getEmail().isEmpty()
            && !customer.getPhone().isEmpty());
        }
        Assertions.assertEquals(customerDao.getRecordsCount(), count);
    }

    @Then("Verify that all customers have address")
    public void verifyThatAllCustomersHaveAddress() {
        for (Customers customer : customersList) {
            Assertions.assertNotNull(customer.getAddress_id());
            Assertions.assertTrue(customer.getAddress_id() != 0);
        }
    }

    @When("Get random addresses by random id's")
    public void getRandomAddressesByRandomIds() {
      randomAddressIds = customerAddressesDao.getRandomIds(3);
      customerAddressesList = customerAddressesDao.getByIds(randomAddressIds);
    }
    @Then("Verify that all mandatory fields are with data")
    public void verify_that_all_mandatory_fields_are_with_data() {
        for (CustomerAddresses customerAddresses : customerAddressesList) {
            Assertions.assertTrue(!customerAddresses.getCity().isEmpty() && customerAddresses.getCity().isBlank());
            Assertions.assertTrue(!customerAddresses.getCountry().isEmpty() && customerAddresses.getCountry().isBlank());
            Assertions.assertNotNull(customerAddresses.getPostal_code());
        }
    }
}
