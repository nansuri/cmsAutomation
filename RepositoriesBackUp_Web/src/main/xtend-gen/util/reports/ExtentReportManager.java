package util.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.openqa.selenium.Platform;

@SuppressWarnings("all")
public class ExtentReportManager {
  private static MavenXpp3Reader reader = new MavenXpp3Reader();
  
  private static Model model = new Function0<Model>() {
    public Model apply() {
      try {
        FileReader _fileReader = new FileReader("pom.xml");
        Model _read = ExtentReportManager.reader.read(_fileReader);
        return _read;
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    }
  }.apply();
  
  private static ExtentReports extent;
  
  private static Platform platform;
  
  private static String reportFileName = (((ExtentReportManager.model.getName() + "_") + new SimpleDateFormat("yyyy_MM_dd HH_mm_SSS").format(new Date())) + ".html");
  
  private static String macPath = (System.getProperty("user.dir") + "/TestReport");
  
  private static String windowsPath = (System.getProperty("user.dir") + "\\TestReport");
  
  private static String macReportFileLoc = ((ExtentReportManager.macPath + "/") + ExtentReportManager.reportFileName);
  
  private static String winReportFileLoc = ((ExtentReportManager.windowsPath + "\\") + ExtentReportManager.reportFileName);
  
  public static ExtentReports getInstance() {
    if ((ExtentReportManager.extent == null)) {
      ExtentReportManager.createInstance();
    }
    return ExtentReportManager.extent;
  }
  
  /**
   * Create an extent report instance
   * @author   shameer
   */
  public static ExtentReports createInstance() {
    try {
      ExtentReportManager.platform = ExtentReportManager.getCurrentPlatform();
      String fileName = ExtentReportManager.getReportFileLocation(ExtentReportManager.platform);
      ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
      htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
      htmlReporter.config().setChartVisibilityOnOpen(true);
      htmlReporter.config().setTheme(Theme.STANDARD);
      htmlReporter.config().setDocumentTitle(fileName);
      htmlReporter.config().setEncoding("utf-8");
      htmlReporter.config().setReportName(ExtentReportManager.reportFileName);
      ExtentReports _extentReports = new ExtentReports();
      ExtentReportManager.extent = _extentReports;
      ExtentReportManager.extent.setSystemInfo("OS", System.getProperty("os.name"));
      ExtentReportManager.extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
      ExtentReportManager.extent.setSystemInfo("Environment", "QA");
      ExtentReportManager.extent.setSystemInfo("User Name", System.getProperty("user.name"));
      ExtentReportManager.extent.attachReporter(htmlReporter);
      return ExtentReportManager.extent;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Get current platform
   * @author
   */
  private static Platform getCurrentPlatform() {
    if ((ExtentReportManager.platform == null)) {
      String operSys = System.getProperty("os.name").toLowerCase();
      boolean _contains = operSys.contains("win");
      if (_contains) {
        ExtentReportManager.platform = Platform.WINDOWS;
      } else {
        if (((operSys.contains("nix") || operSys.contains("nux")) || operSys.contains("aix"))) {
          ExtentReportManager.platform = Platform.LINUX;
        } else {
          boolean _contains_1 = operSys.contains("mac");
          if (_contains_1) {
            ExtentReportManager.platform = Platform.MAC;
          }
        }
      }
    }
    return ExtentReportManager.platform;
  }
  
  /**
   * Select the extent report file location based on platform
   * @param platform According to this platform report location will be selected
   * @author   shameer
   */
  private static String getReportFileLocation(final Platform platform) {
    String reportFileLocation = null;
    if (platform != null) {
      switch (platform) {
        case MAC:
          reportFileLocation = ExtentReportManager.macReportFileLoc;
          ExtentReportManager.createReportPath(ExtentReportManager.macPath);
          System.out.println((("ExtentReport Path for MAC: " + ExtentReportManager.macPath) + "\n"));
          break;
        case WINDOWS:
          reportFileLocation = ExtentReportManager.winReportFileLoc;
          ExtentReportManager.createReportPath(ExtentReportManager.windowsPath);
          System.out.println((("ExtentReport Path for WINDOWS: " + ExtentReportManager.windowsPath) + "\n"));
          break;
        default:
          System.out.println("ExtentReport path has not been set! There is a problem!\n");
          break;
      }
    } else {
      System.out.println("ExtentReport path has not been set! There is a problem!\n");
    }
    return reportFileLocation;
  }
  
  /**
   * Create the report path if it does not exist
   * @param path The location path where reports will be saved
   * @author   shameer
   */
  private static void createReportPath(final String path) {
    File testDirectory = new File(path);
    boolean _exists = testDirectory.exists();
    boolean _not = (!_exists);
    if (_not) {
      boolean _mkdir = testDirectory.mkdir();
      if (_mkdir) {
        System.out.println((("Directory: " + path) + " is created!"));
      } else {
        System.out.println(("Failed to create directory: " + path));
      }
    } else {
      System.out.println(("Directory already exists: " + path));
    }
  }
  
  /**
   * Get the report name and return it
   * @author   shameer
   */
  public String getReportName() {
    return ExtentReportManager.reportFileName;
  }
}
