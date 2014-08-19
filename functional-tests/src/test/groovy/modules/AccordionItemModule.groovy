package modules

/**
 * @author samueldoyle
 */
class AccordionItemModule extends AppDirBaseModule {
    // content $("div.vm-accordion-item", i)
    static content = {
        checkBoxWrapper { $("div.checkbox-input") }
        checkBoxTitleWrapper { $("div.checkbox-title") }
    }

    def checkBoxTitle() {
        checkBoxTitleWrapper.text()
    }

    def select() {
        def checkBoxButton = checkBoxWrapper.find("input[type='button']")
        assert checkBoxButton : "Wasn't able to locate the checkbox button to select"
        checkBoxButton.click()
    }
}
