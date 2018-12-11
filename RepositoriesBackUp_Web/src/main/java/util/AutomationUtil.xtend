package util

import com.google.common.base.Function
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.AndroidKeyCode
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.URLDecoder
import java.util.ArrayList
import java.util.List
import java.util.NoSuchElementException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.imageio.ImageIO
import net.sf.ehcache.Cache
import net.sf.ehcache.CacheManager
import net.sf.ehcache.Element
import net.sourceforge.tess4j.Tesseract
import net.sourceforge.tess4j.util.LoadLibs
import org.openqa.selenium.Dimension
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.OutputType
import org.openqa.selenium.Platform
import org.openqa.selenium.Point
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.interactions.touch.TouchActions
import org.openqa.selenium.internal.WrapsDriver
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.Wait
import org.openqa.selenium.support.ui.WebDriverWait
import org.sikuli.script.TextRecognizer

class AutomationUtil extends DefaultWebDriver {

	/**
	 * Wait for an element to be Displayed
	 * @param element WebElement
	 * @param timeoutInSeconds Timeout in seconds
	 * @author Shameer
	 */
	def void waitToBeDisplayed(WebElement element, int timeoutInSeconds) {
		try {
			var wait = new WebDriverWait(driver, timeoutInSeconds)
			wait.until(ExpectedConditions.visibilityOf(element))
		} catch (TimeoutException timeoutex) {
			var errorMessage = "After " + timeoutInSeconds + "ms, failed to display element "
			throw new TimeoutException(errorMessage)
		} catch (Exception ex) {
			throw new Exception("Error in waitToBeDisplayed: " + ex.getMessage())
		}
	}

