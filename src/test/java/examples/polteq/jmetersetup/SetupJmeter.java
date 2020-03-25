package examples.polteq.jmetersetup;

import examples.polteq.Constants;
import examples.polteq.abstraction.LoginPage;
import examples.polteq.helpers.FileHelper;
import examples.polteq.webdrivermanager.WebDriverManager;
import io.restassured.http.Cookies;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

class SetupJmeter {

    //LOGIN URLS
    protected static String testEnvUsr;
    protected static String testEnvPwd;
    private static Cookies restCookies;


    /**
     * Retrieves credentials either by using maven properties (-DtestEnvUsr=admin), or by secrets file.
     * Starts a new Remote WebDriver, logs into the application and then saves the acquired cookies in a file
     */
    @BeforeAll
    static void setUpJmeterTest() {

        //First attempt to retrieve maven properties
        testEnvUsr = System.getProperty("testEnvUsr");
        testEnvPwd = System.getProperty("testEnvPwd");

        /*
            If no maven properties are passed in the command (i.e. local run),
            set test environment username and pass from secrets file.
         */
        if (testEnvUsr == null) {
            testEnvUsr = FileHelper.getProperty("test.usr");
            testEnvPwd = FileHelper.getProperty("test.pwd");
        }

        WebDriver driver = WebDriverManager.getRemoteChromeDriver();
        driver.get(Constants.applicationUrl);

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
