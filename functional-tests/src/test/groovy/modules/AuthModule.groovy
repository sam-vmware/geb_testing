package modules

import geb.Module
import pages.RootPage
/**
 * @author samueldoyle
 */
class AuthModule extends AppDirBaseModule {

    static content = {
        userRole(required: false) {
            $("span.menu-icon-selected-user").previous("span")
        }
        loginFormModule(required: false) { module LoginModule }
        logoutLink(requred: false, to: RootPage) {
            $("div.header-dropdown-menu").find("span", text: "Logout").parent("a")
        }
    }

    boolean isLoggedIn() {
        userRole
    }

    String getUserName() {
        userRole?.text()
    }

    void login(String username, String password) {
        if (loggedIn) throw new IllegalStateException("already logged in")
        loginFormModule.loginForm.j_username = username
        loginFormModule.loginForm.j_password = password
        loginFormModule.loginButton.click()
    }

    void logout() {
        if (!loggedIn) throw new IllegalStateException("already logged out")
        // First need to click the dropdown
        def userDropDown = $("#userDropdown").find("button")
        assert userDropDown : "Was not able to select #userDropdown button"
        userDropDown.click()
        // Then wait for the logout button to appear
        waitFor { $("div.header-dropdown-menu") }

        // Now the logout link is available
        logoutLink.click()
    }
}
