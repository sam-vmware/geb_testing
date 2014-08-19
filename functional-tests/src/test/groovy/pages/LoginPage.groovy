package pages

import modules.AuthModule
import modules.ErrorModule
import modules.LoginModule

/**
 * @author samueldoyle
 */
class LoginPage extends AppDirBasePage {
    static url = "login/login.jsp"

    static at = { waitFor {title == "VMware AppDirector Login"} }

    static content = {
        authModule { module AuthModule }
        loginFormModule { module LoginModule }

        error(required: false) { module ErrorModule }
    }
}
