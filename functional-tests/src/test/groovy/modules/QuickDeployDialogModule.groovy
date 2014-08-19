package modules

/**
 * @author samueldoyle
 */
class QuickDeployDialogModule extends AppDirBaseModule {
//    static base = { $("div.quick-deploy-dialog") }
    static content = {
        deployButton { $("div.quick-deploy-button") }
        deploymentProfileCombo { $("div.target-deployment-profile-select") }

        // hell knows where this ends up in the DOM with the extjs shenanigans so search whole body
        deploymentProfileSelectBox(required: false) { module AccordionSelectModule, $("div.deployment-profile-containers") }
    }

    def isVisible() {
        $("div.quick-deploy-dialog")
    }

    def clickDeployButton() {
        sleep(5000)
        deployButton.click()
    }
}
