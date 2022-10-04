package helpers;

import dao.CustomerAddressesDao;
import pojos.CustomerAddresses;
import pojos.Customers;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import pojos.Orders;
import pojos.ProductsInventory;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Utils {

    /**
     * This method create new Customer with fake data using JavaFaker
     * @return
     */
    public static Customers createCustomerWithFakeData() {
            Faker faker = new Faker();
            FakeValuesService fakeValuesService = new FakeValuesService(
                    new Locale("en-GB"), new RandomService());
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
                    .address_id(1)
                    .build();
        System.out.println(customer);
        return customer;
    }

    /**
     * This method create a list of Customers with fake data using JavaFaker
     * @return
     */
    public static List<Customers> createListOfCustomers(){
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        ArrayList<Customers> customers = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            customers.add(Customers.builder()
                    .id(faker.number().randomDigit())
                    .profile_name(faker.name().firstName())
                    .email(faker.bothify("????##@gmail.com"))
                    .phone(faker.numerify("##########"))
                    .age(faker.random().nextInt(18,99))
                    .Gdpr_consent(faker.random().nextBoolean())
                    .Is_customer_profile_active(faker.random().nextBoolean())
                    .Profile_created_at(Timestamp.valueOf(LocalDateTime.now()))
                    .Profile_deactivated(Timestamp.valueOf(LocalDateTime.now()))
                    .reason_for_deactivation("No reason")
                    .notes("Some text")
                    .address_id(1)
                    .build());
        }
        System.out.println(customers);
          return customers;
    }

    /**
     * This method create customer address with fake data using JavaFaker
     * @return
     */
    public static CustomerAddresses createCustomerAddressWithFakeData() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        CustomerAddresses customerAddresses = CustomerAddresses.builder()
                        .address(faker.address().streetAddress())
                                .city(faker.address().city())
                                        .province(faker.bothify("no province"))
                                                .state(faker.address().state())
                                                        .postal_code(faker.random().nextInt(1000))
                                                                .country(faker.address().country())
                                                                        .build();
        System.out.println(customerAddresses);
        return customerAddresses;
    }

    /**
     * This method create Product with fake data using JavaFaker
     * @return
     */
    public static ProductsInventory createProductWithFakeData() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        ProductsInventory productsInventory = ProductsInventory.builder()
                .id(25)
                .product_name("prod20")
                .quantity(faker.random().nextInt(2))
                .product_type("car")
                .price_without_vat(50)
                .price_with_vat(60)
                .is_product_in_stock(faker.random().nextBoolean())
                .warehouse(faker.address().city())
                .supplier_id(faker.random().nextInt(3))
                .build();
        System.out.println(productsInventory);
        return productsInventory;
    }

    /**
     * This method create order with fake data using JavaFaker
     * @return
     */

    public static Orders createOrderWithFakeData() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        Orders order = Orders.builder()
                .id(25)
                .customer_id(2)
                .is_order_completed(faker.random().nextBoolean())
                .is_order_payed(faker.random().nextBoolean())
                .date_of_order(Timestamp.valueOf(LocalDateTime.now()))
                .date_order_completed(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        System.out.println(order);
        return order;
    }

}
