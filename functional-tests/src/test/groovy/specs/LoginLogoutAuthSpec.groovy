package specs

import pages.LandingPage
import pages.LoginPage
import spock.lang.Ignore
import spock.lang.Stepwise
import static utils.PropertyMappingEnum.*
/**
 * @author samueldoyle
 */
@Stepwise
class LoginLogoutAuthSpec extends AppDirBaseSpec {

    def "invalid login"() {
        to LoginPage

        given: "I am at the login page"
        at LoginPage

        and: "No error is present"
        !error.displayed

        when: "I am logging in with invalid credentials"
        authModule.login testUserName, getConfigProperty(LoginUserInvalidPassword)

        then: "I am being redirected to the login page"
        at LoginPage

        and: "There is an error"
        error.displayed

        and: "I'm not logged in"
        !authModule.loggedIn
    }

    def "valid login"() {
        to LoginPage

        given: "I am at the login page"
        at LoginPage

        when: "I am logging in as test user"
        authModule.login "admin", "password"

        then: "I am being redirected to the landing page"
        at LandingPage

        and: "There is no error"
        !error.displayed

        and: "I'm logged in as test user"
        authModule.userName == testUserDisplayName
    }

    def "do logout"() {
        given: "I am at the landing page"
        at LandingPage

        and: "No error is present"
        !error.displayed

        when: "I am logged in as administrator"
        authModule.userName == testUserDisplayName

        then: "I want to logout"
        authModule.logout()

        expect: "I'm back at login page"
        at LoginPage

        and: "I'm not logged in"
        !authModule.loggedIn
    }
}
