package com.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.google.common.base.Predicate;

/**
 * Base class for any page object
 * 
 * @author a.a.lopez.gonzalez
 *
 */
public abstract class BasePage {

	protected WebDriver driver;

	protected WebDriverWait wait;
	
	protected SoftAssert assertion;

	public BasePage(WebDriver d) {
		
		driver = d;
		wait = new WebDriverWait(d, 60);
		assertion = new SoftAssert();
		
		waitForLoad();
	}
	
	// START CLICK METHODS
	
	protected void waitAndClick(By locator) {
		getClickableElement(locator).click();
	}
	
	protected void waitAndClick(By locator, int index) {
		getElement(locator, index).click();
	}
	
	protected void waitAndClick(By locator, By sectionLocator, int sectionIndex) {
		WebElement section = getElement(sectionLocator, sectionIndex);
		section.findElement(locator).click();
	}
	
	protected void waitAndClick(By locator, int index, By sectionLocator, int sectionIndex) {
		WebElement section = getElement(sectionLocator, sectionIndex);
		section.findElements(locator).get(index).click();
	}
	
	protected void waitScrollAndClick(By locator) {
		scrollToElement(locator).click();
	}
	
	protected void jsClick(By locator) {
		executeScript("arguments[0].click();", getClickableElement(locator));
	}
	
	protected void jsClickParent(By locator) {
		executeScript("arguments[0].click();", getParentElement(locator));
	}
	
	protected void actionClick(By locator) {
		new Actions(driver).moveToElement(getElement(locator)).click().perform();
	}
	
	// END CLICK METHODS
	
	// START TYPE METHODS
	
	protected void waitAndType(By locator, String text) {
		WebElement element = getClickableElement(locator);
		element.clear();
		element.sendKeys(text);
	}
	
	protected void waitAndType(By locator, int index, String text) {
		WebElement element = getElement(locator, index);
		element.clear();
		element.sendKeys(text);
	}
	
	protected void waitAndType(By locator, By sectionLocator, int sectionIndex, String text) {
		WebElement section = getElement(sectionLocator, sectionIndex);
		WebElement textbox = section.findElement(locator);
		textbox.clear();
		textbox.sendKeys(text);
	}
	
	protected void waitAndSlowType(By locator, String text) {
		WebElement element = getClickableElement(locator);
		element.clear();		
		for (int i = 0; i < text.length(); i++) {
			String s = new StringBuilder().append(text.charAt(i)).toString();
			element.sendKeys(s);
		}
	}
	
	protected void waitAndSlowType(By locator, int index, String text) {
		WebElement element = getElement(locator, index);
		element.clear();		
		for (int i = 0; i < text.length(); i++) {
			String s = new StringBuilder().append(text.charAt(i)).toString();
			element.sendKeys(s);
		}
	}
	
	protected void waitAndSlowType(By locator, By sectionLocator, int sectionIndex, String text) {
		WebElement section = getElement(sectionLocator, sectionIndex);
		WebElement element = section.findElement(locator);
		element.clear();		
		for (int i = 0; i < text.length(); i++) {
			String s = new StringBuilder().append(text.charAt(i)).toString();
			element.sendKeys(s);
		}
	}
	
	protected void waitScrollAndType(By locator, String text) {
		WebElement element = scrollToElement(locator);
		element.clear();
		element.sendKeys(text);
	}
	
	// END TYPE METHODS
	
	// START DROPDOWN METHODS
	
	protected void waitAndSelectDropdown(By locator, String visibleText) {
    	Select dropdown = new Select(getClickableElement(locator));
    	dropdown.selectByVisibleText(visibleText);
    }
	
	protected void waitAndSelectDropdown(By locator, int index, String visibleText) {
    	Select dropdown = new Select(getElement(locator, index));
    	dropdown.selectByVisibleText(visibleText);
    }
	
	protected void waitAndSelectDropdown(By locator, By sectionLocator, 
			int sectionIndex, String visibleText) {
		WebElement section = getElement(sectionLocator, sectionIndex);
    	Select dropdown = new Select(section.findElement(locator));
    	dropdown.selectByVisibleText(visibleText);
    }
	
	protected List<String> getDropdownOptions(By locator) {
    	return getDropdownOptions(getElement(locator));
	}
    
