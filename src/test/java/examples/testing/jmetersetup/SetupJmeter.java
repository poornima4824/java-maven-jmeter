package examples.testing.jmetersetup;

import examples.testing.Constants;
import examples.testing.GeneralTest;
import examples.testing.abstraction.LoginPage;
import examples.testing.webdrivermanager.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

class SetupJmeter extends GeneralTest {

    /**
     * Retrieves credentials either by using maven properties (-DtestEnvUsr=admin), or by secrets file.
     * Starts a new Remote WebDriver, logs into the application and then saves the acquired cookies in a file
     */
    @BeforeAll
    static void setUpJmeterTest() {
        WebDriver driver = WebDriverManager.getRemoteChromeDriver();
        driver.get(Constants.applicationUrl);

        System.out.println(testEnvPwd);

        new LoginPage(driver).login(testEnvUsr, testEnvPwd);
        WebDriverManager.setCookiesInJmeterFormat(driver);

        //As soon as we have the cookies, we can close the webdriver session.
        driver.close();
        driver.quit();
    }

    /**
     * This is a placeholder test that is just there to trigger the setup scripts using the 'mvn verify' goal.
     */
    @Test
    void placeHolderTest() {
        System.out.println("Just here for placeholder purposes...");
        System.out.println("Cookies written to target folder file to be used in JMeter test...");
    }
}
