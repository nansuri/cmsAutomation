package util.reports

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.reporter.ExtentHtmlReporter
import com.aventstack.extentreports.reporter.KlovReporter
import com.aventstack.extentreports.reporter.configuration.ChartLocation
import com.aventstack.extentreports.reporter.configuration.Theme
import java.io.File
import java.io.FileReader
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.Date
import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import org.openqa.selenium.Platform

class ExtentReportManager {

	private static MavenXpp3Reader reader = new MavenXpp3Reader();
	private static Model model = reader.read(new FileReader("pom.xml"))
	private static ExtentReports extent
	private static Platform platform
	private static String reportFileName = model.name + "_" +new SimpleDateFormat("yyyy_MM_dd HH_mm_SSS").format(new Date()) + ".html"
	private static String macPath = System.getProperty("user.dir") + "/TestReport"
	private static String windowsPath = System.getProperty("user.dir") + "\\TestReport"
	private static String macReportFileLoc = macPath + "/" + reportFileName
	private static String winReportFileLoc = windowsPath + "\\" + reportFileName

	def public static ExtentReports getInstance() {
		if (extent === null)
			createInstance()
		return extent
	}

	/**
	 * Create an extent report instance
	 * @author   shameer
	 */
	def static ExtentReports createInstance() {
		platform = getCurrentPlatform()
		var String fileName = getReportFileLocation(platform)
		var ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName)
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM)
		htmlReporter.config().setChartVisibilityOnOpen(true)
		htmlReporter.config().setTheme(Theme.STANDARD)
		htmlReporter.config().setDocumentTitle(fileName)
		htmlReporter.config().setEncoding("utf-8")
		htmlReporter.config().setReportName(reportFileName)

		extent = new ExtentReports()
		extent.setSystemInfo("OS", System.getProperty("os.name"))
		extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName())
		extent.setSystemInfo("Environment", "QA")
		extent.setSystemInfo("User Name", System.getProperty("user.name"))

//		var klovReporter = new KlovReporter()
//		// specify mongoDb connection
//		klovReporter.initMongoDbConnection("localhost", 27017)
//		// specify project name
//		klovReporter.setProjectName = model.name
//		// specify a reportName
//		klovReporter.setReportName = reportFileName
//		// URL of the KLOV server
//		klovReporter.setKlovUrl("http://localhost:80")

		extent.attachReporter(htmlReporter)
		return extent
	}

	/**
	 * Get current platform
	 * @author 
	 */
	def private static Platform getCurrentPlatform() {
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
	 * Select the extent report file location based on platform
	 * @param platform According to this platform report location will be selected
	 * @author   shameer
	 */
	def private static String getReportFileLocation(Platform platform) {
		var String reportFileLocation = null

		switch (platform) {
			case MAC: {
				reportFileLocation = macReportFileLoc
				createReportPath(macPath)
				System.out.println("ExtentReport Path for MAC: " + macPath + "\n")
			}
			case WINDOWS: {
				reportFileLocation = winReportFileLoc
				createReportPath(windowsPath)
				System.out.println("ExtentReport Path for WINDOWS: " + windowsPath + "\n")
			}
			default: {
				System.out.println("ExtentReport path has not been set! There is a problem!\n")
			}
		}
		return reportFileLocation
	}

	/**
	 * Create the report path if it does not exist
	 * @param path The location path where reports will be saved
	 * @author   shameer
	 */
	def private static void createReportPath(String path) {
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
	 * Get the report name and return it
	 * @author   shameer
	 */
	def public getReportName() {
		reportFileName
	}
}
