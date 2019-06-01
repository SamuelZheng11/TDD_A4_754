Narrative:
In order to control the number of non-developer users
as an application user
I want to add and delete non-developer users

Scenario: Scenario Add
Given a pull request
When a non-developer is added
Then the user is added to the database
