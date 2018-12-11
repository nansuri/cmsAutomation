package core.customLocators.utils.elements;

import core.customLocators.enums.How;
import core.customLocators.utils.common.StringUtils;
import io.appium.java_client.MobileBy;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

@SuppressWarnings("all")
public class DynamicElement extends By {
  private By locator;
  
  private How elementSearchCriteria;
  
  private String elementValue;
  
  private final static String REPLACE_TOKEN = "?";
  
  public DynamicElement(final How elementSearchCriteria, final String elementValue) {
    boolean _contains = elementValue.contains(DynamicElement.REPLACE_TOKEN);
    boolean _not = (!_contains);
    if (_not) {
      this.initElement(elementSearchCriteria, elementValue);
    }
    this.elementSearchCriteria = elementSearchCriteria;
    this.elementValue = elementValue;
  }
  
  public final void initElement(final How elementSearchCriteria, final String elementValue) {
    if (elementSearchCriteria != null) {
      switch (elementSearchCriteria) {
        case ID:
          this.locator = By.id(elementValue);
          break;
        case XPATH:
          this.locator = By.xpath(elementValue);
          break;
        case LINK_TEXT:
          this.locator = By.linkText(elementValue);
          break;
        case CLASS_NAME:
          this.locator = By.className(elementValue);
          break;
        case CSS_SELECTOR:
          this.locator = By.cssSelector(elementValue);
          break;
        case TAG_NAME:
          this.locator = By.tagName(elementValue);
          break;
        case NAME:
          this.locator = By.name(elementValue);
          break;
        case PARTIAL_LINK_TEXT:
          this.locator = By.partialLinkText(elementValue);
          break;
        case UiAUTOMATOR:
          this.locator = MobileBy.AndroidUIAutomator(elementValue);
          break;
        default:
          break;
      }
    }
  }
  
  public DynamicElement updateElement(final String... values) {
    this.initElement(this.elementSearchCriteria, StringUtils.replaceWithValues(this.elementValue, DynamicElement.REPLACE_TOKEN, values));
    return this;
  }
  
  public By getLocator() {
    return this.locator;
  }
  
  @Override
  public List<WebElement> findElements(final SearchContext searchContext) {
    return null;
  }
}
