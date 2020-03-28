package examples.testing;

import examples.testing.helpers.FileHelper;
import org.junit.jupiter.api.BeforeAll;

public class GeneralTest {


    protected static String testEnvUsr;
    protected static String testEnvPwd;

    @BeforeAll
    static void setupTest() {
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
    }

}
