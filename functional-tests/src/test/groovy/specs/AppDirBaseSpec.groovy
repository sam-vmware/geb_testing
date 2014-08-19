package specs

import geb.spock.GebReportingSpec
import pages.LandingPage
import pages.LoginPage
import spock.lang.Shared
import utils.BasicTestUtils
import utils.PropertyMappingEnum

/**
 * @author samueldoyle
 */
class AppDirBaseSpec extends GebReportingSpec {
    @Shared def utils = BasicTestUtils.instance
    @Shared def testUserName, testUserPassword, testUserDisplayName

    def setupSpec() {
        (testUserName, testUserPassword, testUserDisplayName) =
            PropertyMappingEnum.with {
                [LoginUserName, LoginUserPassword, DisplayName].collect {
                    BasicTestUtils.instance.getConfigProperty(it)
                }
            }
    }

    def getConfigProperty(PropertyMappingEnum p) {
        utils.getConfigProperty(p)
    }

    def cleanupSpec() {
    }
}
