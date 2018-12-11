package core;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

@SuppressWarnings("all")
public class BrowserCapabilitiesManager {
  /**
   * Set the chrome web application's desired capabilities
   * @author
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
    capabilities.setPlatform(Platform.MAC);
    capabilities.setVersion("ANY");
    capabilities.setCapability("nativeEvents", false);
    return capabilities;
  }
  
  /**
   * Set the firefox web application's desired capabilities
   * @author
   */
  public DesiredCapabilities getFirefoxOptions() {
    DesiredCapabilities capability = DesiredCapabilities.firefox();
    FirefoxOptions options = new FirefoxOptions();
    capability.setBrowserName("firefox");
    capability.setPlatform(Platform.WINDOWS);
    capability.setVersion("ANY");
    capability.setCapability("moz:firefoxOptions", options);
    return capability;
  }
  
  /**
   * Set the MS EDGE web application's desired capabilities
   * @author Shameer
   */
  public DesiredCapabilities getMSEdgeOptions() {
    DesiredCapabilities capability = DesiredCapabilities.edge();
    EdgeOptions options = new EdgeOptions();
    capability.setCapability(EdgeOptions.CAPABILITY, options);
    capability.setBrowserName("MicrosoftEdge");
    capability.setPlatform(Platform.WIN10);
    capability.setBrowserName("internet explorer");
    capability.setCapability("ignoreZoomSetting", true);
    capability.setPlatform(Platform.VISTA);
    capability.setVersion("ANY");
    capability.setCapability("nativeEvents", false);
    capability.setCapability("name", "Remote File Upload using Selenium 2\'s FileDetectors");
    return capability;
  }
}
