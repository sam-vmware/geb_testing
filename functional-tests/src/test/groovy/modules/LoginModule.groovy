package modules

import geb.Module

/**
 * @author samueldoyle
 */
class LoginModule extends AppDirBaseModule {
    static content = {
        loginForm { $("form") }
        loginButton { $("button[type=submit]", value: "Login") }
    }
}
