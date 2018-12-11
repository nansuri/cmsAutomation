package testBase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import constants.EmailSetup;
import core.BrowserCapabilitiesManager;
import core.ConfigKeys;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import util.emailSetup.CompressFolderZip;
import util.emailSetup.SendEmail;
import util.reports.ExtentReportManager;

@SuppressWarnings("all")
public class BaseTest {
  public RemoteWebDriver driver;
  
  public static BrowserCapabilitiesManager optionsManager = new BrowserCapabilitiesManager();
  
  public static ExtentReports extent;
  
  public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
  
  public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
  
  private Platform platform;
  
  private String macPath = (System.getProperty("user.dir") + "/TestReport");
  
  private String windowsPath = (System.getProperty("user.dir") + "\\TestReport");
  
  private String macScreenShotFileLoc = (((this.macPath + "/") + this.getClass().getSimpleName()) + "/");
  
  private String winScreenShotFileLoc = (((this.windowsPath + "\\") + this.getClass().getSimpleName()) + "\\");
  
  /**
   * Set up report and properties before suite start
   * @author Shameer
   */
  @BeforeSuite(alwaysRun = true)
  public void setUpBeforeSuite() {
    ConfigKeys.setProperties();
    BaseTest.extent = ExtentReportManager.createInstance();
  }
  
  /**
   * Create the parent test in extent report
   * @param testContext It contains all the information for a given test run
   * @author Shameer
   */
  @BeforeClass
  public synchronized void createParentTest(final ITestContext testContext) {
    String _name = testContext.getCurrentXmlTest().getName();
    String _plus = (_name + " | ");
    String _simpleName = this.getClass().getSimpleName();
    String _plus_1 = (_plus + _simpleName);
    ExtentTest parent = BaseTest.extent.createTest(_plus_1);
    BaseTest.parentTest.set(parent);
  }
  
