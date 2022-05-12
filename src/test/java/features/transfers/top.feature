@post/transfers
Feature: POST/transfers

  @tra1
  Scenario: Make a successful top transfer
    Given a payload:
      | source_account_id  | 952                              |
      | source_user_id     | auth0\|61a550cda70765006a15fa89  |
      | amount             | 5                                |
      | currency           | JMD                              |
      | comments           | top transfer                     |
      | transfer_type      | TOP                              |

    When perform a POST to transfers
    Then the status code is "201"
    And new transfer is in db