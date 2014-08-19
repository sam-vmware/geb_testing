---
This code was used with an earlier AppD 5x build to demonstrate using [Geb](http://www.gebish.org/) to perform functional testing. This particular PoC performed
* Navigation to AppD landing page
* Logging in
* Searching for an application
* Perform deployment using quick deploy
* Wait for success or error
* Teardown afterwards

Geb uses the [BDD](http://en.wikipedia.org/wiki/Behavior-driven_development) approach (which itself is based on TDD) for testing which has become popular. Instead of explaining it, you can view [DeployAppSpec](functional-tests/src/test/groovy/specs/DeployAppSpec.groovy) to see how it works. This specification performs the steps mentioned previously all the while performing assertions to make sure things go as expected.

To make it clearer look at these lines:

```groovy
 def "Perform login"() {
        to LoginPage

```
This is using a Selenium [PageObject](https://code.google.com/p/selenium/wiki/PageObjects) but obviously much less verbose compared to the standard Java implementation. When this step is performed we are driven to the login page via the [LoginPage](functional-tests/src/test/groovy/pages/LoginPage.groovy) PageObject. These lines
```groovy
static content = {
        authModule { module AuthModule }
        loginFormModule { module LoginModule }

        error(required: false) { module ErrorModule }
    }
```
are part of the Geb [Content DSL](http://www.gebish.org/manual/current/pages.html#the_content_dsl) that allow us to define content for the PageObject which also in turn can use other modules. Here we make use of the authModule.
```groovy
def "Perform login"() {
        to LoginPage

        given: "I am at the login page"
        at LoginPage

        when: "I am logging in as test user"
        authModule.login testUserName, testUserPassword
```
