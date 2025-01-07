@UserRegistrationSteps
Feature: User Registration

  Scenario Outline: User registration with different passwords

    #    Fail Scenarios

    # Invalid Password Scenarios
    Given the user tries to register with following data
      | username   | firstName   | lastName   | password   | birthDate   |
      | <username> | <firstName> | <lastName> | <password> | <birthDate> |
    Then the user should see the error message <errorMessage>

    Examples:
      | username  | firstName | lastName | password      | birthDate  | errorMessage                                 |
      | testuser1 | mehdi     | lName    | testpassword  | 1625641234 | "Password can not contain the word password" |
      | testuser2 | mana      | lName    | passmana      | 1625641234 | "Password can not contain the First name"    |
      | testuser3 | hadi      | lName    | testlName     | 1625641234 | "Password can not contain the Last name"     |
      | testuser4 | mojtaba   | lName    | passtestuser4 | 1625641234 | "Password can not contain username"          |

