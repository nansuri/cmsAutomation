package util.listeners

import org.openqa.selenium.WebDriver
import org.testng.IRetryAnalyzer
import org.testng.ITestResult
import testBase.BaseTest

class Retry implements IRetryAnalyzer {

	private int count = 0
	private static int maxTry = 2 // Run the failed test 2 times

	/**
	 * If any Test cases got fail,those test cases will be executed again[@test] 2 times
	 * @author Shameer
	 */
	override public boolean retry(ITestResult iTestResult) {
		if (!iTestResult.isSuccess()) { // Check if test not succeed
			if (count < maxTry) { // Check if maxtry count is reached
				count++ // Increase the maxTry count by 1
				// iTestResult.setStatus(ITestResult.FAILURE) // Mark test as failed
				// extendReportsFailOperations(iTestResult)    //ExtentReports fail operations
				BaseTest.extent.removeTest(BaseTest.test.get)
				return true // Tells TestNG to re-run the test
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS) // If test passes, TestNG marks it as passed
		}
		return false
	}

	def public void extendReportsFailOperations(ITestResult iTestResult) {
		var Object testClass=iTestResult.getInstance() 
		var WebDriver webDriver=((testClass as BaseTest)).driver
		//BaseTest.extent.removeTest(webDriver)
		//ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed") 

	}
}
