package core

import java.io.FileInputStream
import java.util.Properties
import org.openqa.selenium.Platform

class ConfigKeys {

	val public static final String BROWSER_TYPE = "CHROME";

	public static String BASE_URL;
	public static String CHROME_NODEURL
	public static String IE_NODEURL
	public static String FF_NODEURL
	public static Platform platform;
	public static String ANDROID_AUTOMATION_INSTRUMENTATION
	public static String IOS_AUTOMATION_INSTRUMENTATION

	/**
	 * Set the properties to retrieve the configuration key values
	 * @author Shameer
	 */
	def static void setProperties() {
		var Properties properties = new Properties();
		try {
			platform = PlatformPathSetup.getCurrentPlatform()
			properties.load(new FileInputStream(PlatformPathSetup.getReportFileLocation(platform)));
			BASE_URL = properties.getProperty("BASE_URL");
			CHROME_NODEURL = properties.getProperty("CHROME_NODEURL");
			IE_NODEURL = properties.getProperty("IE_NODEURL");
			FF_NODEURL = properties.getProperty("FF_NODEURL");
			ANDROID_AUTOMATION_INSTRUMENTATION = properties.getProperty("android_automationName");
			IOS_AUTOMATION_INSTRUMENTATION = properties.getProperty("ios_automationName");
		} catch (Exception ex) {
			System.exit(0);
		}
	}
}
