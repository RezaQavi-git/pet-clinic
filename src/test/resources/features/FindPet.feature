Feature: Finding pets

  Scenario: Finding pet that we add just right now
    Given One of owners is "mamad" from "mamadabad" with telephone number "22"
    And Owner "mamad" has a new pet with id 2
    Then We can find pet with Id 2 in pet service
