package functions.login

import org.openqa.selenium.remote.RemoteWebDriver
import pages.BasePage
import pages.LoginPage

class Login_Func extends BasePage {

	new(RemoteWebDriver driver) {
		super(driver)

	}

	/**
	 * Login to the system
	 * @param email Email of the system
	 * @param password Password of the system
	 * @author Shameer
	 */
	def void cmsLogin(String email, String password) {
		var LoginPage loginPage = new LoginPage(driver)

		waitForPageLoadComplete(driver)
		loginPage.txt_email.waitToBeDisplayed
		Thread.sleep(2000)
		loginPage.txt_email.type(email)
		Thread.sleep(3000)
		//loginPage.btn_continue.waitToBeClickable
		//loginPage.btn_continue.click
		//loginPage.txt_password.waitToBeDisplayed
		loginPage.txt_password.type(password)
		Thread.sleep(3000)
		loginPage.dropdownClick.click
		loginPage.dropdownClick.waitToBeDisplayed
		loginPage.dropdownClick.selectFromDropdown("Admin")
		Thread.sleep(2000)
		loginPage.btn_Submit.click
		//dashboardPage.txt_filterContainers.waitToBeDisplayed
		waitForPageLoadComplete(driver)
		Thread.sleep(5000)

	}

}
