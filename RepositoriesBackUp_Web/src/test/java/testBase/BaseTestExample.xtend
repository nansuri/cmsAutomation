package testBase

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.MediaEntityBuilder
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.markuputils.ExtentColor
import com.aventstack.extentreports.markuputils.MarkupHelper
import constants.EmailSetup
import core.ConfigKeys
import core.DeviceCapabilitiesManager
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import java.io.File
import java.io.PrintWriter
import java.lang.reflect.Method
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.Platform
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.ITestContext
import org.testng.ITestResult
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.BeforeSuite
import org.testng.annotations.Parameters
import org.testng.annotations.Test
import util.AutomationUtil
import util.emailSetup.CompressFolderZip
import util.emailSetup.SendEmail
import util.reports.ExtentReportManager

class BaseTestExample {
	
	public RemoteWebDriver driver
	public static DeviceCapabilitiesManager optionsManager = new DeviceCapabilitiesManager();

	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	private Platform platform
	private String macPath =  "../TestReport"
	private String windowsPath = "..\\TestReport"
	private String macScreenShotFileLoc = macPath + "/" + getClass.simpleName + "/"
	public String winScreenShotFileLoc = windowsPath + "\\" + getClass.simpleName + "\\"

	/**
	 * Set up report and properties before suite start
	 * @author Shameer
	 */
	@BeforeSuite(alwaysRun=true)
	def void setUpBeforeSuite() {
		ConfigKeys.setProperties();
		extent = ExtentReportManager.createInstance()

	}

	/**
	 * Create the parent test in extent report
	 * @param testContext It contains all the information for a given test run
	 * @author Shameer
	 */
	@BeforeClass
	def public synchronized void createParentTest(ITestContext testContext) {
		var ExtentTest parent = extent.createTest(testContext.getCurrentXmlTest().getName() + " | " +getClass().simpleName);
		//parent.assignCategory("Retry-Regression")//Please enable this when you need to append the another execution to thi same report
		parentTest.set(parent);

	}

	/**
	 * Create the parent test in extent report
	 * @param method Test method name
	 * @author Shameer
	 */
	@BeforeMethod
	def public synchronized void createChildTest(Method method) {
		var Test testClass=method.getAnnotation(Test)
		var ExtentTest child = parentTest.get().createNode(method.getName(),testClass.description)
		
		test.set(child)		 

		test.get.log(Status.INFO, "====== " + "TEST [<b>" + method.getName().toFirstUpper + "</b> ] STARTED ======")
	}

