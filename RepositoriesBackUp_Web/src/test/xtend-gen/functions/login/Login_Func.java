package functions.login;

import org.eclipse.xtext.xbase.lib.Exceptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.BasePage;
import pages.LoginPage;

@SuppressWarnings("all")
public class Login_Func extends BasePage {
  public Login_Func(final RemoteWebDriver driver) {
    super(driver);
  }
  
  /**
   * Login to the system
   * @param email Email of the system
   * @param password Password of the system
   * @author Shameer
   */
  public void cmsLogin(final String email, final String password) {
    try {
      LoginPage loginPage = new LoginPage(this.driver);
      this.waitForPageLoadComplete(this.driver);
      this.waitToBeDisplayed(loginPage.txt_email);
      Thread.sleep(2000);
      this.type(loginPage.txt_email, email);
      Thread.sleep(3000);
      this.type(loginPage.txt_password, password);
      Thread.sleep(3000);
      loginPage.dropdownClick.click();
      this.waitToBeDisplayed(loginPage.dropdownClick);
      this.selectFromDropdown(loginPage.dropdownClick, "Admin");
      Thread.sleep(2000);
      loginPage.btn_Submit.click();
      this.waitForPageLoadComplete(this.driver);
      Thread.sleep(5000);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
