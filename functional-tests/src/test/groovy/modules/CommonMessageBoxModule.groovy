package modules

/**
 * @author samueldoyle
 */
class CommonMessageBoxModule extends AppDirBaseModule {
    static base = { $("div.common-message-box") }
    static content = {
        textField { $("span.ext-mb-text") }
        okYesButton { $("div:not(.is-last).btn-ok-yes") }
        noCancelButton { $("div:not(.is-last).btn-no-cancel") }
    }

    // present is really only viable if the required properties on the content is set to false
    // this may change so leave these convenience methods.
    def clickOkYes() {
        click(okYesButton)
    }

    def clickNoCancel() {
        click(noCancelButton)
    }

    def click(def elem) {
        if (elem.present) {
           /* def button = elem.find("input[type='button']")
            if (button) button.click()*/
            elem.click()
        }
        true
    }
}

