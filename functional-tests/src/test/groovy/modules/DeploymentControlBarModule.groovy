package modules

/**
 * @author samueldoyle
 */
class DeploymentControlBarModule extends AppDirBaseModule {
    static base = { $("div.control-bar") }
    static content = {
        tearDownButton { $("div.teardown-button") }
    }
}
