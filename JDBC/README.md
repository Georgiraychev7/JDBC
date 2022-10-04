# JDBC

# JDBC : Project setup

#
Theory:

Java DataBase Connectivity (JDBC)

JDBC drivers

Properties file

Factory and Singleton design patterns

Java try with resources

Practical tasks:

Create automation project

Description: Create automation project using Java 11 and Maven. Find and add the appropriate JDBC driver that:

is compatible with the target DB

is compatible with Java 11

Create PropertiesHelper and properties file

Description: Create read-only PropertiesHelper class that can retrieve different properties from a configuration file.

Create a config.properties file containing all the data connection configuration (username, password, URL, etc). Create a PropertiesHelper class that can read properties from that configuration file.

Create database connection that is then automatically closed in a Try with resources block

Description: create a database helper class with a method that creates and returns a new database connection. This connection then can be automatically closed by try with resources block.

Create a database connection before tests start and close it after last test is executed

Description: create a database helper class with a method that creates and returns a single database connection. New connection is created only if the old one gets closed and is not alive anymore. Connection is created before all tests and closed at the end of the test execution.

Acceptance criteria:

As a QA Automation trainee, I want to gain knowledge of:

how to create java projects that use JDBC drivers

how to establish database connections

how to use and implement Singleton and Factory design patterns

how to create and use configuration files

# JDBC: POJOs with Lombok and generating test data with JavaFaker

Theory:

POJO files

Lombok

Builder pattern

Generating test data using JavaFaker

Practical tasks:

Create Customer POJO

Description: Using Lombok, create a POJO class for Customers table. Implement the Builder pattern using Lombok’s Builder annotation.

Create Customer example data 

Description: Create a Helper class that creates Customer class objects with random data using JavaFaker:

create a single object with random data

a list of X objects with random data

Acceptance criteria:

As a QA Automation trainee, I want to gain knowledge of:

how to create POJO classes that are aligned with the Java naming conventions.

how to simplify my code and produce code faster using Lombok

how to implement builder pattern with Lombok 

how to quickly create test data using JavaFaker

# JDBC: POJOs and DAOs to save, update and delete entries

Theory:

Data Access Object (DAO) Pattern

Java Generics

Practical tasks:

Create a DAO Interface

Description:  create an interface that the Customer DAO class will implement. The following methods should be included:

save() - saves the record to the database

update() - updates the record in the database

delete() - deletes the record from the database

deleteAll() - deletes all records in the table

getRandomId() - returns a random record id

getRandomIds(X) - returns a list of X random records' id

getRecordsCount() - get the count of all records in the table

getByID() - to extract a single object from the database by ID

getByIDs() - to extract a list of objects from the database by a List of IDs

The getByID() and getByIDs() methods will be implemented in the next stories.

Create Customer DAO

Description: Create a DAO for the customers table that implements the CRUD DAO interface. 

You can store your SQL queries in an interface and use them with String.format() to update them with the POJO data. 

Acceptance criteria:

As a QA Automation trainee, I want to gain knowledge of:

how and why to use DAO pattern 

how to use Java Generics

how to save new and update existing records in a DB

how to delete records from a table

# JDBC: Mapping DB data to POJOs using ResultSet

Theory:

JDBC ResultSet

Practical tasks:

Extend Customer DAO to load customer data in an object using ResultSet

Description: implement getByID() and getByIDs() methods in Customer DAO using Result set to map the data into the class object.

Acceptance criteria:

As a QA Automation trainee, I want to gain knowledge of:

how to retrieve data from a database and map it to POJO classes using ResultSets

how to handle name mismatch between the POJO variables and the table column names

what is a JDBC result set

# JDBC: Mapping DB data to POJOs using ResultSetMapper and class annotations

Theory:

JDBC ResultSetMapper with Annotations

Basic Java Reflection

Practical tasks:

Extend Customer DAO to load customer data in an object with ResultSetMapper

Description: implement getByID() and getByIDs() methods in Customer DAO using ResultSetMapper to map the data into the class object.

This approach is great for testing database migrations.

Acceptance criteria:

As a QA Automation trainee, I want to gain knowledge of:

how to retrieve data from a database and map it to POJO classes using ResultSetMapper

how to handle name mismatch between the POJO variables and the table column names

what is a JDBC ResultSetMapper with Annotations

Basic usage of Java Reflection

# JDBC: Mapping DB data to POJOs using Apache DBUtils

Theory:

JDBC Apache DBUtils

Practical tasks:

Extend Customer DAO to load customer data in an object with Apache DBUtils

Description: implement getByID() and getByIDs() methods in Customer DAO using ResultSetMapper to map the data into the class object.

Acceptance criteria:

As a QA Automation trainee, I want to gain knowledge of:

how to retrieve data from a database and map it to POJO using DBUtils

how to handle name mismatch between the POJO variables and the table column names

what is Apache DBUtils and how to load data into Java objects using it

# JDBC: Extracting relational data

Theory:

How to extract relational data using JDBC

Practical tasks:

Get the customer’s address and orders with the products

Description:  Extract the customer’s address and all of the customer’s orders with the ordered products.

To be able to do this, create an Address, Orders, and Product POJOs and DAOs. The Customer class should hold the Address and Orders Objects, Orders class should hold the Products objects.

Extract the data using Apache DBUtils Database Helper. Create a DataBase Manager Helper class if needed to extract all common database actions.

Acceptance criteria:

As a QA Automation trainee, I want to gain knowledge of:

how to extract relational data (one-to-one, one-to-many, and many-to-many)

# JDBC: Database Tests with Cucumber

Practical tasks:

Background: Create test data before test execution and verify the table is not empty

Description: Before the tests are executed I want to drop all the data in the target table and create X new records in it. 

Relations should exist for the created test data - customers should have an address.

After that, verify that the target table is not empty / contains X amount of records.

Create tests for Customer data

Description: Create tests that validate the data in the customer's table. Verify:

create a new record, save it and verify that it was saved successfully, verify there are more entries in the table

delete a record, and verify that it doesn’t exist in the database and there are fewer entries in the table.

get X random customers by getting X random customer IDs and verify that their mandatory fields are with data

verify the data count for the customers table

Create tests for Customer Addresses

get X random customers by getting X random customer IDs and verify they have an address. 

get X random addresses by getting X random IDs and verify that they have all mandatory fields with data

Acceptance criteria:

As a QA Automation trainee, I want to gain knowledge of:

how to write tests for relational data using Cucumber and JDBC

As a QA Automation trainee, I want to be able to:

run the tests through the Maven command line command

to run different test suites
