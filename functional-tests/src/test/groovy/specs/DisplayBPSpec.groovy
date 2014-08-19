package specs

import pages.LandingPage
import pages.LoginPage
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Stepwise
import static utils.PropertyMappingEnum.*
/**
 * @author samueldoyle
 */
@Stepwise
class DisplayBPSpec extends AppDirBaseSpec {
    @Shared def targetCardLocation // index in the dom
    @Shared def targetCard // the dom card itself div.card-container, targetCardLocation
    @Shared def targetVersion // represents and application version displayed in title container eg jPetStore v1.0.0

    // TODO figure out a viable test reuse in this case since logging in is common in many cases
    def "Perform login"() {
        to LoginPage

        given: "I am at the login page"
        at LoginPage

        when: "I am logging in as test user"
        authModule.login testUserName, testUserPassword

        then: "I am being redirected to the landing page"
        at LandingPage

        and: "There is no error"
        !error.displayed

        and: "I'm logged in as test user"
        authModule.userName == testUserDisplayName
    }

    def "check to see we have all oob applications"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        expect: "I have all the cards"
        cardsContainer.size() == getConfigProperty(NumberOfApplications)
    }

    @Ignore
    def "find a target application based on name out of all the displayed cards"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        when: "We look for our test application on the page"
        targetCardLocation = cardsContainer.findCardWithTitleText(getConfigProperty(TargetApplication))

        then: "We have been provided with a valid location"
        targetCardLocation >= 0
    }

    @Ignore
    def "Check our card based on location"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        when: "I want to lookup the card based on index"
        targetCard = cardsContainer.card(targetCardLocation)

        then: "Our card based on index is correct"
        targetCard.titleText() == getConfigProperty(TargetApplication)
    }

    def "Search for target application"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        when: "We are showing the correct number of cards"
        cardsContainer.size() == getConfigProperty(NumberOfApplications)

        then: "We search for our test application"
        searchModule.search(getConfigProperty(TargetApplication))

        expect: "We only have one card showing now"
        waitFor { cardsContainer.size() == 1 }

        and: "This card matches our target search"
        cardsContainer.card(0).titleText() == getConfigProperty(TargetApplication)
    }

    def "Display target application versions"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        and: "This card matches our target search" // a reassertion from our previous test
        cardsContainer.card(0).titleText() == getConfigProperty(TargetApplication)

        when: "We select the card for the application"
        cardsContainer.card(0).click()

        then: "We are displaying the application page"
        waitFor { titleContainer.text() == getConfigProperty(TargetApplication) }
    }

    def "Display first deployment profile"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        and: "We have at least one version"
        cardsContainer.size() > 0

        when: "We grab the first version of the application and build the version string"
        targetVersion = "${getConfigProperty(TargetApplication)} v${cardsContainer.card(0).titleText()}"  // now the titletext contains the version

        then: "We select the card version to display the deployment profile"
        cardsContainer.card(0).click()

        expect: "Now the title content will be our application and version selected"
        waitFor { titleContainer.text() == targetVersion}
    }

    def "Display the blueprint"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        when: "We are still showing the deployment profile for our target version" // a reassertion from our previous test
        titleContainer.text() == targetVersion

        then: "We click the blueprint icon for it"
        bluePrintImage.click()

        expect: "We are now showing the blueprint for this application version"
        waitFor { titleContainer.text() == "$targetVersion - Blueprint" }
        waitFor { !xMask.displayed }
    }

    // TODO figure out a viable test reuse in this case since logging out is common in many cases
    def "do logout"() {
        given: "I am at the landing page"
        at LandingPage

        and: "No error is present"
        !error.displayed

        when: "I am logged in as administrator"
        authModule.userName == testUserDisplayName

        and: "I want to logout"
        authModule.logout()

        // some reason the confirmation dialog shows up at times
        then: "Dismiss confirmation dialog if present"
        try { commonMessageBox.clickNoCancel() } catch(all) {} // eat this

        expect: "I'm back at login page"
        at LoginPage

        and: "I'm not logged in"
        !authModule.loggedIn
    }

}
