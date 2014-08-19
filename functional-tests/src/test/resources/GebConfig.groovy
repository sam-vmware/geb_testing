/*
	Geb Configuration: http://www.gebish.org/manual/current/configuration.html
*/

import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.chrome.ChromeDriver

import java.util.logging.Level

baseUrl = "https://localhost:8443/darwin/"
reportsDir = "target/test-reports/geb"

// Use htmlunit as the default
// See: http://code.google.com/p/selenium/wiki/HtmlUnitDriver
driver = {
    def driver = new HtmlUnitDriver()
    driver.javascriptEnabled = true
    driver
}

waiting {
    timeout = 10
    retryInterval = 1
}

System.properties.with { p ->
  p["org.apache.commons.logging.Log"]="org.apache.commons.logging.impl.SimpleLog"
  p["org.apache.commons.logging.simplelog.log.org.apache.http"]="WARN"
}

environments {


    def driverCmdPath = "src/test/resources/drivers/chrome/"

    // run as “mvn -Dgeb.env=chrome test”
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    chrome {
        switch (System.properties["os.name"]) {
            case ~/^Mac.*$/:
                driverCmdPath += "mac/chromedriver"
                break
            case ~/^Win.*$/:
                driverCmdPath += "win\\chromedriver.exe"
                break
        }
        System.setProperty("webdriver.chrome.driver", driverCmdPath)
        driver = {
            def chromeDriver = new ChromeDriver()
//            chromeDriver.setLogLevel(Level.WARNING)
            chromeDriver
        }
    }

    // run as “mvn -Dgeb.env=firefox integration-test”
    // See: http://code.google.com/p/selenium/wiki/FirefoxDriver
   /* firefox {
        driver = { new FirefoxDriver() }
    }*/

}
