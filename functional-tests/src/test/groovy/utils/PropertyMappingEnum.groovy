package utils

/**
 * @author samueldoyle
 */

public enum PropertyMappingEnum {

    LoginUserName("tests.functional.values.user.userName"),
    LoginUserPassword("tests.functional.values.user.userPassword"),
    LoginUserInvalidPassword("tests.functional.values.user.invalidUserPassword"),
    DisplayName("tests.functional.values.user.appDirDisplayName"),
    NumberOfApplications("tests.functional.values.apps.total"),
    TargetApplication("tests.functional.values.apps.targetApp"),
    TargetApplicationDP("tests.functional.values.apps.deploymentProfile")

    def final propertyPath

    PropertyMappingEnum(propertyPath) {
        this.propertyPath = propertyPath
    }
}

