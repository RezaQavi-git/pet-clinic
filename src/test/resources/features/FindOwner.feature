Feature: Finding owners

  Scenario: Can find name in pet service
    Given One of owners is "Ali" from "Anzali" with telephone number "09102228978"
    When We are finding owner with "Ali" name
    Then We found owner with "Ali" name

  Scenario: Can not find name in pet service
    Given One of owners is "Gholi" from "Anzali" with telephone number "09102228978"
    When We are finding owner with "Gholi" name
    Then We did not found owner with "Ali" name
