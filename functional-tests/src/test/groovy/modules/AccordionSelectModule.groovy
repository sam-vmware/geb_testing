package modules

/**
 * @author samueldoyle
 */
class AccordionSelectModule extends AppDirBaseModule {
    static content = {
        accordionItem { i -> module AccordionItemModule, $("div.vm-accordion-item", i) }
        size { $("div.vm-accordion-item").size() }
    }

    def findAccordionItemWithText(String searchText) {
        def returnItem
        for (i in 0..<size()) {
            if (accordionItem(i).checkBoxTitle() == searchText) {
                returnItem = accordionItem(i)
            }
        }
        returnItem
    }
}
