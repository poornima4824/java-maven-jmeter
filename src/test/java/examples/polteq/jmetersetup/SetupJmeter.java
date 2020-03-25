package examples.polteq.jmetersetup;

import examples.polteq.Constants;
import examples.polteq.abstraction.LoginPage;
import examples.polteq.helpers.FileHelper;
import examples.polteq.webdrivermanager.WebDriverManager;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class SetupJmeter {

    //LOGIN URLS
    protected static String testEnvUsr;
    protected static String testEnvPwd;
    private static Cookies restCookies;


    /**
     * Retrieves credentials either by using maven properties (-DtestEnvUsr=admin), or by secrets file.
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

        getCookiesViaRemoteWebDriver();
        setCookiesInJmeterFormat();
    }

    /**
     * Starts a new Remote WebDriver, logs into the application and then saves the acquired cookies in a file
     */
    protected static void getCookiesViaRemoteWebDriver() {
        if (restCookies == null) {
            //If test environment, then log in. In case of PROD iDAM is used to log in.
            WebDriver driver = WebDriverManager.getRemoteChromeDriver();
            driver.get(Constants.applicationUrl);
            new LoginPage(driver).login(testEnvUsr, testEnvPwd);
            restCookies = WebDriverManager.getCookies(driver);
            driver.quit();

        }
    }


    /**
     * Writes a Cookies.data file in project main dir.
     */
    public static void setCookiesInJmeterFormat() {
        // create file named Cookies to store Login Information
        File file = new File("target/cookies.txt");
        try {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);

            // loop for getting the cookie information
            for (Cookie ck : restCookies) {
                String cookieString = ck.getDomain() + "\t true \t" + ck.getPath() + "\t" + ck.isSecured() + "\t" +
                        ck.getExpiryDate().getTime() + "\t" + ck.getName() + "\t" + ck.getValue();
                cookieString = cookieString.replaceAll("null", "").trim();
                Bwrite.write(cookieString);
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
