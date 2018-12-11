package util

import org.openqa.selenium.remote.RemoteWebDriver

class DefaultWebDriver {

	public RemoteWebDriver driver

	def RemoteWebDriver getDriver() {
		return this.driver;
	}

}
