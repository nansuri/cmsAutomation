package pages

import core.customLocators.annotations.SearchBy
import core.customLocators.utils.elements.DynamicElement
import java.lang.reflect.Field
import java.util.ArrayList
import java.util.Arrays
import java.util.List
import java.util.concurrent.TimeUnit
import org.openqa.selenium.Dimension
import org.openqa.selenium.OutputType
import org.openqa.selenium.Point
import org.openqa.selenium.Rectangle
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.PageFactory
import util.AutomationUtil

class BasePage extends AutomationUtil {

	new(RemoteWebDriver driver) {
		this.driver = driver
		PageFactory.initElements(driver, this);
		initElements()
	}

	/**
	 * Providing the mechanism for finding dynamic web elements.
	 * @author Shameer
	 */
	def final void initElements() {
		val List<Field> fields = new ArrayList()
		var Class currentPageObject = this.getClass()
		while (currentPageObject !== BasePage) {
			fields.addAll(new ArrayList(Arrays.asList(currentPageObject.getDeclaredFields())))
			currentPageObject = currentPageObject.getSuperclass()
		}
		for (Field field : fields) {
			val SearchBy fieldAnnotation = field.getAnnotation(SearchBy)
			val boolean accessible = field.isAccessible()
			if (fieldAnnotation !== null) {
				try {
					field.setAccessible(true)
					field.set(this, new DynamicElement(fieldAnnotation.searchBy(), fieldAnnotation.value()))
					field.setAccessible(accessible)
				} catch (IllegalAccessException e) {
				}

			}
		}
	}

	/**
	 * Updating the values of dynamic web elements.
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def DynamicElement updateElement(DynamicElement element, String... values) {
		return element.updateElement(values)
	}

	/**
	 * Find the first matching dynamic element on the current page
	 * @param element Dynamic Element
	 * @author Shameer
	 */
	def WebElement findElement(DynamicElement element) {
		return driver.findElement(element.getLocator())
	}

	/**
	 * Click this dynamic element
	 * @param element Dynamic Element
	 * @author Shameer
	 */
	def void click(DynamicElement element) {
		findElement(element).click()
	}

	/**
	 * Get the innerText of this element..
	 * @param element Dynamic Element
	 * @author Shameer
	 */
	def String getText(DynamicElement element) {
		return findElement(element).getText()
	}

	/**
	 * A list of all dynamic elements, or an empty list if nothing matches.
	 * @param element Dynamic Element
	 * @author Shameer
	 */
	def public List<WebElement> findElements(DynamicElement element) {
		return findElement(element).findElements(element.getLocator());
	}

	/**
	 * Clear the value
	 * @param element Dynamic Element
	 * @author Shameer
	 */
	def void clear(DynamicElement element) {
		findElement(element).clear
	}

	/**
	 * Get the attribute/property's current value or null if the value is not set.
	 * @param element Dynamic Element
	 * @author Shameer
	 */
	def String getAttribute(DynamicElement element, String name) {
		return findElement(element).getAttribute(name);
	}

	/**
	 * Get the current, computed value of the property.
	 * @param element Dynamic Element
	 * @author Shameer
	 */
	def public String getCssValue(DynamicElement element, String propertyName) {
		return findElement(element).getCssValue(propertyName);
	}

	/**
	 * Get a point which containing the location of the top left-hand corner of the dynamic element
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public Point getLocation(DynamicElement element) {
		return findElement(element).getLocation();
	}

	/**
	 * Get the location and size of the rendered dynamic element
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public Rectangle getRect(DynamicElement element) {
		return findElement(element).getRect();
	}

	/**
	 * Get the size of the dynamic element on the page.
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public Dimension getSize(DynamicElement element) {
		return findElement(element).getSize();
	}

	/**
	 * Get the tag name of this dynamic element.
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public String getTagName(DynamicElement element) {
		return findElement(element).getTagName();
	}

	/**
	 * Is this dynamic element displayed or not
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public boolean isDisplayed(DynamicElement element) {
		return findElement(element).isDisplayed();
	}

	/**
	 * Is the dynamic element currently enabled or not
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public boolean isEnabled(DynamicElement element) {
		return findElement(element).isEnabled();

	}

	/**
	 * Determine whether or not this dynamic element is selected or not.
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public boolean isSelected(DynamicElement element) {
		return findElement(element).isSelected();
	}

	/**
	 * simulate typing into an dynamic element
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public void sendKeys(DynamicElement element, CharSequence... keysToSend) {
		findElement(element).sendKeys(keysToSend);
	}

	/**
	 * If this current dynamic element is a form, or an dynamic element within a form, then this will be submitted to
	 * the remote server.
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public void submit(DynamicElement element) {
		findElement(element).submit();
	}

	/**
	 * Capture the screenshot and store it in the specified location.
	 * @param element DynamicElement
	 * @author Shameer
	 */
	def public <X> X getScreenshotAs(DynamicElement element, OutputType<X> target) throws WebDriverException {
		findElement(element).getScreenshotAs(target);
	}

	/** 
	 * Check if the element is present in the page
	 * @param element DynamicElement
	 * @throws InterruptedException
	 */
	def Boolean isDynamicElementPresent(DynamicElement element) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
		try {
			findElement(element).displayed.wait(3)
			return true
		} catch (Exception e) {
			return false
		} finally {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
		}
	}
	
	/** 
	 * Move to the element which is dynamically change
	 * @param element DynamicElement
	 * @throws InterruptedException
	 */
	def dynamicMoveTo(DynamicElement element) throws InterruptedException {
		// Configure the Action
		var Actions builder = new Actions(driver)
		// To focus on element using webdriver
		builder.moveToElement(findElement(element)).perform()
		// To click on the element to focus
		builder.moveToElement(findElement(element)).click().perform()

	}
}
