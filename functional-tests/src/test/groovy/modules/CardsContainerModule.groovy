package modules

/**
 * @author samueldoyle
 */
class CardsContainerModule extends AppDirBaseModule {
    // context $(".paged-card-container")
    static content = {
        card { i -> module CardModule, $("div.card-container", i) }
        size { $("div.card-container").size() }
    }

    def findCardWithTitleText(String title) {
        def index
        for (i in 0..<size()) {
            if (card(i).titleText() == title) {
                index = i
                break
            }
        }
        index
    }
}
