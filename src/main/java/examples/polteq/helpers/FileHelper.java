package examples.polteq.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileHelper {

    private static Properties props = new Properties();

    /**
     * Enables all paths to be written in linux/mac style using '/' for file separators.
     *
     * @param linuxPath
     * @return
     */
    public static String filePathMaker(String linuxPath) {
        if (OSHelper.isWindows()) return linuxPath.replaceAll("/", File.separator + File.separator);
        else return linuxPath;
    }

    /**
     * Reads all properties from secret file and stores them in field.
     *
     * @return
     */
    private static void readProperties() {
        if (props.getProperty("test.usr") == null) {
            try (InputStream input = new FileInputStream(filePathMaker("secrets/secrets.properties"))) {

                // load a properties file
                props.load(input);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @param propertyName name of the property you want to retrieve.
     * @return specified property
     */
    public static String getProperty(String propertyName) {
        readProperties();
        return props.getProperty(propertyName);
    }


}
