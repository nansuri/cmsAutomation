package util;

import org.openqa.selenium.remote.RemoteWebDriver;

@SuppressWarnings("all")
public class DefaultWebDriver {
  public RemoteWebDriver driver;
  
  public RemoteWebDriver getDriver() {
    return this.driver;
  }
}
