package pages

import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.How

class LoginPage extends BasePage {

	new(RemoteWebDriver driver) {
		super(driver)

	}

	@FindBy(how=How.XPATH, using="//input[@id='username']")
	public WebElement txt_email;

	@FindBy(how=How.XPATH, using="//button[@id='login-submit']")
	public WebElement btn_continue;

	@FindBy(how=How.XPATH, using="//input[@id='password']")
	public WebElement txt_password;
	
	@FindBy(how=How.XPATH, using="//button[@id='login-submit']")
	public WebElement btn_login;
	
	@FindBy(how=How.XPATH, using="//select[@id='type']")
	public WebElement dropdownClick;

	@FindBy(how=How.XPATH, using ="//button[@type='submit']")
	public WebElement btn_Submit
}
