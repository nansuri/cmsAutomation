package core;

import org.openqa.selenium.Platform;

@SuppressWarnings("all")
public class PlatformPathSetup {
  public static Platform platform;
  
  public static String macPath = "./src/main/resources/config/config.properties";
  
  public static String windowsPath = ".\\src\\main\\resources\\config\\config.properties";
  
  /**
   * Get current platform/OS in order to set the 'config properties' path
   * @author Shameer
   */
  public static Platform getCurrentPlatform() {
    if ((PlatformPathSetup.platform == null)) {
      String operSys = System.getProperty("os.name").toLowerCase();
      boolean _contains = operSys.contains("win");
      if (_contains) {
        PlatformPathSetup.platform = Platform.WINDOWS;
      } else {
        if (((operSys.contains("nix") || operSys.contains("nux")) || operSys.contains("aix"))) {
          PlatformPathSetup.platform = Platform.LINUX;
        } else {
          boolean _contains_1 = operSys.contains("mac");
          if (_contains_1) {
            PlatformPathSetup.platform = Platform.MAC;
          }
        }
      }
    }
    return PlatformPathSetup.platform;
  }
  
  /**
   * Select the 'config properties' location based on platform
   * @param platform Current platform name
   * @author Shameer
   */
  public static String getReportFileLocation(final Platform platform) {
    String reportFileLocation = null;
    if (platform != null) {
      switch (platform) {
        case MAC:
          reportFileLocation = PlatformPathSetup.macPath;
          System.out.println((("Config Properties file Path for MAC: " + PlatformPathSetup.macPath) + "\n"));
          break;
        case WINDOWS:
          reportFileLocation = PlatformPathSetup.windowsPath;
          System.out.println((("Config Properties file Path for WINDOWS: " + PlatformPathSetup.windowsPath) + "\n"));
          break;
        default:
          System.out.println("Config Properties file Path has not been set! There is a problem!\n");
          break;
      }
    } else {
      System.out.println("Config Properties file Path has not been set! There is a problem!\n");
    }
    return reportFileLocation;
  }
}
