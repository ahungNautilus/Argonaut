@Transfers
  Feature:
    Smoke test for Transfers endpoints

    Scenario: Verify the information from an existing transfer
      When I search for a transfer by id 64
      Then the response should have the source_user_id as "edikson.garcia@nautilus.team" and the status should be "PENDING"



