Feature:
  Smoke test for Account get endpoints

  Scenario: Verify account holder information by their id
    When I Search an user by the id number 1
    Then I should be able to see the response from the API


