package modules

import groovy.util.logging.Slf4j
import org.openqa.selenium.StaleElementReferenceException
/**
 * @author samueldoyle
 */
@Slf4j
class DeploymentStatusModule extends AppDirBaseModule {
    enum DeploymentStatusEnum {
        DEPLOYMENT_SUCCESS("Deployment Success"),
        DEPLOYMENT_FAILED("Deployment Failed"),
        DEPLOYMENT_SCHEDULED("Deployment Scheduled"),
        DEPLOYMENT_IN_PROGRESS("Deployment In Progress"),
        TEARDOWN_SCHEDULED("Teardown Scheduled"),
        TEARDOWN_SUCCESS("Teardown Success")
        String msg

        DeploymentStatusEnum(String msg) {
            this.msg = msg
        }
    }

//    static base = { $("div.title-control-bar") }
    static content = {
        statusMsg { $("div.deployment-statusbar").find("div.status-msg") }
    }

    private def isSuccessful(DeploymentStatusEnum status, DeploymentStatusEnum failStatus = null,
                             retryPeriod = 60000 /* 1 minute */, int numberOfRetries = 30 /* 30 minutes */) {
        final def result = false, tryCounter = 0
        Thread.start {
            def statusText
            for (i in 0..<numberOfRetries) {
                tryCounter++
                try {
                    statusText = statusMsg.text()
                } catch (StaleElementReferenceException sere) {
                    log.error "StaleReferenceException, reinitialze"
                    continue
                } //swallow stale elements
                if (statusText == failStatus?.msg) {
                    log.error "Deployment failed, status text matched failure text to look for: ${failStatus.msg}"
                    break
                }
                if (statusText != status.msg) {
                    sleep retryPeriod
                    continue
                }

                result = true
                break
            }
        }.join()


        if (tryCounter == numberOfRetries) throw new RuntimeException("Maximum number of retries attempted!")
        result
    }

    def isDeploymentSuccessful() {
        isSuccessful(DeploymentStatusEnum.DEPLOYMENT_SUCCESS, DeploymentStatusEnum.DEPLOYMENT_FAILED)
    }

    def isTearDownSuccess() {
        isSuccessful(DeploymentStatusEnum.TEARDOWN_SUCCESS)
    }
}
