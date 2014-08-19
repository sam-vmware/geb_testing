package pages

import geb.Page
import modules.AuthModule
import modules.CardsContainerModule
import modules.CommonMessageBoxModule
import modules.DeploymentControlBarModule
import modules.DeploymentStatusModule
import modules.ErrorModule
import modules.QuickDeployDialogModule
import modules.SearchModule
import modules.XMaskModule

/**
 * @author samueldoyle
 */
class LandingPage extends AppDirBasePage {
    static url = "index.html"

    static at = { authModule.loggedIn }

    // TODO This page is basically all pages with content updated by extjs constantly updating the DOM
    // so figure out a way modularize this, it will probably grow quite large
    static content = {
        commonMessageBox { module CommonMessageBoxModule }
        authModule { module AuthModule }
        searchModule { module SearchModule }

        cardsContainer(required: false) { module CardsContainerModule, $("div.paged-card-container") }
        error(required: false) { module ErrorModule }
        bluePrintImage(required: false) { $("div.app-blueprint-image") }
        titleContainer(required: false) { $("div.title-content-container") }
        xMask(required: false) { module XMaskModule }
        quickDeployDialog(required: false) { module QuickDeployDialogModule }
        deploymentStatus(required: false) { module DeploymentStatusModule }
        deploymentControlBar(required: false) { module DeploymentControlBarModule }
    }

    void onLoad(Page previousPage) {
        super.onLoad(previousPage)
        if (authModule.userRole.text()) return
        waitFor { authModule.userRole.present }
    }
}