	/**
	 * Start the execution by stating device/application capabilities
	 * @param platform State the platform and application type
	 * @param nodeURL The  node URL where the test execute
	 * @param deviceID The real connected device id
	 * @param appPath The application's path of application
	 * @param appActivity The application's activity name
	 * @param appPackage The application's package name
	 * @author Shameer
	 */
	@BeforeMethod
	@Parameters("platform","nodeURL","deviceID","appPath","appActivity","appPackage","appWaitActivity")
	def public void BeforeTest(String platform, String nodeURL, String deviceID, String appPath, String appActivity,
		String appPackage, String appWaitActivity) throws MalformedURLException {

		try {
			if (platform.equals("ANDROID_NATIVE")) {

				try {
					driver = new AndroidDriver(new URL(nodeURL),
						optionsManager.getAndroidNativeOptions(deviceID, appPath, appActivity, appPackage,
							appWaitActivity))
					
					createScreenShotFolder(getScreenShotFolderLocation(currentPlatform))
					var File logFile = new File(getScreenShotFolderLocation(currentPlatform)+getClass.simpleName+"-"+deviceID+"-"+new SimpleDateFormat("yyyy_MM_dd HH_mm_SSS").format(new Date())+".txt");
					if (!logFile.exists() && !logFile.isDirectory()) {
						logFile.createNewFile
					}	
					var PrintWriter log_file_writer = new PrintWriter(logFile);
					log_file_writer.println(new AutomationUtil().executeAsString("adb -s "+deviceID+" logcat -v time -d"));

				} catch (MalformedURLException e) {

					e.printStackTrace();

				}

			} else if (platform.equals("ANDROID_WEB")) {

				try {
					driver = new AndroidDriver(new URL(nodeURL), optionsManager.getAndroidWebOptions(deviceID));
				} catch (MalformedURLException e) {
					e.printStackTrace();

				}

			} else if (platform.equals("IOS_IPHONE_NATIVE")) {

				try {
					driver = new IOSDriver(new URL(nodeURL), optionsManager.getIphoneNativeOptions(deviceID, appPath));
				} catch (MalformedURLException e) {
					e.printStackTrace();

				}

			} else if (platform.equals("IOS_IPAD_NATIVE")) {

				try {
					driver = new IOSDriver(new URL(nodeURL), optionsManager.getIpadNativeOptions(deviceID, appPath));
				} catch (MalformedURLException e) {
					e.printStackTrace();

				}

			} else if (platform.equals("IOS_IPHONE_WEB")) {

				try {
					driver = new IOSDriver(new URL(nodeURL), optionsManager.getIphoneWebOptions(deviceID));
				} catch (MalformedURLException e) {
					e.printStackTrace();

				}

			} else if (platform.equals("IOS_IPAD_WEB")) {

				try {
					driver = new IOSDriver(new URL(nodeURL), optionsManager.getIpadWebOptions(deviceID));
				} catch (MalformedURLException e) {
					e.printStackTrace();

				}

			}
		} catch (Exception e) {
			parentTest.get.log(Status.ERROR, MarkupHelper.createLabel("ERROR due to below issue:", ExtentColor.PINK))
			parentTest.get.log(Status.ERROR, MarkupHelper.createCodeBlock(e.message))
			extent.flush
		}

	}

