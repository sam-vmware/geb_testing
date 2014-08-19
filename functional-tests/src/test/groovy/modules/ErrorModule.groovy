package modules

import geb.Module

/**
 * @author samueldoyle
 */
class ErrorModule extends AppDirBaseModule {
    static base = { $("div .error") }
}