    protected List<String> getDropdownOptions(WebElement element) {
    	waitUntilElementIsVisible(element);
		return getDropdownOptions(new Select(element));
	}
    
    protected List<String> getDropdownOptions(Select select) {
		
		List<String> options = new ArrayList<String>();
		
		for (WebElement elem : select.getOptions()) {
			options.add(elem.getText());
		}
		
		return options;
	}
	
	// END DROPDOWN METHODS
	
	// START CHECKBOX METHODS
	
	protected boolean isSelected(By locator) {
		return getElement(locator).isSelected();
	}
	
	protected boolean isSelected(By locator, int index) {
		return getElement(locator, index).isSelected();
	}
	
	protected void selectCheckBox(By locator) {
    	if (!isSelected(locator)) {
    		log("Click on checkbox to select");
    		waitAndClick(locator);
    	}
    }
	
	protected void selectCheckBox(By locator, int index) {
		if (!isSelected(locator, index)) {
			log("Click on checkbox to select");
			waitAndClick(locator, index);
    	}
    }

    protected void unselectCheckBox(By locator) {
    	if (isSelected(locator)) {
    		log("Click on checkbox to unselect");
    		waitAndClick(locator);
    	}
    }
    
    protected void unselectCheckBox(By locator, int index) {
    	if (isSelected(locator, index)) {
			log("Click on checkbox to unselect");
			waitAndClick(locator, index);
    	}
    }
    
    // END CHECKBOX METHODS
    
    // START RETRIEVE METHODS
    
    protected String waitAndGetText(By locator) {
    	return getElement(locator).getText();
    }
    
    protected String waitAndGetText(By locator, int index) {
    	return getElement(locator, index).getText();
    }
    
    protected String waitAndGetText(By locator, By sectionLocator) {
    	WebElement section = getElement(sectionLocator);
    	return section.findElement(locator).getText();
    }
    
    protected String waitAndGetText(By locator, By sectionLocator, int sectionIndex) {
    	WebElement section = getElement(sectionLocator, sectionIndex);
		return section.findElement(locator).getText();
    }
    
    protected String waitAndGetText(By locator, int index, By sectionLocator, int sectionIndex) {
    	WebElement section = getElement(sectionLocator, sectionIndex);
    	return section.findElements(locator).get(index).getText();
    }
    
    protected List<String> getTextFromElements(By locator) {
    	
    	List<String> list = new ArrayList<String>();
    	
    	for (WebElement element : gatherElements(locator)) {
    		list.add(element.getText());
    	}
    	
    	return list;
    }
    
    protected List<String> getTextFromElements(By locator, By sectionLocator) {
    	
    	List<String> list = new ArrayList<String>();
    	
    	for (WebElement section : gatherElements(sectionLocator)) {    		
    		list.add(section.findElement(locator).getText());
    	}
    	
    	return list;
    }
    
    protected String waitAndGetDropdownText(By locator) {
    	WebElement option = new Select(getElement(locator)).getFirstSelectedOption();
    	return option.getText();
    }
    
    protected String waitAndGetDropdownText(By locator, int index) {
    	WebElement option = 
    			new Select(getElement(locator, index)).getFirstSelectedOption();
    	return option.getText();
    }
    
    protected String waitAndGetDropdownText(By locator, By sectionLocator, int sectionIndex) {
    	WebElement section = getElement(sectionLocator, sectionIndex);
    	WebElement option = new Select(section.findElement(locator)).getFirstSelectedOption();
    	return option.getText();
    }
    
    protected String waitAndGetValue(By locator) {
    	return getElement(locator).getAttribute("value");
    }
    
    protected String waitAndGetValue(By locator, By sectionLocator) {
    	WebElement section = getElement(sectionLocator);
    	return section.findElement(locator).getAttribute("value");
    }
    
    protected String waitAndGetValue(By locator, By sectionLocator, int sectionIndex) {
    	WebElement section = getElement(sectionLocator, sectionIndex);
    	return section.findElement(locator).getAttribute("value");
    }
    
    protected List<String> waitAndGetValues(By locator) {
		List<String> list = new ArrayList<String>();
		for (WebElement element : gatherElements(locator)) {
			list.add(element.getAttribute("value"));
		}
		return list;
	}
    
