package pages;

import core.customLocators.annotations.SearchBy;
import core.customLocators.enums.How;
import core.customLocators.utils.elements.DynamicElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import util.AutomationUtil;

@SuppressWarnings("all")
public class BasePage extends AutomationUtil {
  public BasePage(final RemoteWebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
    this.initElements();
  }
  
  /**
   * Providing the mechanism for finding dynamic web elements.
   * @author Shameer
   */
  public final void initElements() {
    final List<Field> fields = new ArrayList<Field>();
    Class currentPageObject = this.getClass();
    while ((currentPageObject != BasePage.class)) {
      {
        List<Field> _asList = Arrays.<Field>asList(currentPageObject.getDeclaredFields());
        ArrayList<Field> _arrayList = new ArrayList<Field>(_asList);
        fields.addAll(_arrayList);
        currentPageObject = currentPageObject.getSuperclass();
      }
    }
    for (final Field field : fields) {
      {
        final SearchBy fieldAnnotation = field.<SearchBy>getAnnotation(SearchBy.class);
        final boolean accessible = field.isAccessible();
        if ((fieldAnnotation != null)) {
          try {
            field.setAccessible(true);
            How _searchBy = fieldAnnotation.searchBy();
            String _value = fieldAnnotation.value();
            DynamicElement _dynamicElement = new DynamicElement(_searchBy, _value);
            field.set(this, _dynamicElement);
            field.setAccessible(accessible);
          } catch (final Throwable _t) {
            if (_t instanceof IllegalAccessException) {
            } else {
              throw Exceptions.sneakyThrow(_t);
            }
          }
        }
      }
    }
  }
  
  /**
   * Updating the values of dynamic web elements.
   * @param element DynamicElement
   * @author Shameer
   */
  public DynamicElement updateElement(final DynamicElement element, final String... values) {
    return element.updateElement(values);
  }
  
  /**
   * Find the first matching dynamic element on the current page
   * @param element Dynamic Element
   * @author Shameer
   */
  public WebElement findElement(final DynamicElement element) {
    return this.driver.findElement(element.getLocator());
  }
  
  /**
   * Click this dynamic element
   * @param element Dynamic Element
   * @author Shameer
   */
  public void click(final DynamicElement element) {
    this.findElement(element).click();
  }
  
  /**
   * Get the innerText of this element..
   * @param element Dynamic Element
   * @author Shameer
   */
  public String getText(final DynamicElement element) {
    return this.findElement(element).getText();
  }
  
  /**
   * A list of all dynamic elements, or an empty list if nothing matches.
   * @param element Dynamic Element
   * @author Shameer
   */
  public List<WebElement> findElements(final DynamicElement element) {
    return this.findElement(element).findElements(element.getLocator());
  }
  
  /**
   * Clear the value
   * @param element Dynamic Element
   * @author Shameer
   */
  public void clear(final DynamicElement element) {
    this.findElement(element).clear();
  }
  
  /**
   * Get the attribute/property's current value or null if the value is not set.
   * @param element Dynamic Element
   * @author Shameer
   */
  public String getAttribute(final DynamicElement element, final String name) {
    return this.findElement(element).getAttribute(name);
  }
  
  /**
   * Get the current, computed value of the property.
   * @param element Dynamic Element
   * @author Shameer
   */
  public String getCssValue(final DynamicElement element, final String propertyName) {
    return this.findElement(element).getCssValue(propertyName);
  }
  
  /**
   * Get a point which containing the location of the top left-hand corner of the dynamic element
   * @param element DynamicElement
   * @author Shameer
   */
  public Point getLocation(final DynamicElement element) {
    return this.findElement(element).getLocation();
  }
  
  /**
   * Get the location and size of the rendered dynamic element
   * @param element DynamicElement
   * @author Shameer
   */
  public Rectangle getRect(final DynamicElement element) {
    return this.findElement(element).getRect();
  }
  
  /**
   * Get the size of the dynamic element on the page.
   * @param element DynamicElement
   * @author Shameer
   */
  public Dimension getSize(final DynamicElement element) {
    return this.findElement(element).getSize();
  }
  
  /**
   * Get the tag name of this dynamic element.
   * @param element DynamicElement
   * @author Shameer
   */
  public String getTagName(final DynamicElement element) {
    return this.findElement(element).getTagName();
  }
  
  /**
   * Is this dynamic element displayed or not
   * @param element DynamicElement
   * @author Shameer
   */
  public boolean isDisplayed(final DynamicElement element) {
    return this.findElement(element).isDisplayed();
  }
  
  /**
   * Is the dynamic element currently enabled or not
   * @param element DynamicElement
   * @author Shameer
   */
  public boolean isEnabled(final DynamicElement element) {
    return this.findElement(element).isEnabled();
  }
  
  /**
   * Determine whether or not this dynamic element is selected or not.
   * @param element DynamicElement
   * @author Shameer
   */
  public boolean isSelected(final DynamicElement element) {
    return this.findElement(element).isSelected();
  }
  
  /**
   * simulate typing into an dynamic element
   * @param element DynamicElement
   * @author Shameer
   */
  public void sendKeys(final DynamicElement element, final CharSequence... keysToSend) {
    this.findElement(element).sendKeys(keysToSend);
  }
  
  /**
   * If this current dynamic element is a form, or an dynamic element within a form, then this will be submitted to
   * the remote server.
   * @param element DynamicElement
   * @author Shameer
   */
  public void submit(final DynamicElement element) {
    this.findElement(element).submit();
  }
  
  /**
   * Capture the screenshot and store it in the specified location.
   * @param element DynamicElement
   * @author Shameer
   */
  public <X extends Object> X getScreenshotAs(final DynamicElement element, final OutputType<X> target) throws WebDriverException {
    return this.findElement(element).<X>getScreenshotAs(target);
  }
  
  /**
   * Check if the element is present in the page
   * @param element DynamicElement
   * @throws InterruptedException
   */
  public Boolean isDynamicElementPresent(final DynamicElement element) throws InterruptedException {
    this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    try {
      Boolean.valueOf(this.findElement(element).isDisplayed()).wait(3);
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
   * Move to the element which is dynamically change
   * @param element DynamicElement
   * @throws InterruptedException
   */
  public void dynamicMoveTo(final DynamicElement element) throws InterruptedException {
    Actions builder = new Actions(this.driver);
    builder.moveToElement(this.findElement(element)).perform();
    builder.moveToElement(this.findElement(element)).click().perform();
  }
}
