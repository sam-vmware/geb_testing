package modules

/**
 * @author samueldoyle
 */
class SearchModule extends AppDirBaseModule {
    static content = {
        searchField { $("input", placeholder: "Search") }
        xMask(required: false) { module XMaskModule }
    }

    void search(String targetSearchText) {
        searchField << targetSearchText
        waitFor { ! xMask.displayed }
    }
}
