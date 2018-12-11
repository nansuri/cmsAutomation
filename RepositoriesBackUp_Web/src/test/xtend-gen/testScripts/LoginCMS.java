package testScripts;

import contexts.loginCMS;
import core.dataReaderConfig.TestDataReader;
import functions.login.Login_Func;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import testBase.BaseTest;

@SuppressWarnings("all")
public class LoginCMS extends BaseTest {
  private loginCMS loginCMS = new contexts.loginCMS();
  
  @BeforeMethod
  @Parameters("dataPath")
  public void dataReader(@Optional("./src/test/resources/testData/tc_001/TC_001.properties") final String dataPath) {
    try {
      TestDataReader.getTestDataFile(dataPath);
      this.loginCMS.URL = TestDataReader.getTestData("URL");
      this.loginCMS.email = TestDataReader.getTestData("email");
      this.loginCMS.password = TestDataReader.getTestData("password");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test(description = "Login CMS", alwaysRun = true)
  public void loginCMS() {
    BaseTest.test.get().assignAuthor("Shameer");
    this.driver.get(this.loginCMS.URL);
    new Login_Func(this.driver).cmsLogin(this.loginCMS.email, this.loginCMS.password);
  }
}