    protected String waitAndGetDropdownValue(By locator) {
    	WebElement option = new Select(getElement(locator)).getFirstSelectedOption();
    	return option.getAttribute("value");
    }
    
    // END RETRIEVE METHODS
    
    // START SCROLL METHODS
    
    protected void moveToElementWithOffset(WebElement element, int offset) {
    	Actions movement = new Actions(driver).moveToElement(element, 0, offset);
    	movement.perform();
    }
    
    protected WebElement scrollToElement(By locator) {
    	WebElement element = getElement(locator);
    	executeScript("arguments[0].scrollIntoView();", element);
    	return element;
    }
    
    protected void scrollToElementWithOffset(By locator, int offset) {
    	Point location = getElement(locator).getLocation();
    	executeScript("window.scrollTo(" + location.x + "," + (location.y + offset) + ");");
    }
    
    protected WebElement scrollToElement(By locator, int index) {
    	WebElement element = getElement(locator, index);
    	executeScript("arguments[0].scrollIntoView();", element);
    	return element;
    }
    
    protected void scrollToElementWithOffset(By locator, int index, int offset) {
    	Point location = getElement(locator, index).getLocation();
    	executeScript("window.scrollTo(" + location.x + "," + (location.y + offset) + ");");
    }
    
    protected void scrollToElementWithOffset(By locator, By sectionLocator, int sectionIndex, int offset) {
    	WebElement section = getElement(sectionLocator, sectionIndex);
    	Point location = section.findElement(locator).getLocation();
    	executeScript("window.scrollTo(" + location.x + "," + (location.y + offset) + ");");
    }
    
    protected void scrollToTop() {
    	executeScript("window.scrollTo(0, - document.body.scrollHeight)");
    }
    
    // END SCROLL METHODS
    
    // START VERIFICATION METHODS
    
    protected boolean isElementPresent(By locator) {
    	return driver.findElements(locator).size() > 0;
    }
    
    protected boolean isElementPresent(By locator, By sectionLocator) {
    	WebElement section = driver.findElement(sectionLocator);
    	return section.findElements(locator).size() > 0;
    }
    
    protected boolean isElementPresent(By locator, By sectionLocator, int index) {
    	WebElement section = driver.findElements(sectionLocator).get(index);
    	return section.findElements(locator).size() > 0;
    }
    
    /**
     * Validates text of all elements of given locator is ordered alphabetically 
     * @param locator
     * @param ascending
     * @return
     */
	protected boolean validateTextElementsAlphabeticallySorted(By locator, boolean ascending) {
		
		boolean sorted = false;
		
		Iterator<WebElement> elements = gatherElements(locator).iterator();
		String current = null;
		String previous = null;
		
		while (elements.hasNext()) {
			
			current = elements.next().getText();
			
			if (previous != null) {
				log("Comparing previous [" + previous + "] with current [" + current + "]");
			}
			
			if (previous == null) {
				sorted = true;
			} else if (current.compareToIgnoreCase(previous) < 0 && ascending) {
				log("[" + previous + "] was found before [" + current + "]");
				sorted = false;
				break;
			}  else if (current.compareToIgnoreCase(previous) > 0 && !ascending) {
				log("[" + previous + "] was found before [" + current + "]");
				sorted = false;
				break;
			}
			
			previous = current;
		}
		
		return sorted;
	}
	
	/**
	 * Check if text is found in any element for specified locator
	 * @param locator
	 * @param textValue
	 * @param elementName
	 * @return
	 */
	protected boolean elementsContainsTextValue(By locator, String textValue, String elementName) {
		
		List<WebElement> list = gatherElements(locator);
		
		for (int index = 0; index < list.size(); index++) {
			String value = list.get(index).getText();
			if (value != null && value.equals(textValue)) {
				log(elementName + " was found on element with index " + index);
				return true;
			}
		}
		
		return false;
	}
    
	protected void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// END VERIFICATION METHODS
	
	// START WAIT METHODS
	
	protected void waitUntilElementIsVisible(By locator) {
		WebElement element = getElement(locator);
		waitUntilElementIsVisible(element);
	}
	
	protected void waitUntilElementIsVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	protected void waitUntilElementsAreNotVisible(By locator) {
		waitUntilElementsAreNotVisible(gatherElements(locator));
	}
	
