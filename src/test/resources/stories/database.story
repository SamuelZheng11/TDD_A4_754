Narrative:
In order to control the number of non-developer users
as an application user
I want to add and delete non-developer users

Scenario: Add user to DB
Given a pull request
When a non-developer creates 2 code reviews
Then the user is added to the database