	/**
	 * Waiting 30 seconds for an element to be present on the page/screen, and checking for its presence once every 5 seconds.
	 * @param element WebElement
	 * @author Shameer
	 */
	def void fluentWait(WebElement element) {

		var FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).
			pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException)
		wait.until(new Function<WebDriver, WebElement>() {
			override WebElement apply(WebDriver driver) {
				return element

			}
		})

	}

	/**
	 * Wait for an element to be Displayed
	 * @param element WebElement
	 * @author Shameer
	 */
	def void waitToBeDisplayed(WebElement element) {
		waitToBeDisplayed(element, 60)

	}

	/**
	 * Wait for an element to be Clickable
	 * @param element WebElement
	 * @param timeOutInSeconds Timeout in seconds
	 * @author Shameer
	 */
	def void waitToBeClickable(WebElement element, int timeOutInSeconds) {
		try {
			var wait = new WebDriverWait(driver, timeOutInSeconds)
			wait.until(ExpectedConditions.elementToBeClickable(element))
		} catch (TimeoutException timeoutex) {
			var errorMessage = "After " + timeOutInSeconds + "ms, element failed to be clickable"
			throw new TimeoutException(errorMessage)
		} catch (Exception ex) {
			throw new Exception("Error in waitToBeClickable: " + ex.getMessage())
		}
	}

	/**
	 * Wait for an element to be Clickable
	 * @param element WebElement
	 * @author Shameer
	 */
	def void waitToBeClickable(WebElement element) {
		waitToBeClickable(element, 60)
	}

	/**
	 * Wait for an elements to be Hidden/ Not Visible
	 * @param element WebElement
	 * @param timeOutInSeconds Timeout in seconds
	 * @author Shameer
	 */
	def void waitToBeHiddenElements(WebElement element, int timeOutInSeconds) {
		// TODO "Need to set the Implicit wait to 0 here - verify later" 
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
		var elements = newArrayList
		try {
			// First add the element to a list because ExpectedConditions.invisibilityOfAllElements only accepts a list
			elements.add(element)
			var wait = new WebDriverWait(driver, timeOutInSeconds)
			wait.until(ExpectedConditions.invisibilityOfAllElements(elements))
		} catch (TimeoutException timeoutex) {
			var errorMessage = "After " + timeOutInSeconds + "ms, element is not hidden"
			throw new TimeoutException(errorMessage)
		} catch (Exception ex) {
			throw new Exception("Error in waitToBeHidden: " + ex.getMessage())
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
		}
	}

	/**
	 * Wait for an element to be Hidden/ Not Visible
	 * @param element WebElement
	 * @param timeOutInSeconds Timeout in seconds
	 * @author Shameer
	 */
	def void waitToBeHiddenElement(WebElement element, int timeOutInSeconds) {
		// TODO "Need to set the Implicit wait to 0 here - verify later" 
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
		try {
			var wait = new WebDriverWait(driver, timeOutInSeconds)
			wait.until(ExpectedConditions.invisibilityOf(element))
		} catch (TimeoutException timeoutex) {
			var errorMessage = "After " + timeOutInSeconds + "ms, element is not hidden"
			throw new TimeoutException(errorMessage)
		} catch (Exception ex) {
			throw new Exception("Error in waitToBeHidden: " + ex.getMessage())
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
		}
	}

	/**
	 * Wait for an element to be Hidden/ Not Visible
	 * @param element WebElement
	 * @author Shameer
	 */
	def void waitToBeHidden(WebElement element) {
		waitToBeHiddenElements(element, 60)
	}

	/**
	 * Wait for an element to contain any text
	 * Checks the element.getText().length() != 0
	 * @param element WebElement
	 * @param timeOutInSeconds Timeout in seconds
	 * @author Shameer
	 */
	def void waitForAnyText(WebElement element, int timeOutInSeconds) {
		try {
			var wait = new WebDriverWait(driver, timeOutInSeconds)
			wait.until(new ExpectedCondition<Boolean>() {
				override Boolean apply(WebDriver d) {
					return element.getText().length() != 0
				}
			})
		} catch (TimeoutException timeoutex) {
			var errorMessage = "After " + timeOutInSeconds + "ms, element failed to contain any text"
			throw new TimeoutException(errorMessage)
		} catch (Exception ex) {
			throw new Exception("Error in waitForAnyText: " + ex.getMessage())
		}
	}

	/**
	 * Wait for an elements to be displayed
	 * @param element WebElement
	 * @param timeOutInSeconds Timeout in seconds
	 * @author Shameer
	 */
	def void waitToBeDisplayedElements(List<RemoteWebElement> element, int timeOutInSeconds) {
		// TODO "Need to set the Implicit wait to 0 here - verify later" 
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
		try {
			var wait = new WebDriverWait(driver, timeOutInSeconds)
			wait.until(ExpectedConditions.visibilityOfAllElements(element))
		} catch (TimeoutException timeoutex) {
			var errorMessage = "After " + timeOutInSeconds + "ms, element is not hidden"
			throw new TimeoutException(errorMessage)
		} catch (Exception ex) {
			throw new Exception("Error in waitToBeHidden: " + ex.getMessage())
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
		}
	}

	/**
	 * Wait for an element to hidden the specified text	 * 
	 * @param element WebElement
	 * @param text The text which need to hidden
	 * @param timeOutInSeconds Timeout in seconds
	 * @author Shameer
	 */
	def void waitTextToBeHidden(WebElement element, String text, int timeOutInSeconds) {
		try {
			var wait = new WebDriverWait(driver, timeOutInSeconds)
			wait.until(new ExpectedCondition<Boolean>() {
				override Boolean apply(WebDriver driver) {
					try {
						return !element.getText().equals(text)
					} catch (NoSuchElementException e) {
						// Returns true because the element with text is not present in DOM. The
						// try block checks if the element is present but is invisible.
						return true
					} catch (StaleElementReferenceException e) {
						// Returns true because stale element reference implies that element
						// is no longer visible.
						return true
					}

				}

				override String toString() {
					return String.format("element containing '%s' to no longer be visible: %s", text, element)
				}
			})
		} catch (TimeoutException timeoutex) {
			var errorMessage = "After " + timeOutInSeconds + "ms, element failed to contain any text"
			throw new TimeoutException(errorMessage)
		} catch (Exception ex) {
			throw new Exception("Error in waitForAnyText: " + ex.getMessage())
		}
	}

	/**
	 * Wait for an element to contain given text
	 * @param element WebElement
	 * @param text Text to contain
	 * @param timeOutInSeconds Timeout in seconds
	 * @author Shameer
	 */
	def void waitForContainText(WebElement element, String text, long timeOutInSeconds) {
		try {
			var wait = new WebDriverWait(driver, timeOutInSeconds)
			wait.until(ExpectedConditions.textToBePresentInElement(element, text))
		} catch (TimeoutException timeoutex) {
			var errorMessage = "After " + timeOutInSeconds + "ms, element failed to contain given text: " + text
			throw new TimeoutException(errorMessage)
		} catch (Exception ex) {
			throw new Exception("Error in waitForContainText: " + ex.getMessage())
		}
	}

	/**
	 * Get text value list of List<WebElement>
	 * @param elements List<WebElement> WebElement List
	 * @return list of text values in the element list
	 * @author Shameer
	 */
	def List<String> getTextList(List<WebElement> elements) {
		var textList = newArrayList
		var elementText = ""
		try {
			for (WebElement e : elements) {
				elementText = e.getText().trim()
				textList.add(elementText)
			}
		} catch (Exception ex) {
			throw new Exception("Error in waitForAnyListText: " + ex.getMessage())
		}
		return textList
	}

	/**
	 * Get WebElement containing the given text
	 * @param elements List<WebElement> WebElement List
	 * @param text Text
	 * @return WebElement with the given text
	 * @author Shameer
	 */
	def WebElement getElementByPartialText(List<WebElement> elements, String text) {
		var elementText = ""
		try {
			if (elements !== null && elements.size() > 0) {
				for (WebElement e : elements) {
					elementText = e.getText().trim()
					if (elementText.contains(text)) {
						return e
					}
				}
			}
		} catch (Exception ex) {
			throw new Exception("Error in getElementByPartialText: " + ex.getMessage())
		}
		return null
	}

	/**
	 * Get WebElement matching exact given text
	 * @param elements List<WebElement> WebElement List
	 * @param text Text
	 * @return WebElement with the given text
	 * @author Shameer
	 */
	def WebElement getElementByText(List<WebElement> elements, String text) {
		var WebElement element = null
		var elementText = ""
		try {
			if (elements !== null && elements.size() > 0) {
				for (WebElement e : elements) {
					elementText = e.getText().trim()
					if (elementText == text) {
						element = e
						return e
					}
				}
			}
		} catch (Exception ex) {
			throw new Exception("Error in getElementByText: " + ex.getMessage())

		}

		return element
	}

	/**
	 * Right click on the exact webElement
	 * @param element WebElement
	 * @author Shameer
	 */
	def void rightClick(WebElement element) {
		try {
			var Actions action = new Actions(driver).contextClick(element)
			action.build().perform()
			System.out.println("Successfully Right clicked on the element")
		} catch (StaleElementReferenceException e) {
			throw new StaleElementReferenceException("Element is not attached to the page document " +
				e.getStackTrace())
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Element " + element + " was not found in DOM " + e.getStackTrace())
		} catch (Exception e) {
			throw new Exception("Element " + element + " was not clickable " + e.getStackTrace())
		}
	}

	/**
	 * Select the text from drop down which match with exact given text
	 * @param element WebElement
	 * @param text Text
	 * @author Shameer
	 */
	def selectFromDropdown(WebElement element, String text) {
		var select = new Select(element)
		select.selectByVisibleText(text)
	}

	/**
	 * Select the option from drop down which match with exact given text
	 * @param elements List<WebElement> WebElement List
	 * @param text Text
	 * @author Shameer
	 */
	def void selectFromDropdown(WebElement btn, List<WebElement> optionsList, String text) {
		btn.click()
		var matchingElement = getElementByPartialText(optionsList, text)
		matchingElement.click()
	}

	/**
	 * Get the text box value using 'value' attribute
	 * @param element WebElement
	 * @author Shameer
	 */
	def String GetTextBoxvalue(WebElement element) {
		return element.getAttribute("value")
	}

	/**
	 * Clear the whole text from text box
	 * @param element WebElement
	 * @author Shameer
	 */
	def clearRobustly(WebElement element) {
		try {
			element.clear()

			// Clear robustly
			if (element.GetTextBoxvalue().trim().length() != 0) {
				element.sendKeys(Keys.chord(Keys.CONTROL, "a"))
				element.sendKeys(Keys.chord(Keys.DELETE))
			}

		} catch (Exception e) {
			throw new Exception("Element " + element + " cannot be cleared " + e.getStackTrace())
		}
	}

	/**
	 * Press the ENTER
	 * @param element WebElement
	 * @author Shameer
	 */
	def void pressEnterKey(WebElement element) {
		element.sendKeys(Keys.ENTER)
	}

	/**
	 * Type/SendKeys on element with the given text
	 * @param element WebElement
	 * @param text Text to contain
	 * @author Shameer
	 */
	def void type(WebElement element, String text) {

		element.clear
		element.sendKeys(text)

	}

	/**
	 * Swipe the screen in android paltform
	 * @param startX The x coordinate relative to the start viewport
	 * @param startY The y coordinate relative to the start viewport
	 * @param endX The x coordinate relative to the end viewport
	 * @param endY The y coordinate relative to the end viewport
	 * @author Shameer
	 */
	def void androidSwipe(int startX, int startY, int endX, int endY) throws Exception {
		var TouchActions actions = new TouchActions(driver)
		actions.down(startX, startY).perform()
		Thread.sleep(5000)
		actions.move(endX, endY).perform()
		actions.up(endX, endY).perform()

	}

	/**
	 * Swipe the screen
	 * @param startX The x coordinate relative to the start viewport
	 * @param startY The y coordinate relative to the start viewport
	 * @param endX The x coordinate relative to the end viewport
	 * @param endY The y coordinate relative to the end viewport
	 * @author Shameer
	 */
	def void swipe(int startX, int startY, int endX, int endY) throws Exception {
		var Dimension size = driver.manage().window().getSize()
		var Point screen = new Point(size.getWidth(), size.getHeight())
		var Point swipe_start = new Point(screen.x * startX, screen.y * startY)
		var Point swipe_end = new Point(screen.x * endX, screen.y * endY)
		androidSwipe((swipe_start.x), (swipe_start.y ), (swipe_end.x ), (swipe_end.y ))
		if (Platform.IOS.equals(Platform.IOS)) {
			// iOSSwipe((swipe_start.x as int), (swipe_start.y as int), (swipe_end.x as int), (swipe_end.y as int)) 
		} else {
			androidSwipe((swipe_start.x as int), (swipe_start.y as int), (swipe_end.x as int), (swipe_end.y as int))
		}
	}

	/** 
	 * Check if the element is present in the page
	 * @param element WebElement
	 * @throws InterruptedException
	 * @author Shameer
	 */
	def Boolean isElementPresent(WebElement element) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
		try {
			element.displayed
			return true
		} catch (Exception e) {
			return false
		} finally {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
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
	def String swipe(String deviceID, int startx, int starty, int endx, int endy) {
		return executeAsString(
			"adb -s " + deviceID + " shell input touchscreen swipe " + startx + " " + starty + " " + endx + " " + endy)
	}

	/** 
	 * Single tab on the coordinates
	 * @param deviceID In which device screen need to swipe
	 * @param startx The x coordinate relative to the start viewport
	 * @param starty The y coordinate relative to the start viewport
	 * @author Shameer
	 */
	def String singleTab(String deviceID, int startx, int starty) {
		return executeAsString("adb -s " + deviceID + " shell input tap " + startx + " " + starty)
	}

	/** 
	 * Turn on/off the wifi network
	 * @param deviceID In which device screen need to swipe
	 * @param status The status of the [enable /disable] the wifi network
	 * @author Shameer
	 */
	def String wifiConnection(String deviceID, String status) {
		return executeAsString(
			"adb -s " + deviceID + " shell am broadcast -a io.appium.settings.wifi --es setstatus " + status)
	}

	/** 
	 * Execute the command as String
	 * @param command The Command which need to be executed on command prompt
	 * @throws InterruptedException
	 * @throws IOException
	 * @return command as string
	 * @author Shameer
	 */
	def public String executeAsString(String command) {
		try {
			var Process pr = execute(command)
			var BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()))
			var StringBuilder sb = new StringBuilder()
			var String line
			while ((line = input.readLine()) !== null) {
				if (!line.isEmpty()) {
					sb.append(line)
				}
			}
			input.close()
			pr.destroy()
			return sb.toString()
		} catch (Exception e) {
			throw new RuntimeException("Execution error while executing command" + command, e)
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
	def public Process execute(String command) throws IOException, InterruptedException {
		var List<String> commandP = new ArrayList()
		var String[] com = command.split(" ")
		for (var int i = 0; i < com.length; i++) {
			commandP.add({
				val _rdIndx_com = i
				com.get(_rdIndx_com)
			})
		}
		var ProcessBuilder prb = new ProcessBuilder(commandP)
		var Process pr = prb.start()
		pr.waitFor(10, TimeUnit.SECONDS)
		return pr
	}

	/** 
	 * Open the recent Applications on mobile
	 * @author Shameer
	 */
	def void openRecentsApp() {
		((driver as AndroidDriver<MobileElement>)).pressKeyCode(AndroidKeyCode.HOME)
		((driver as AndroidDriver<MobileElement>)).pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH)
		Thread.sleep(5000) // This is mandotory:Without this ; function might get fail
	}

	/** 
	 * Create a cache manager and cache
	 * @return cache Return the created cache
	 * @author Shameer
	 */
	def createCache() {
		var CacheManager cachManager = CacheManager.getInstance()
		var Cache cache

		if (cachManager.cacheExists("cache")) {
			cache = cachManager.getCache("cache")
			return cache
		} else {
			cachManager.addCache("cache")
			cache = cachManager.getCache("cache")
			return cache
		}

	}

	/** 
	 * Store the value in to cache
	 * @param key The key which holds the value
	 * @param value The value which need to  be cache
	 * @author Shameer
	 */
	def setCache(String key, String value) {
		createCache.put(new Element(key, value))
	}

	/** 
	 * Get the stored cached value by giving key
	 * @param key The key which holds the value
	 * @author Shameer

	 */
	def getCache(String key) {
		var Element ele = createCache.get(key)
		return ele.getObjectValue().toString()
	}

	/** 
	 * Recognize the text presents in the given image.
	 * @param capturedImage the image which need to be extract the text
	 * @return the text present in the image.
	 * @author Shameer
	 */
	def String recognizeImage(String capturedImage) {
		var TextRecognizer textRecognizer = TextRecognizer.getInstance()
		var String reconizedText = ""
		try {

			var BufferedImage image = ImageIO.read(new File(URLDecoder.decode(capturedImage, "UTF-8")))
			reconizedText = textRecognizer.recognizeWord(image)
		} catch (IOException ioException) {
			System.err.println(ioException.getMessage())

		}

		return reconizedText
	}

	/** 
	 * create image  for element using its location and size.This will give image data specific to the WebElement
	 * @param element  Get the entire screenshot from the driver of passed WebElement
	 * @return Return the file object containing image data
	 * @author Shameer
	 */
	def File captureElementPicture(WebElement element) throws Exception {
		// get the WrapsDriver of the WebElement
		var WrapsDriver wrapsDriver = (element as WrapsDriver)
		// get the entire screenshot from the driver of passed WebElement
		var File screen = ((wrapsDriver.getWrappedDriver() as TakesScreenshot)).getScreenshotAs(OutputType.FILE)
		// create an instance of buffered image from captured screenshot
		var BufferedImage img = ImageIO.read(screen)
		// get the width and height of the WebElement using getSize()
		var int width = element.getSize().getWidth()
		var int height = element.getSize().getHeight()
		// create a rectangle using width and height
		var Rectangle rect = new Rectangle(width, height)
		// get the location of WebElement in a Point.
		// this will provide X & Y co-ordinates of the WebElement
		var Point p = element.getLocation()
		// create image  for element using its location and size.
		// this will give image data specific to the WebElement
		var BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height)
		// write back the image data for element in File object
		ImageIO.write(dest, "png", screen)
		// return the File object containing image data
		return screen
	}

	/** 
	 * Read the image data for element in File object
	 * @param imageElement  The image element which need to be read
	 * @return Return the file object containing image data
	 * @author Shameer
	 */
	def readImageWebElement(File imageElement) throws Exception {
		var Tesseract instance = Tesseract.getInstance()

		instance.setTessVariable("tessedit_char_whitelist",
			"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
		instance.setTessVariable("tessedit_char_blacklist", "~ﬂ")
		var File tessDataFolder = LoadLibs.extractTessResources("tessdata")
		// Set the tessdata path
		instance.setDatapath(tessDataFolder.getAbsolutePath())
		return instance.doOCR(imageElement)
	}
	
	/**
	 * Wait till the page load complete
	 * @param elements List<WebElement> WebElement List
	 * @return list of text values in the element list
	 * @author Shameer
	 */
	 
	def void waitForPageLoadComplete(WebDriver driver, int specifiedTimeout) {
		var Wait<WebDriver> wait=new WebDriverWait(driver,specifiedTimeout) 
		wait.until([driver1 | String.valueOf(((driver1 as JavascriptExecutor)).executeScript("return document.readyState")).equals("complete")]) 
	}
	
	/**
	 * Wait 60 seconds till the page load complete
	 * @param elements List<WebElement> WebElement List
	 * @return list of text values in the element list
	 * @author Shameer
	 */
	 
	def void waitForPageLoadComplete(WebDriver driver) {
		waitForPageLoadComplete(driver, 60) 
	}
}