	protected void waitUntilElementsAreNotVisible(List<WebElement> elements) {
		wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
	}
	
	protected WebElement waitUntilClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	protected void waitForLoad() {
		Predicate<WebDriver> pageLoaded = 
				d -> ((JavascriptExecutor)d).executeScript(
						"return document.readyState").equals("complete");		
	    waitUntil(pageLoaded);
	}
	
	protected void waitUntilElementNotPresent(By locator) {
		Predicate<WebDriver> elementNotPresent = 
				d -> d.findElements(locator).size() == 0;
	    waitUntil(elementNotPresent);
	}
	
	protected void waitUntilElementPresent(By locator) {
		Predicate<WebDriver> elementPresent = 
				d -> d.findElements(locator).size() > 0;
	    waitUntil(elementPresent);
	}
	
	private void waitUntil(Predicate<WebDriver> value) {
		try {
			//wait.until(value);
		} catch (TimeoutException e) {
			log("Next exception was catched while waiting: " + e.getMessage());
		}
	}
	
	// END WAIT METHODS
	
	// START JAVASCRIPT METHODS
	
	protected Object executeScript(String script, Object... args) {
		return ((JavascriptExecutor)driver).executeScript(script, args);
	}
	
	// END JAVASCRIPT METHODS
	
	// START GETTER METHODS
	
	protected List<WebElement> gatherElements(By locator) {
		waitUntilElementPresent(locator);
		return driver.findElements(locator);
	}
	
	protected List<WebElement> gatherElements(By locator, By sectionLocator) {
		WebElement section = driver.findElement(sectionLocator);
		return section.findElements(locator);
	}
	
	protected List<WebElement> gatherElements(By locator, By sectionLocator, int index) {
		WebElement section = driver.findElements(sectionLocator).get(index);
		return section.findElements(locator);
	}
	
	protected WebElement getElement(By locator, int index) {
		waitUntilElementPresent(locator);
 		return driver.findElements(locator).get(index); 
 	}
	
	protected WebElement getElement(By locator, By sectionLocator, int sectionIndex) {
		WebElement section = getElement(sectionLocator, sectionIndex);
 		return section.findElement(locator);
 	}

	protected WebElement getElement(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	protected WebElement getClickableElement(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	protected WebElement getParentElement(By locator) {
		WebElement element = driver.findElement(locator);
		return (WebElement) executeScript("return arguments[0].parentNode;", element);
	}
	
	// END GETTER METHODS
	
	// START ALERT METHODS
	
	public boolean validateAlertPresentAndDismiss() {
		try 
		{
			Alert alert = driver.switchTo().alert();
			alert.dismiss();			
			returnToDefaultPosition();			
			return true;
		}
		catch (NoAlertPresentException e) {
			return false;
		}
	}
	
	public boolean validateAlertPresentAndAccept() {
		try 
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();			
			returnToDefaultPosition();			
			return true;
		}
		catch (NoAlertPresentException e) {
			return false;
		}
	}
	
	// END ALERT METHODS
	
	// START SWITCH METHODS
	
	public WebDriver returnToDefaultPosition() {
		return driver.switchTo().defaultContent();
	}
	
	public WebDriver switchToFrame(String frame) {
		return driver.switchTo().frame(frame);
	}
	
	public WebDriver switchToFrame(WebElement frame) {
		return driver.switchTo().frame(frame);
	}
	
	// END SWITCH METHODS
	
	// START OTHER METHODS
	
	protected void log(String message) {
		// TODO: DEFINE LOGGING
		//Log.log(driver).info(message);
	}
	
	protected void submit(By locator) {
    	getElement(locator).submit();
    }
	
	protected void printElementLocation(By locator) {
		WebElement elem = getElement(locator);
		log("Element location: " + elem.getLocation().x + "," + elem.getLocation().y);
	}
	
	protected void clearField(By locator) {
		getElement(locator).clear();
	}
	
	public boolean hasFocus(By locator) {
		return hasFocus(driver.findElement(locator));
	}
	
	public boolean hasFocus(WebElement element) {
		return element.equals(driver.switchTo().activeElement());
	}
	
	public void sendKey(Keys key) {
		driver.switchTo().activeElement().sendKeys(key);
	}
	
	//  END OTHER METHODS
}

