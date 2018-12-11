package util;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.imageio.ImageIO;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.TextRecognizer;
import util.DefaultWebDriver;

@SuppressWarnings("all")
public class AutomationUtil extends DefaultWebDriver {
  /**
   * Wait for an element to be Displayed
   * @param element WebElement
   * @param timeoutInSeconds Timeout in seconds
   * @author Shameer
   */
  public void waitToBeDisplayed(final WebElement element, final int timeoutInSeconds) {
    try {
      try {
        WebDriverWait wait = new WebDriverWait(this.driver, timeoutInSeconds);
        wait.<WebElement>until(ExpectedConditions.visibilityOf(element));
      } catch (final Throwable _t) {
        if (_t instanceof TimeoutException) {
          String errorMessage = (("After " + Integer.valueOf(timeoutInSeconds)) + "ms, failed to display element ");
          throw new TimeoutException(errorMessage);
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in waitToBeDisplayed: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Waiting 30 seconds for an element to be present on the page/screen, and checking for its presence once every 5 seconds.
   * @param element WebElement
   * @author Shameer
   */
  public void fluentWait(final WebElement element) {
    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
    wait.<WebElement>until(new Function<WebDriver, WebElement>() {
      @Override
      public WebElement apply(final WebDriver driver) {
        return element;
      }
    });
  }
  
  /**
   * Wait for an element to be Displayed
   * @param element WebElement
   * @author Shameer
   */
  public void waitToBeDisplayed(final WebElement element) {
    this.waitToBeDisplayed(element, 60);
  }
  
  /**
   * Wait for an element to be Clickable
   * @param element WebElement
   * @param timeOutInSeconds Timeout in seconds
   * @author Shameer
   */
  public void waitToBeClickable(final WebElement element, final int timeOutInSeconds) {
    try {
      try {
        WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
        wait.<WebElement>until(ExpectedConditions.elementToBeClickable(element));
      } catch (final Throwable _t) {
        if (_t instanceof TimeoutException) {
          String errorMessage = (("After " + Integer.valueOf(timeOutInSeconds)) + "ms, element failed to be clickable");
          throw new TimeoutException(errorMessage);
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in waitToBeClickable: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Wait for an element to be Clickable
   * @param element WebElement
   * @author Shameer
   */
  public void waitToBeClickable(final WebElement element) {
    this.waitToBeClickable(element, 60);
  }
  
  /**
   * Wait for an elements to be Hidden/ Not Visible
   * @param element WebElement
   * @param timeOutInSeconds Timeout in seconds
   * @author Shameer
   */
  public void waitToBeHiddenElements(final WebElement element, final int timeOutInSeconds) {
    try {
      this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
      ArrayList<WebElement> elements = CollectionLiterals.<WebElement>newArrayList();
      try {
        elements.add(element);
        WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
        wait.<Boolean>until(ExpectedConditions.invisibilityOfAllElements(elements));
      } catch (final Throwable _t) {
        if (_t instanceof TimeoutException) {
          String errorMessage = (("After " + Integer.valueOf(timeOutInSeconds)) + "ms, element is not hidden");
          throw new TimeoutException(errorMessage);
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in waitToBeHidden: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      } finally {
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Wait for an element to be Hidden/ Not Visible
   * @param element WebElement
   * @param timeOutInSeconds Timeout in seconds
   * @author Shameer
   */
  public void waitToBeHiddenElement(final WebElement element, final int timeOutInSeconds) {
    try {
      this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
      try {
        WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
        wait.<Boolean>until(ExpectedConditions.invisibilityOf(element));
      } catch (final Throwable _t) {
        if (_t instanceof TimeoutException) {
          String errorMessage = (("After " + Integer.valueOf(timeOutInSeconds)) + "ms, element is not hidden");
          throw new TimeoutException(errorMessage);
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in waitToBeHidden: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      } finally {
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Wait for an element to be Hidden/ Not Visible
   * @param element WebElement
   * @author Shameer
   */
  public void waitToBeHidden(final WebElement element) {
    this.waitToBeHiddenElements(element, 60);
  }
  
  /**
   * Wait for an element to contain any text
   * Checks the element.getText().length() != 0
   * @param element WebElement
   * @param timeOutInSeconds Timeout in seconds
   * @author Shameer
   */
  public void waitForAnyText(final WebElement element, final int timeOutInSeconds) {
    try {
      try {
        WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
        wait.<Boolean>until(new ExpectedCondition<Boolean>() {
          @Override
          public Boolean apply(final WebDriver d) {
            int _length = element.getText().length();
            return Boolean.valueOf((_length != 0));
          }
        });
      } catch (final Throwable _t) {
        if (_t instanceof TimeoutException) {
          String errorMessage = (("After " + Integer.valueOf(timeOutInSeconds)) + "ms, element failed to contain any text");
          throw new TimeoutException(errorMessage);
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in waitForAnyText: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Wait for an elements to be displayed
   * @param element WebElement
   * @param timeOutInSeconds Timeout in seconds
   * @author Shameer
   */
  public void waitToBeDisplayedElements(final List<RemoteWebElement> element, final int timeOutInSeconds) {
    try {
      this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
      try {
        WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
        wait.<List<WebElement>>until(ExpectedConditions.visibilityOfAllElements(((WebElement[])Conversions.unwrapArray(element, WebElement.class))));
      } catch (final Throwable _t) {
        if (_t instanceof TimeoutException) {
          String errorMessage = (("After " + Integer.valueOf(timeOutInSeconds)) + "ms, element is not hidden");
          throw new TimeoutException(errorMessage);
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in waitToBeHidden: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      } finally {
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Wait for an element to hidden the specified text
   * @param element WebElement
   * @param text The text which need to hidden
   * @param timeOutInSeconds Timeout in seconds
   * @author Shameer
   */
  public void waitTextToBeHidden(final WebElement element, final String text, final int timeOutInSeconds) {
    try {
      try {
        WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
        wait.<Boolean>until(new ExpectedCondition<Boolean>() {
          @Override
          public Boolean apply(final WebDriver driver) {
            try {
              boolean _equals = element.getText().equals(text);
              return Boolean.valueOf((!_equals));
            } catch (final Throwable _t) {
              if (_t instanceof NoSuchElementException) {
                return Boolean.valueOf(true);
              } else if (_t instanceof StaleElementReferenceException) {
                return Boolean.valueOf(true);
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
          }
          
          @Override
          public String toString() {
            return String.format("element containing \'%s\' to no longer be visible: %s", text, element);
          }
        });
      } catch (final Throwable _t) {
        if (_t instanceof TimeoutException) {
          String errorMessage = (("After " + Integer.valueOf(timeOutInSeconds)) + "ms, element failed to contain any text");
          throw new TimeoutException(errorMessage);
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in waitForAnyText: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Wait for an element to contain given text
   * @param element WebElement
   * @param text Text to contain
   * @param timeOutInSeconds Timeout in seconds
   * @author Shameer
   */
  public void waitForContainText(final WebElement element, final String text, final long timeOutInSeconds) {
    try {
      try {
        WebDriverWait wait = new WebDriverWait(this.driver, timeOutInSeconds);
        wait.<Boolean>until(ExpectedConditions.textToBePresentInElement(element, text));
      } catch (final Throwable _t) {
        if (_t instanceof TimeoutException) {
          String errorMessage = ((("After " + Long.valueOf(timeOutInSeconds)) + "ms, element failed to contain given text: ") + text);
          throw new TimeoutException(errorMessage);
        } else if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in waitForContainText: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Get text value list of List<WebElement>
   * @param elements List<WebElement> WebElement List
   * @return list of text values in the element list
   * @author Shameer
   */
  public List<String> getTextList(final List<WebElement> elements) {
    try {
      ArrayList<String> textList = CollectionLiterals.<String>newArrayList();
      String elementText = "";
      try {
        for (final WebElement e : elements) {
          {
            elementText = e.getText().trim();
            textList.add(elementText);
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in waitForAnyListText: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      return textList;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Get WebElement containing the given text
   * @param elements List<WebElement> WebElement List
   * @param text Text
   * @return WebElement with the given text
   * @author Shameer
   */
  public WebElement getElementByPartialText(final List<WebElement> elements, final String text) {
    try {
      String elementText = "";
      try {
        if (((elements != null) && (elements.size() > 0))) {
          for (final WebElement e : elements) {
            {
              elementText = e.getText().trim();
              boolean _contains = elementText.contains(text);
              if (_contains) {
                return e;
              }
            }
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in getElementByPartialText: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      return null;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Get WebElement matching exact given text
   * @param elements List<WebElement> WebElement List
   * @param text Text
   * @return WebElement with the given text
   * @author Shameer
   */
  public WebElement getElementByText(final List<WebElement> elements, final String text) {
    try {
      WebElement element = null;
      String elementText = "";
      try {
        if (((elements != null) && (elements.size() > 0))) {
          for (final WebElement e : elements) {
            {
              elementText = e.getText().trim();
              boolean _equals = Objects.equal(elementText, text);
              if (_equals) {
                element = e;
                return e;
              }
            }
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception ex = (Exception)_t;
          String _message = ex.getMessage();
          String _plus = ("Error in getElementByText: " + _message);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      return element;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Right click on the exact webElement
   * @param element WebElement
   * @author Shameer
   */
  public void rightClick(final WebElement element) {
    try {
      try {
        Actions action = new Actions(this.driver).contextClick(element);
        action.build().perform();
        System.out.println("Successfully Right clicked on the element");
      } catch (final Throwable _t) {
        if (_t instanceof StaleElementReferenceException) {
          final StaleElementReferenceException e = (StaleElementReferenceException)_t;
          StackTraceElement[] _stackTrace = e.getStackTrace();
          String _plus = ("Element is not attached to the page document " + _stackTrace);
          throw new StaleElementReferenceException(_plus);
        } else if (_t instanceof NoSuchElementException) {
          final NoSuchElementException e_1 = (NoSuchElementException)_t;
          StackTraceElement[] _stackTrace_1 = e_1.getStackTrace();
          String _plus_1 = ((("Element " + element) + " was not found in DOM ") + _stackTrace_1);
          throw new NoSuchElementException(_plus_1);
        } else if (_t instanceof Exception) {
          final Exception e_2 = (Exception)_t;
          StackTraceElement[] _stackTrace_2 = e_2.getStackTrace();
          String _plus_2 = ((("Element " + element) + " was not clickable ") + _stackTrace_2);
          throw new Exception(_plus_2);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Select the text from drop down which match with exact given text
   * @param element WebElement
   * @param text Text
   * @author Shameer
   */
  public void selectFromDropdown(final WebElement element, final String text) {
    Select select = new Select(element);
    select.selectByVisibleText(text);
  }
  
  /**
   * Select the option from drop down which match with exact given text
   * @param elements List<WebElement> WebElement List
   * @param text Text
   * @author Shameer
   */
  public void selectFromDropdown(final WebElement btn, final List<WebElement> optionsList, final String text) {
    btn.click();
    WebElement matchingElement = this.getElementByPartialText(optionsList, text);
    matchingElement.click();
  }
  
  /**
   * Get the text box value using 'value' attribute
   * @param element WebElement
   * @author Shameer
   */
  public String GetTextBoxvalue(final WebElement element) {
    return element.getAttribute("value");
  }
  
  /**
   * Clear the whole text from text box
   * @param element WebElement
   * @author Shameer
   */
  public void clearRobustly(final WebElement element) {
    try {
      try {
        element.clear();
        int _length = this.GetTextBoxvalue(element).trim().length();
        boolean _notEquals = (_length != 0);
        if (_notEquals) {
          element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
          element.sendKeys(Keys.chord(Keys.DELETE));
        }
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
          StackTraceElement[] _stackTrace = e.getStackTrace();
          String _plus = ((("Element " + element) + " cannot be cleared ") + _stackTrace);
          throw new Exception(_plus);
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Press the ENTER
   * @param element WebElement
   * @author Shameer
   */
  public void pressEnterKey(final WebElement element) {
    element.sendKeys(Keys.ENTER);
  }
  
  /**
   * Type/SendKeys on element with the given text
   * @param element WebElement
   * @param text Text to contain
   * @author Shameer
   */
  public void type(final WebElement element, final String text) {
    element.clear();
    element.sendKeys(text);
  }
  
  /**
   * Swipe the screen in android paltform
   * @param startX The x coordinate relative to the start viewport
   * @param startY The y coordinate relative to the start viewport
   * @param endX The x coordinate relative to the end viewport
   * @param endY The y coordinate relative to the end viewport
   * @author Shameer
   */
  public void androidSwipe(final int startX, final int startY, final int endX, final int endY) throws Exception {
    TouchActions actions = new TouchActions(this.driver);
    actions.down(startX, startY).perform();
    Thread.sleep(5000);
    actions.move(endX, endY).perform();
    actions.up(endX, endY).perform();
  }
  
  /**
   * Swipe the screen
   * @param startX The x coordinate relative to the start viewport
   * @param startY The y coordinate relative to the start viewport
   * @param endX The x coordinate relative to the end viewport
   * @param endY The y coordinate relative to the end viewport
   * @author Shameer
   */
  public void swipe(final int startX, final int startY, final int endX, final int endY) throws Exception {
    Dimension size = this.driver.manage().window().getSize();
    int _width = size.getWidth();
    int _height = size.getHeight();
    Point screen = new Point(_width, _height);
    Point swipe_start = new Point((screen.x * startX), (screen.y * startY));
    Point swipe_end = new Point((screen.x * endX), (screen.y * endY));
    this.androidSwipe(swipe_start.x, swipe_start.y, swipe_end.x, swipe_end.y);
    boolean _equals = Platform.IOS.equals(Platform.IOS);
    if (_equals) {
    } else {
      this.androidSwipe(((int) swipe_start.x), ((int) swipe_start.y), ((int) swipe_end.x), ((int) swipe_end.y));
    }
  }
  
  /**
   * Check if the element is present in the page
   * @param element WebElement
   * @throws InterruptedException
   * @author Shameer
   */
  public Boolean isElementPresent(final WebElement element) throws InterruptedException {
    this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    try {
      element.isDisplayed();
      return Boolean.valueOf(true);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        return Boolean.valueOf(false);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    } finally {
      this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
  }
  
  /**
   * Swipe the screen using command prompt
   * @param deviceID In which device screen need to swipe
   * @param startx The x coordinate relative to the start viewport
   * @param starty The y coordinate relative to the start viewport
   * @param endx The x coordinate relative to the destination viewport
   * @param endy The y coordinate relative to the destination viewport
   * @author Shameer
   */
  public String swipe(final String deviceID, final int startx, final int starty, final int endx, final int endy) {
    return this.executeAsString(
      ((((((((("adb -s " + deviceID) + " shell input touchscreen swipe ") + Integer.valueOf(startx)) + " ") + Integer.valueOf(starty)) + " ") + Integer.valueOf(endx)) + " ") + Integer.valueOf(endy)));
  }
  
  /**
   * Single tab on the coordinates
   * @param deviceID In which device screen need to swipe
   * @param startx The x coordinate relative to the start viewport
   * @param starty The y coordinate relative to the start viewport
   * @author Shameer
   */
  public String singleTab(final String deviceID, final int startx, final int starty) {
    return this.executeAsString(((((("adb -s " + deviceID) + " shell input tap ") + Integer.valueOf(startx)) + " ") + Integer.valueOf(starty)));
  }
  
  /**
   * Turn on/off the wifi network
   * @param deviceID In which device screen need to swipe
   * @param status The status of the [enable /disable] the wifi network
   * @author Shameer
   */
  public String wifiConnection(final String deviceID, final String status) {
    return this.executeAsString(
      ((("adb -s " + deviceID) + " shell am broadcast -a io.appium.settings.wifi --es setstatus ") + status));
  }
  
  /**
   * Execute the command as String
   * @param command The Command which need to be executed on command prompt
   * @throws InterruptedException
   * @throws IOException
   * @return command as string
   * @author Shameer
   */
  public String executeAsString(final String command) {
    try {
      Process pr = this.execute(command);
      InputStream _inputStream = pr.getInputStream();
      InputStreamReader _inputStreamReader = new InputStreamReader(_inputStream);
      BufferedReader input = new BufferedReader(_inputStreamReader);
      StringBuilder sb = new StringBuilder();
      String line = null;
      while (((line = input.readLine()) != null)) {
        boolean _isEmpty = line.isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          sb.append(line);
        }
      }
      input.close();
      pr.destroy();
      return sb.toString();
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        throw new RuntimeException(("Execution error while executing command" + command), e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Get the process command(cmd)
   * @param command The Command which need to be executed on command prompt
   * @throws InterruptedException
   * @throws IOException
   * @return process
   * @author Shameer
   */
  public Process execute(final String command) throws IOException, InterruptedException {
    List<String> commandP = new ArrayList<String>();
    String[] com = command.split(" ");
    for (int i = 0; (i < com.length); i++) {
      String _xblockexpression = null;
      {
        final int _rdIndx_com = i;
        _xblockexpression = com[_rdIndx_com];
      }
      commandP.add(_xblockexpression);
    }
    ProcessBuilder prb = new ProcessBuilder(commandP);
    Process pr = prb.start();
    pr.waitFor(10, TimeUnit.SECONDS);
    return pr;
  }
  
  /**
   * Open the recent Applications on mobile
   * @author Shameer
   */
  public void openRecentsApp() {
    try {
      ((AndroidDriver<MobileElement>) this.driver).pressKeyCode(AndroidKeyCode.HOME);
      ((AndroidDriver<MobileElement>) this.driver).pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
      Thread.sleep(5000);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Create a cache manager and cache
   * @return cache Return the created cache
   * @author Shameer
   */
  public Cache createCache() {
    CacheManager cachManager = CacheManager.getInstance();
    Cache cache = null;
    boolean _cacheExists = cachManager.cacheExists("cache");
    if (_cacheExists) {
      cache = cachManager.getCache("cache");
      return cache;
    } else {
      cachManager.addCache("cache");
      cache = cachManager.getCache("cache");
      return cache;
    }
  }
  
  /**
   * Store the value in to cache
   * @param key The key which holds the value
   * @param value The value which need to  be cache
   * @author Shameer
   */
  public void setCache(final String key, final String value) {
    Cache _createCache = this.createCache();
    Element _element = new Element(key, value);
    _createCache.put(_element);
  }
  
  /**
   * Get the stored cached value by giving key
   * @param key The key which holds the value
   * @author Shameer
   */
  public String getCache(final String key) {
    Element ele = this.createCache().get(key);
    return ele.getObjectValue().toString();
  }
  
  /**
   * Recognize the text presents in the given image.
   * @param capturedImage the image which need to be extract the text
   * @return the text present in the image.
   * @author Shameer
   */
  public String recognizeImage(final String capturedImage) {
    TextRecognizer textRecognizer = TextRecognizer.getInstance();
    String reconizedText = "";
    try {
      String _decode = URLDecoder.decode(capturedImage, "UTF-8");
      File _file = new File(_decode);
      BufferedImage image = ImageIO.read(_file);
      reconizedText = textRecognizer.recognizeWord(image);
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException ioException = (IOException)_t;
        System.err.println(ioException.getMessage());
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return reconizedText;
  }
  
  /**
   * create image  for element using its location and size.This will give image data specific to the WebElement
   * @param element  Get the entire screenshot from the driver of passed WebElement
   * @return Return the file object containing image data
   * @author Shameer
   */
  public File captureElementPicture(final WebElement element) throws Exception {
    WrapsDriver wrapsDriver = ((WrapsDriver) element);
    WebDriver _wrappedDriver = wrapsDriver.getWrappedDriver();
    File screen = ((TakesScreenshot) _wrappedDriver).<File>getScreenshotAs(OutputType.FILE);
    BufferedImage img = ImageIO.read(screen);
    int width = element.getSize().getWidth();
    int height = element.getSize().getHeight();
    Rectangle rect = new Rectangle(width, height);
    Point p = element.getLocation();
    BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
    ImageIO.write(dest, "png", screen);
    return screen;
  }
  
  /**
   * Read the image data for element in File object
   * @param imageElement  The image element which need to be read
   * @return Return the file object containing image data
   * @author Shameer
   */
  public String readImageWebElement(final File imageElement) throws Exception {
    Tesseract instance = Tesseract.getInstance();
    instance.setTessVariable("tessedit_char_whitelist", 
      "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    instance.setTessVariable("tessedit_char_blacklist", "~ﬂ");
    File tessDataFolder = LoadLibs.extractTessResources("tessdata");
    instance.setDatapath(tessDataFolder.getAbsolutePath());
    return instance.doOCR(imageElement);
  }
  
  /**
   * Wait till the page load complete
   * @param elements List<WebElement> WebElement List
   * @return list of text values in the element list
   * @author Shameer
   */
  public void waitForPageLoadComplete(final WebDriver driver, final int specifiedTimeout) {
    Wait<WebDriver> wait = new WebDriverWait(driver, specifiedTimeout);
    final java.util.function.Function<WebDriver, Boolean> _function = (WebDriver driver1) -> {
      return Boolean.valueOf(String.valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState")).equals("complete"));
    };
    wait.<Boolean>until(_function);
  }
  
  /**
   * Wait 60 seconds till the page load complete
   * @param elements List<WebElement> WebElement List
   * @return list of text values in the element list
   * @author Shameer
   */
  public void waitForPageLoadComplete(final WebDriver driver) {
    this.waitForPageLoadComplete(driver, 60);
  }
}
