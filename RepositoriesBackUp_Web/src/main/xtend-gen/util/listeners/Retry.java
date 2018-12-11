package util.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import testBase.BaseTest;

@SuppressWarnings("all")
public class Retry implements IRetryAnalyzer {
  private int count = 0;
  
  private static int maxTry = 2;
  
  /**
   * If any Test cases got fail,those test cases will be executed again[@test] 2 times
   * @author Shameer
   */
  @Override
  public boolean retry(final ITestResult iTestResult) {
    boolean _isSuccess = iTestResult.isSuccess();
    boolean _not = (!_isSuccess);
    if (_not) {
      if ((this.count < Retry.maxTry)) {
        this.count++;
        BaseTest.extent.removeTest(BaseTest.test.get());
        return true;
      }
    } else {
      iTestResult.setStatus(ITestResult.SUCCESS);
    }
    return false;
  }
  
  public void extendReportsFailOperations(final ITestResult iTestResult) {
    Object testClass = iTestResult.getInstance();
    WebDriver webDriver = ((BaseTest) testClass).driver;
  }
}