  /**
   * Create the parent test in extent report
   * @param method Test method name
   * @author Shameer
   */
  @BeforeMethod
  public synchronized void createChildTest(final Method method) {
    Test testClass = method.<Test>getAnnotation(Test.class);
    ExtentTest child = BaseTest.parentTest.get().createNode(method.getName(), testClass.description());
    BaseTest.test.set(child);
    ExtentTest _get = BaseTest.test.get();
    String _firstUpper = StringExtensions.toFirstUpper(method.getName());
    String _plus = (("====== " + "TEST [<b>") + _firstUpper);
    String _plus_1 = (_plus + "</b> ] STARTED ======");
    _get.log(Status.INFO, _plus_1);
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
  @Parameters("browser")
  public void BeforeTest(@Optional(ConfigKeys.BROWSER_TYPE) final String browser) throws MalformedURLException {
    try {
      boolean _equals = browser.equals("CHROME");
      if (_equals) {
        try {
          URL _uRL = new URL(ConfigKeys.CHROME_NODEURL);
          DesiredCapabilities _chromeOptions = BaseTest.optionsManager.getChromeOptions();
          RemoteWebDriver _remoteWebDriver = new RemoteWebDriver(_uRL, _chromeOptions);
          this.driver = _remoteWebDriver;
        } catch (final Throwable _t) {
          if (_t instanceof MalformedURLException) {
            final MalformedURLException e = (MalformedURLException)_t;
            e.printStackTrace();
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
      } else {
        boolean _equals_1 = browser.equals("FIREFOX");
        if (_equals_1) {
          try {
            URL _uRL_1 = new URL(ConfigKeys.FF_NODEURL);
            DesiredCapabilities _firefoxOptions = BaseTest.optionsManager.getFirefoxOptions();
            RemoteWebDriver _remoteWebDriver_1 = new RemoteWebDriver(_uRL_1, _firefoxOptions);
            this.driver = _remoteWebDriver_1;
          } catch (final Throwable _t_1) {
            if (_t_1 instanceof MalformedURLException) {
              final MalformedURLException e_1 = (MalformedURLException)_t_1;
              e_1.printStackTrace();
            } else {
              throw Exceptions.sneakyThrow(_t_1);
            }
          }
        } else {
          boolean _equals_2 = browser.equals("MSEDGE");
          if (_equals_2) {
            try {
              URL _uRL_2 = new URL(ConfigKeys.IE_NODEURL);
              DesiredCapabilities _mSEdgeOptions = BaseTest.optionsManager.getMSEdgeOptions();
              RemoteWebDriver _remoteWebDriver_2 = new RemoteWebDriver(_uRL_2, _mSEdgeOptions);
              this.driver = _remoteWebDriver_2;
            } catch (final Throwable _t_2) {
              if (_t_2 instanceof MalformedURLException) {
                final MalformedURLException e_2 = (MalformedURLException)_t_2;
                e_2.printStackTrace();
              } else {
                throw Exceptions.sneakyThrow(_t_2);
              }
            }
          }
        }
      }
    } catch (final Throwable _t_3) {
      if (_t_3 instanceof Exception) {
        final Exception e_3 = (Exception)_t_3;
        BaseTest.parentTest.get().log(Status.ERROR, MarkupHelper.createLabel("ERROR due to below issue:", ExtentColor.PINK));
        BaseTest.parentTest.get().log(Status.ERROR, MarkupHelper.createCodeBlock(e_3.getMessage()));
        BaseTest.extent.flush();
      } else {
        throw Exceptions.sneakyThrow(_t_3);
      }
    }
  }
  
  /**
   * Get the result of the test after each test method executed
   * @param result The result of a test
   * @author Shameer
   */
  @AfterMethod(alwaysRun = true)
  public synchronized void getTheResult(final ITestResult result) {
    try {
      int _status = result.getStatus();
      boolean _equals = (_status == ITestResult.FAILURE);
      if (_equals) {
        String _name = result.getName();
        String _plus = (_name + " | ");
        String _plus_1 = (_plus + " FAILED due to below issue:");
        BaseTest.test.get().log(Status.FAIL, 
          MarkupHelper.createLabel(_plus_1, ExtentColor.RED));
        BaseTest.test.get().log(Status.FAIL, MarkupHelper.createCodeBlock(result.getThrowable().getMessage()));
        BaseTest.test.get().fail("<b>FAILED ScreenShot : </b>", 
          MediaEntityBuilder.createScreenCaptureFromPath(this.takeScreenShot(this.driver, this.generateScreenShotName())).build());
      } else {
        int _status_1 = result.getStatus();
        boolean _equals_1 = (_status_1 == ITestResult.SKIP);
        if (_equals_1) {
          String _name_1 = result.getName();
          String _plus_2 = (_name_1 + " | ");
          String _plus_3 = (_plus_2 + " SKIPED due to below issue:");
          BaseTest.test.get().log(Status.SKIP, 
            MarkupHelper.createLabel(_plus_3, ExtentColor.ORANGE));
          BaseTest.test.get().log(Status.SKIP, MarkupHelper.createCodeBlock(result.getThrowable().getMessage()));
          BaseTest.test.get().skip("<b>SKIPPED ScreenShot : </b>", 
            MediaEntityBuilder.createScreenCaptureFromPath(this.takeScreenShot(this.driver, this.generateScreenShotName())).build());
        } else {
          int _status_2 = result.getStatus();
          boolean _equals_2 = (_status_2 == ITestResult.SUCCESS);
          if (_equals_2) {
            String _name_2 = result.getName();
            String _plus_4 = (_name_2 + " | ");
            String _plus_5 = (_plus_4 + " PASSED");
            BaseTest.test.get().log(Status.PASS, MarkupHelper.createLabel(_plus_5, ExtentColor.GREEN));
          } else {
            String _name_3 = result.getName();
            String _plus_6 = (_name_3 + " | ");
            String _plus_7 = (_plus_6 + " ERROR");
            BaseTest.test.get().log(Status.ERROR, MarkupHelper.createLabel(_plus_7, ExtentColor.PINK));
            BaseTest.test.get().log(Status.ERROR, MarkupHelper.createCodeBlock(result.getThrowable().getMessage()));
            BaseTest.test.get().skip("<b>ERROR ScreenShot : </b>", 
              MediaEntityBuilder.createScreenCaptureFromPath(this.takeScreenShot(this.driver, this.generateScreenShotName())).build());
          }
        }
      }
      ExtentTest _get = BaseTest.test.get();
      String _firstUpper = StringExtensions.toFirstUpper(result.getName());
      String _plus_8 = (("====== " + "TEST [<b>") + _firstUpper);
      String _plus_9 = (_plus_8 + "</b> ] FINISHED ======");
      _get.log(Status.INFO, _plus_9);
      BaseTest.extent.flush();
      this.driver.quit();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Get the skip tests status of the test after each test class got executed
   * @param context It contains all the information for a given test run
   * @author Shameer
   */
  @AfterClass(alwaysRun = true)
  public synchronized void getTheSkipStatus(final ITestContext context) {
    Set<ITestResult> _allResults = context.getSkippedTests().getAllResults();
    for (final ITestResult result : _allResults) {
      int _status = result.getStatus();
      boolean _equals = (_status == 3);
      if (_equals) {
        ExtentTest child = BaseTest.parentTest.get().createNode(result.getName());
        BaseTest.test.set(child);
        String _name = result.getName();
        String _plus = (_name + " | ");
        String _plus_1 = (_plus + " SKIPED due to below issue:");
        BaseTest.test.get().log(Status.SKIP, 
          MarkupHelper.createLabel(_plus_1, 
            ExtentColor.ORANGE));
        BaseTest.test.get().log(Status.SKIP, MarkupHelper.createCodeBlock(result.getThrowable().toString()));
      } else {
        int _status_1 = result.getStatus();
        boolean _equals_1 = (_status_1 == 2);
        if (_equals_1) {
          ExtentTest child_1 = BaseTest.parentTest.get().createNode(result.getName());
          BaseTest.test.set(child_1);
          String _name_1 = result.getName();
          String _plus_2 = (_name_1 + " | ");
          String _plus_3 = (_plus_2 + " FAILED due to below issue:");
          BaseTest.test.get().log(Status.FAIL, 
            MarkupHelper.createLabel(_plus_3, 
              ExtentColor.ORANGE));
          BaseTest.test.get().log(Status.FAIL, MarkupHelper.createCodeBlock(result.getThrowable().toString()));
        } else {
          ExtentTest child_2 = BaseTest.parentTest.get().createNode(result.getName());
          BaseTest.test.set(child_2);
          String _name_2 = result.getName();
          String _plus_4 = (_name_2 + " | ");
          String _plus_5 = (_plus_4 + " ERROR due to below issue:");
          BaseTest.test.get().log(Status.ERROR, 
            MarkupHelper.createLabel(_plus_5, ExtentColor.PINK));
          BaseTest.test.get().log(Status.ERROR, MarkupHelper.createCodeBlock(result.getThrowable().toString()));
        }
      }
    }
    BaseTest.extent.flush();
  }
  
  /**
   * Capture the screenshot and store it in the specified location.
   * @param driver The driver instance
   * @param screenName Screenshot name
   * @author Shameer
   */
  public String takeScreenShot(final WebDriver driver, final String screenName) {
    try {
      this.platform = this.getCurrentPlatform();
      String folderName = this.getScreenShotFolderLocation(this.platform);
      File screenshotFile = ((TakesScreenshot) driver).<File>getScreenshotAs(OutputType.FILE);
      String destination = ((folderName + screenName) + ".png");
      File targetFile = new File(destination);
      FileUtils.copyFile(screenshotFile, targetFile);
      return destination;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        Throwable _cause = e.getCause();
        String _plus = ("An exception occured while taking screenshot " + _cause);
        System.out.println(_plus);
        return null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Generate the screen shot name with unique identifier [timeStamp].
   * @author Shameer
   */
  public String generateScreenShotName() {
    long _id = Thread.currentThread().getId();
    String _plus = (Long.valueOf(_id) + "_");
    String _simpleName = this.getClass().getSimpleName();
    String _plus_1 = (_plus + _simpleName);
    String _plus_2 = (_plus_1 + "_");
    String _methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
    String _plus_3 = (_plus_2 + _methodName);
    String _plus_4 = (_plus_3 + "_");
    SimpleDateFormat _simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd SSS");
    Date _date = new Date();
    String _format = _simpleDateFormat.format(_date);
    String fileName = (_plus_4 + _format);
    return fileName;
  }
  
  /**
   * Get current platform
   * @author Shameer
   */
  public Platform getCurrentPlatform() {
    if ((this.platform == null)) {
      String operSys = System.getProperty("os.name").toLowerCase();
      boolean _contains = operSys.contains("win");
      if (_contains) {
        this.platform = Platform.WINDOWS;
      } else {
        if (((operSys.contains("nix") || operSys.contains("nux")) || operSys.contains("aix"))) {
          this.platform = Platform.LINUX;
        } else {
          boolean _contains_1 = operSys.contains("mac");
          if (_contains_1) {
            this.platform = Platform.MAC;
          }
        }
      }
    }
    return this.platform;
  }
  
  /**
   * Select the screen shot folder Location based on platform
   * @param platform According to this platform report location will be selected
   * @author Shameer
   */
  public String getScreenShotFolderLocation(final Platform platform) {
    String reportFileLocation = null;
    if (platform != null) {
      switch (platform) {
        case MAC:
          reportFileLocation = this.macScreenShotFileLoc;
          this.createScreenShotFolder(this.macPath);
          System.out.println((("ExtentReport Path for MAC: " + this.macPath) + "\n"));
          break;
        case WINDOWS:
          reportFileLocation = this.winScreenShotFileLoc;
          this.createScreenShotFolder(this.windowsPath);
          System.out.println((("ExtentReport Path for WINDOWS: " + this.windowsPath) + "\n"));
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
   * Create the screen shot folder path if it does not exist
   * @param path The location path where reports will be saved
   * @author Shameer
   */
  public void createScreenShotFolder(final String path) {
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
   * Compress the test report folder in to zip and send it through mail
   * @author Shameer
   */
  @AfterSuite(alwaysRun = true)
  public void sendMail() {
    CompressFolderZip appZip = new CompressFolderZip();
    String _value = EmailSetup.SOURCE_FOLDER.value();
    File _file = new File(_value);
    appZip.generateFileList(_file);
    appZip.zipIt(EmailSetup.OUTPUT_ZIP_FILE.value());
    SendEmail.sendMailWithAttachment(new ExtentReportManager().getReportName());
  }
}
