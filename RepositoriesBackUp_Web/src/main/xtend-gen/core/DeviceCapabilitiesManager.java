package core;

import core.ConfigKeys;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import testBase.BaseTest;
import util.AutomationUtil;

@SuppressWarnings("all")
public class DeviceCapabilitiesManager {
  /**
   * Set the desktop web application's desired capabilities
   * @author Shameer
   */
  public DesiredCapabilities getChromeOptions() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--start-maximized");
    options.addArguments("--ignore-certificate-errors");
    options.addArguments("--disable-popup-blocking");
    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    capabilities.setBrowserName("chrome");
    capabilities.setCapability("ignoreZoomSetting", true);
    capabilities.setPlatform(Platform.WIN10);
    capabilities.setVersion("ANY");
    capabilities.setCapability("nativeEvents", false);
    return capabilities;
  }
  
  /**
   * Set the Android native application's desired capabilities
   * @param deviceID Device Id of the android phone
   * @param appPath Application's Path of .apk file
   * @param appActivity The application's activity name
   * @param appPackage The application's package name
   * 
   * @author Shameer
   */
  public DesiredCapabilities getAndroidNativeOptions(final String deviceID, final String appPath, final String appActivity, final String appPackage, final String appWaitActivity) {
    try {
      DesiredCapabilities capabilities = DesiredCapabilities.android();
      capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
      capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
      capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ("Android_" + deviceID));
      capabilities.setCapability(MobileCapabilityType.APP, appPath);
      capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
      capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
      capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, appWaitActivity);
      capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
      capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
      capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
      capabilities.setCapability(MobileCapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
      capabilities.setCapability("unicodeKeyboard", true);
      capabilities.setCapability("resetKeyboard", true);
      capabilities.setCapability("newCommandTimeout", Integer.valueOf(70000));
      new BaseTest().createScreenShotFolder(new BaseTest().getScreenShotFolderLocation(new BaseTest().getCurrentPlatform()));
      String _screenShotFolderLocation = new BaseTest().getScreenShotFolderLocation(new BaseTest().getCurrentPlatform());
      String _simpleName = this.getClass().getSimpleName();
      String _plus = (_screenShotFolderLocation + _simpleName);
      String _plus_1 = (_plus + "-1-");
      String _plus_2 = (_plus_1 + deviceID);
      String _plus_3 = (_plus_2 + "-");
      SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_SSS");
      Date _date = new Date();
      String _format = _simpleDateFormat.format(_date);
      String _plus_4 = (_plus_3 + _format);
      String _plus_5 = (_plus_4 + ".txt");
      File logFile = new File(_plus_5);
      if (((!logFile.exists()) && (!logFile.isDirectory()))) {
        logFile.createNewFile();
      }
      PrintWriter log_file_writer = new PrintWriter(logFile);
      log_file_writer.println(new AutomationUtil().executeAsString((("adb -s " + deviceID) + " logcat -v time -d")));
      return capabilities;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Set the Android web application's desired capabilities
   * @param deviceID Device Id of the android phone
   * 
   * @author Shameer
   */
  public DesiredCapabilities getAndroidWebOptions(final String deviceID) {
    DesiredCapabilities capabilities = DesiredCapabilities.android();
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
    capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ("Android_" + deviceID));
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ConfigKeys.ANDROID_AUTOMATION_INSTRUMENTATION);
    return capabilities;
  }
  
  /**
   * Set the Iphone(IOS) native application's desired capabilities
   * @param deviceID Device Id of the android phone
   * @param appPath Application's Path
   * 
   * @author Shameer
   */
  public DesiredCapabilities getIphoneNativeOptions(final String deviceID, final String appPath) {
    DesiredCapabilities capabilities = DesiredCapabilities.iphone();
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS_IPHONE");
    capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ("IOS_IPHONE_" + deviceID));
    capabilities.setCapability(MobileCapabilityType.APP, appPath);
    capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
    capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
    return capabilities;
  }
  
  /**
   * Set the Ipad(IOS) native application's desired capabilities
   * @param deviceID Device Id of the android phone
   * @param appPath Application's Path of .apk file
   * 
   * @author Shameer
   */
  public DesiredCapabilities getIpadNativeOptions(final String deviceID, final String appPath) {
    DesiredCapabilities capabilities = DesiredCapabilities.ipad();
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS_IPAD");
    capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ("IOS_IPAD_" + deviceID));
    capabilities.setCapability(MobileCapabilityType.APP, appPath);
    capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
    capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
    return capabilities;
  }
  
  /**
   * Set the Ipad(IOS) web application's desired capabilities
   * @param deviceID Device Id of the android phone
   * 
   * @author Shameer
   */
  public DesiredCapabilities getIphoneWebOptions(final String deviceID) {
    DesiredCapabilities capabilities = DesiredCapabilities.iphone();
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS_IPHONE");
    capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ("IOS_IPHONE_" + deviceID));
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
    capabilities.setCapability(IOSMobileCapabilityType.START_IWDP, true);
    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ConfigKeys.IOS_AUTOMATION_INSTRUMENTATION);
    return capabilities;
  }
  
  /**
   * Set the Ipad(IOS) web application's desired capabilities
   * @param deviceID Device Id of the android phone
   * 
   * @author Shameer
   */
  public DesiredCapabilities getIpadWebOptions(final String deviceID) {
    DesiredCapabilities capabilities = DesiredCapabilities.ipad();
    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS_IPAD");
    capabilities.setCapability(MobileCapabilityType.UDID, deviceID);
    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ("IOS_IPAD_" + deviceID));
    capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, MobileBrowserType.SAFARI);
    capabilities.setCapability(IOSMobileCapabilityType.START_IWDP, true);
    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ConfigKeys.IOS_AUTOMATION_INSTRUMENTATION);
    return capabilities;
  }
}
