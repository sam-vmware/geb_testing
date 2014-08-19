package modules

/**
 * @author samueldoyle
 */
class CardModule extends AppDirBaseModule {
    // context $(div.card-container, i)
    static content = {
        titleText { $("div.card-title-text").text() }
        id { $().@id }
    }

    void clickQuickDeploy() {
        // Fix this when there is a better way to identify the quick deploy button
        def quickDeployButton = $(".deploy-action-white-small").parent()
        assert quickDeployButton.@type == "button" : "Quick deploy target is not a button."

        quickDeployButton.click()
        sleep(5000)
    }
}
