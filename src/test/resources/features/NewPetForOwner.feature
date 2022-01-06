Feature: Add new pet for owner

  Scenario: New pet will add to owner's pets
    Given One of owners is "Alireza" from "Tehran" with telephone number "9990"
    And Owner "Alireza" has a new pet with id 2
    Then New pet is now owner "Alireza"'s pet
