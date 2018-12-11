package core.customLocators.utils.elements

import core.customLocators.enums.How
import io.appium.java_client.MobileBy
import java.util.List
import org.openqa.selenium.By
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

import static core.customLocators.utils.common.StringUtils.replaceWithValues

class DynamicElement extends By {
	By locator
	How elementSearchCriteria
	String elementValue

	static final String REPLACE_TOKEN = "?"

	new(How elementSearchCriteria, String elementValue) {
		if (!elementValue.contains(REPLACE_TOKEN)) {
			initElement(elementSearchCriteria, elementValue)
		}
		this.elementSearchCriteria = elementSearchCriteria
		this.elementValue = elementValue

	}

	def final void initElement(How elementSearchCriteria, String elementValue) {

		switch (elementSearchCriteria) {
			case ID: {
				locator = By.id(elementValue)
			}
			case XPATH: {
				locator = By.xpath(elementValue)
			}
			case LINK_TEXT: {
				locator = By.linkText(elementValue)
			}
			case CLASS_NAME: {
				locator = By.className(elementValue)
			}
			case CSS_SELECTOR: {
				locator = By.cssSelector(elementValue)
			}
			case TAG_NAME: {
				locator = By.tagName(elementValue)
			}
			case NAME: {
				locator = By.name(elementValue)
			}
			case PARTIAL_LINK_TEXT: {
				locator = By.partialLinkText(elementValue)
			}
			case UiAUTOMATOR: {
				locator = MobileBy.AndroidUIAutomator(elementValue)
			}
		}
	}

	def DynamicElement updateElement(String... values) {
		initElement(elementSearchCriteria, replaceWithValues(elementValue, REPLACE_TOKEN, values))
		return this
	}

	def By getLocator() {
		return locator
	}

	override List<WebElement> findElements(SearchContext searchContext) {
		return null
	}

}
