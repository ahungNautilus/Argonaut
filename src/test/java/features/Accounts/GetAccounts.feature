@Accounts
Feature:
  Smoke test for Account get endpoints

  Scenario: Verify the information from an existing account holder
    When I Search an user by the id number 1
    Then I should be able to see the response from the API

  Scenario: Verify the response of the service when the user search for a non existing account holder
    When I Search an user by the id number 100
    And the error description should be "Account not found in the database."
    And the error code should be "xxx-400"
    Then I should be able to see the status code 404

  Scenario: Create an account with valida data and verify database
    When I create a valid user with "JMD", "qa_test@nautilus.team" and "12345"
    Then new Account information should be on the database











