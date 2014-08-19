def getProp = { val ->
    System.getProperty(val as String) ?: val
}

/**
 * @author samueldoyle
 */
tests {
    functional {
        values {
            user {
                userName = getProp "admin"
                userPassword = getProp "password"
                invalidUserPassword = getProp "iaminvalid"
                appDirDisplayName = getProp "AppDirector Administrator"
            }
            apps {
                total = getProp 8
                targetApp = getProp "Spring Travel"
                deploymentProfile = getProp "SPRING TRAVEL DP - IT"
            }
        }
    }
}
