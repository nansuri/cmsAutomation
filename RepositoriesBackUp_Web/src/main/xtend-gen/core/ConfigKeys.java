package core;

import core.PlatformPathSetup;
import java.io.FileInputStream;
import java.util.Properties;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.openqa.selenium.Platform;

@SuppressWarnings("all")
public class ConfigKeys {
  public final static String BROWSER_TYPE = "CHROME";
  
  public static String BASE_URL;
  
  public static String CHROME_NODEURL;
  
  public static String IE_NODEURL;
  
  public static String FF_NODEURL;
  
  public static Platform platform;
  
  public static String ANDROID_AUTOMATION_INSTRUMENTATION;
  
  public static String IOS_AUTOMATION_INSTRUMENTATION;
  
  /**
   * Set the properties to retrieve the configuration key values
   * @author Shameer
   */
  public static void setProperties() {
    Properties properties = new Properties();
    try {
      ConfigKeys.platform = PlatformPathSetup.getCurrentPlatform();
      String _reportFileLocation = PlatformPathSetup.getReportFileLocation(ConfigKeys.platform);
      FileInputStream _fileInputStream = new FileInputStream(_reportFileLocation);
      properties.load(_fileInputStream);
      ConfigKeys.BASE_URL = properties.getProperty("BASE_URL");
      ConfigKeys.CHROME_NODEURL = properties.getProperty("CHROME_NODEURL");
      ConfigKeys.IE_NODEURL = properties.getProperty("IE_NODEURL");
      ConfigKeys.FF_NODEURL = properties.getProperty("FF_NODEURL");
      ConfigKeys.ANDROID_AUTOMATION_INSTRUMENTATION = properties.getProperty("android_automationName");
      ConfigKeys.IOS_AUTOMATION_INSTRUMENTATION = properties.getProperty("ios_automationName");
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        System.exit(0);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
