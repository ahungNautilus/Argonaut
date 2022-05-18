@post/transfers
Feature: POST/transfers

  @tra1
  Scenario: Make a successful top transfer
    Given a payload:
      | source_account_id     | 952                              |
      | source_user_id        | auth0\|61a550cda70765006a15fa89  |
      | amount                | 2                                |
      | currency              | JMD                              |
      | comments              | top transfer                     |
      | transfer_type         | TOP                              |
      | external_reference_id | 1766                             |

    When perform a POST to transfers
    Then verify that the status code it is "201"
    And all the data in the response body is correct and valid
    And new transfer is in db