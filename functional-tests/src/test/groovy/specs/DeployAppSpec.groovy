package specs

import pages.LandingPage
import pages.LoginPage
import spock.lang.Shared
import spock.lang.Stepwise

import static utils.PropertyMappingEnum.*
import static modules.DeploymentStatusModule.DeploymentStatusEnum.*

/**
 * @author samueldoyle
 */
@Stepwise
class DeployAppSpec extends AppDirBaseSpec {
    @Shared def targetProfileSelectItem

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

    def "Launch quick deploy dialog"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        and: "The first card matches our target search" // a reassertion from our previous test
        cardsContainer.card(0).titleText() == getConfigProperty(TargetApplication)

        when: "We want to do a quick deploy"
        cardsContainer.card(0).clickQuickDeploy()

        then: "Make sure we have quick deploy dialog present"
        waitFor { quickDeployDialog.displayed }
    }

    def "Select deployment profile from quick deploy dialog launches profile selection dialog"() {
        given: "My quick deployment is open"
        quickDeployDialog.displayed

        and: "There are no errors."
        !error.displayed

        when: "We select our quick deployment"
        quickDeployDialog.deploymentProfileCombo.click()

        then: "We have our deployment profile combo box"
        waitFor { quickDeployDialog.deploymentProfileSelectBox.displayed }
    }

    def "Find our deployployment profile for quick deployment"() {
        given: "The deployment profile quick deployment combobox selection is open"
        quickDeployDialog.deploymentProfileSelectBox.displayed

        and: "There are no errors."
        !error.displayed

        when: "We want to find our deployment profile to quick deploy with"
        targetProfileSelectItem =
            quickDeployDialog.deploymentProfileSelectBox.findAccordionItemWithText(getConfigProperty(TargetApplicationDP))

        then: "We got something back"
        targetProfileSelectItem

        and: "It is a match"
        targetProfileSelectItem.checkBoxTitle() == getConfigProperty(TargetApplicationDP)
    }

    def "Select out target deployment profile for quick deployment"() {
        given: "We have our target quick deployment profile"
        targetProfileSelectItem

        and: "It is still a match"
        targetProfileSelectItem.checkBoxTitle() == getConfigProperty(TargetApplicationDP)

        and: "There are no errors."
        !error.displayed

        when: "We want to select the checkbox for this profile"
        targetProfileSelectItem.select()

        then: "Our profile selection dialog goes away"
        waitFor { !quickDeployDialog.deploymentProfileSelectBox.displayed }
    }

    def "Start the quick deployment"() {
        given: "The deployment profile quick deployment combobox selection is closed"
        !quickDeployDialog.deploymentProfileSelectBox.displayed

        and: "The quick deployment dialog is still open"
        quickDeployDialog.displayed

        when: "We want to start the quick deployment"
        quickDeployDialog.clickDeployButton()

        then: "The quick deployment dialog goes away"
        waitFor(120) { !quickDeployDialog.isVisible() } // wait for up to two minutes for the display to go away
    }

    def "Wait for successful deployment"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        when: "There is a deployment in progress"
        deploymentStatus.statusMsg.text() in [DEPLOYMENT_SCHEDULED.msg, DEPLOYMENT_IN_PROGRESS.msg]

        then: "Wait for successful completion"
        deploymentStatus.isDeploymentSuccessful()
    }
    def "Tear down deployment"() {
        given: "I am at the landing page"
        at LandingPage

        and: "There are no errors."
        !error.displayed

        and: "The deployment was successful"
        deploymentStatus.statusMsg.text() == DEPLOYMENT_SUCCESS.msg

        when: "We trigger a teardown"
        waitFor { deploymentControlBar.tearDownButton }.click()

        then: "We click ok for confirmation"
        waitFor { commonMessageBox }.clickOkYes()

        expect: "A successful teardown"
        deploymentStatus.isTearDownSuccess()
    }

   /* def "teardown"() {
        given: "I am at the landing page"
        at LandingPage

        and: "select deployments"
        waitFor { $(".header-dropdown") }.click()
        waitFor { $("#menuitem-1038").find("a") }.click()

        and: "select first card"
        waitFor { cardsContainer.card(0)}.click()

        when: "We trigger a teardown"
        deploymentControlBar.tearDownButton().click()

        then: "We see a confirm dialog and select ok"
        waitFor { commonMessageBox }.clickOkYes()

        expect: "A successful teardown"
        deploymentStatus.isTearDownSuccess()
    }*/
}