	/**
	 * Get the result of the test after each test method executed
	 * @param result The result of a test
	 * @author Shameer
	 */
	@AfterMethod(alwaysRun=true)
	def public synchronized void getTheResult(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			test.get.log(Status.FAIL,
				MarkupHelper.createLabel(result.getName() + " | " + " FAILED due to below issue:", ExtentColor.RED))
			test.get.log(Status.FAIL, MarkupHelper.createCodeBlock(result.throwable.message))
			test.get.fail("<b>FAILED ScreenShot : </b>",
				MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver, generateScreenShotName)).build())
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.get.log(Status.SKIP,
				MarkupHelper.createLabel(result.getName() + " | " + " SKIPED due to below issue:", ExtentColor.ORANGE))
			test.get.log(Status.SKIP, MarkupHelper.createCodeBlock(result.throwable.message))
			test.get.skip("<b>SKIPPED ScreenShot : </b>",
				MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver, generateScreenShotName)).build())
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.get.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " | " + " PASSED", ExtentColor.GREEN))
		} else {
			test.get.log(Status.ERROR, MarkupHelper.createLabel(result.getName() + " | " + " ERROR", ExtentColor.PINK))
			test.get.log(Status.ERROR, MarkupHelper.createCodeBlock(result.throwable.message))
			test.get.skip("<b>ERROR ScreenShot : </b>",
				MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver, generateScreenShotName)).build())
		}

		test.get.log(Status.INFO, "====== " + "TEST [<b>" + result.getName().toFirstUpper + "</b> ] FINISHED ======")
		extent.flush()
		driver.quit()

	}

	/**
	 * Get the skip tests status of the test after each test class got executed
	 * @param context It contains all the information for a given test run
	 * @author Shameer
	 */
	@AfterClass(alwaysRun=true)
	def public synchronized void getTheSkipStatus(ITestContext context) {

		for (ITestResult result : context.getSkippedTests.getAllResults) {
			if (result.getStatus() == 3) {
				var ExtentTest child = parentTest.get().createNode(result.getName())
				test.set(child)
				test.get.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " | " + " SKIPED due to below issue:",
						ExtentColor.ORANGE))
				test.get.log(Status.SKIP, MarkupHelper.createCodeBlock(result.throwable.toString))
			} else if (result.getStatus() == 2) {
				var ExtentTest child = parentTest.get().createNode(result.getName())
				test.set(child)
				test.get.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " | " + " FAILED due to below issue:",
						ExtentColor.ORANGE))
				test.get.log(Status.FAIL, MarkupHelper.createCodeBlock(result.throwable.toString))
			} else {
				var ExtentTest child = parentTest.get().createNode(result.getName())
				test.set(child)
				test.get.log(Status.ERROR,
					MarkupHelper.createLabel(result.getName() + " | " + " ERROR due to below issue:", ExtentColor.PINK))
				test.get.log(Status.ERROR, MarkupHelper.createCodeBlock(result.throwable.toString))
			}
		}
		extent.flush()
	}

	/**
	 * Capture the screenshot and store it in the specified location.
	 * @param driver The driver instance
	 * @param screenName Screenshot name
	 * @author Shameer
	 */
	def public takeScreenShot(WebDriver driver, String screenName) {

		try {
			platform = getCurrentPlatform()
			var String folderName = getScreenShotFolderLocation(platform)
			var File screenshotFile = ((driver as TakesScreenshot)).getScreenshotAs(OutputType.FILE)
			var String destination = folderName + screenName + ".png"
			var File targetFile = new File(destination)
			FileUtils.copyFile(screenshotFile, targetFile)
			return destination

		} catch (Exception e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause())
			return null
		}
	}

	/**
	 * Generate the screen shot name with unique identifier [timeStamp].
	 * @author Shameer
	 */
	def public String generateScreenShotName() {
		var String fileName = Thread.currentThread().id + "_" + getClass().simpleName + "_" +
			Thread.currentThread().stackTrace.get(2).methodName + "_" +
			new SimpleDateFormat("yyyy_MM_dd SSS").format(new Date())
		return fileName
	}

	/**
	 * Get current platform
	 * @author Shameer
	 */
	def public Platform getCurrentPlatform() {
		if (platform === null) {
			var String operSys = System.getProperty("os.name").toLowerCase()
			if (operSys.contains("win")) {
				platform = Platform.WINDOWS
			} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
				platform = Platform.LINUX
			} else if (operSys.contains("mac")) {
				platform = Platform.MAC
			}
		}
		return platform
	}

	/**
	 * Select the screen shot folder Location based on platform
	 * @param platform According to this platform report location will be selected
	 * @author Shameer
	 */
	def public String getScreenShotFolderLocation(Platform platform) {
		var String reportFileLocation = null

		switch (platform) {
			case MAC: {
				reportFileLocation = macScreenShotFileLoc
				createScreenShotFolder(macPath)
				System.out.println("ExtentReport Path for MAC: " + macPath + "\n")
			}
			case WINDOWS: {
				reportFileLocation = winScreenShotFileLoc
				createScreenShotFolder(windowsPath)
				System.out.println("ExtentReport Path for WINDOWS: " + windowsPath + "\n")
			}
			default: {
				System.out.println("ExtentReport path has not been set! There is a problem!\n")
			}
		}
		return reportFileLocation
	}

	/**
	 * Create the screen shot folder path if it does not exist
	 * @param path The location path where reports will be saved
	 * @author Shameer
	 */
	def public void createScreenShotFolder(String path) {
		var File testDirectory = new File(path)
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory: " + path + " is created!")
			} else {
				System.out.println("Failed to create directory: " + path)
			}
		} else {
			System.out.println("Directory already exists: " + path)
		}
	}
	
	/**
	 * Compress the test report folder in to zip and send it through mail
	 * @author Shameer
	 */
	@AfterSuite(alwaysRun=true)
	def public void sendMail() {
		var CompressFolderZip appZip=new CompressFolderZip() 
		appZip.generateFileList(new File(EmailSetup.SOURCE_FOLDER.value)) 
		appZip.zipIt(EmailSetup.OUTPUT_ZIP_FILE.value) 

		SendEmail.sendMailWithAttachment(new ExtentReportManager().getReportName)
	}
	
}