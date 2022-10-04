
  @DbTest
  Feature: In this feature we will test Customers table in our database

    Background: Delete all records from Customers and Customer_addresses tables and create new records
      Given Delete all records from Customers and Customer_addresses tables
      When  Create 5 new records in Customers and Customer_addresses tables
      Then  Verify tables row count is 5

      Scenario: Create new Customer , save it and verify that it was saved successfully
        When Create new Customer and save it
        Then Verify customer count is 6

      Scenario: Delete a Customer and verify that the record is deleted
        When Delete Customer
        Then Verify that customer count is 5

      Scenario: Get random Customers by getting random customer id's and verify their mandatory fields are with data and verify the data count from Customers table
        When Get random Customers by random id's
        Then Verify customer fields and customers table count is 5

      Scenario: Get random customers by getting random customers id's and verify they have an address
        When Get random Customers by random id's
        Then Verify that all customers have address

      Scenario: Get random addresses by getting random id's and verify that they all mandatory fields are with data
        When Get random addresses by random id's
        Then Verify that all mandatory fields are with data