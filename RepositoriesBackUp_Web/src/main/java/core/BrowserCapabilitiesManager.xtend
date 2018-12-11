package core

import org.openqa.selenium.Platform
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities

class BrowserCapabilitiesManager {

	/**
	 * Set the chrome web application's desired capabilities
	 * @author 
	 */
	def public DesiredCapabilities getChromeOptions() {

		var ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--disable-popup-blocking");

		var DesiredCapabilities capabilities = DesiredCapabilities.chrome();
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
	def public DesiredCapabilities getFirefoxOptions() {

		var DesiredCapabilities capability = DesiredCapabilities.firefox();
		var FirefoxOptions options = new FirefoxOptions();
		capability.setBrowserName("firefox");
		capability.setPlatform(Platform.WINDOWS);
		capability.setVersion("ANY")
		capability.setCapability("moz:firefoxOptions", options);

		return capability;
	}

	/**
	 * Set the MS EDGE web application's desired capabilities
	 * @author Shameer
	 */
	def public DesiredCapabilities getMSEdgeOptions() {
		var DesiredCapabilities capability = DesiredCapabilities.edge();
		var EdgeOptions options = new EdgeOptions();

		capability.setCapability(EdgeOptions.CAPABILITY, options);
		capability.setBrowserName("MicrosoftEdge");
		capability.setPlatform(Platform.WIN10);
		capability.setBrowserName("internet explorer");
		capability.setCapability("ignoreZoomSetting", true);
		capability.setPlatform(Platform.VISTA);
		capability.setVersion("ANY");
		capability.setCapability("nativeEvents", false);
		capability.setCapability("name", "Remote File Upload using Selenium 2's FileDetectors");
		return capability;
	}
}
