package pages

import geb.Page
import groovy.util.logging.Log

/**
 * @author samueldoyle
 */
@Log
class AppDirBasePage extends Page {

    void onLoad(Page previousPage) {
        log.info("Loading page: $previousPage")
    }

    void onUnload(Page newPage) {
        log.info("Unloading page: $newPage")
    }
}
