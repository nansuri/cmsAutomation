package testScripts

//import contexts.BitbucketContext
import contexts.loginCMS
import core.dataReaderConfig.TestDataReader
//import functions.bitBucket.Bitbucket_Func
import functions.login.Login_Func
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Optional
import org.testng.annotations.Parameters
import org.testng.annotations.Test
import testBase.BaseTest

class LoginCMS extends BaseTest {

	// List down the context files
	loginCMS loginCMS = new loginCMS

	@BeforeMethod
	@Parameters("dataPath")
	def void dataReader(@Optional("./src/test/resources/testData/tc_001/TC_001.properties") String dataPath) {
		TestDataReader.getTestDataFile(dataPath) // Load the test data file
		
		loginCMS.URL = TestDataReader.getTestData("URL")
		loginCMS.email = TestDataReader.getTestData("email")
		loginCMS.password = TestDataReader.getTestData("password")
		
		
		
	}

	@Test(description="Login CMS", alwaysRun=true) // retryAnalyzer = Retry
	def void loginCMS() {
		test.get.assignAuthor("Shameer")
		
		driver.get(loginCMS.URL); // Call the base URL
		
		// Login to the system
		new Login_Func(driver).cmsLogin(loginCMS.email, loginCMS.password)
		
		

	}

}
