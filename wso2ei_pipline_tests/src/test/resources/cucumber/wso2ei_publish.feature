Feature: EI_Publish

  Scenario: Test publishing message
    Given Clean up db
    When Call soap service
    Then I wait 10 seconds
    Then Record exist in db