package com.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Class for Google's home page
 *
 */
public class SearchResults extends BasePage 
{

	public SearchResults(WebDriver d){
		super(d);
	}
	
	private By labelSearchResults = 
			By.xpath("//h2[contains(text(),'Resultados de Búsqueda')]");
	
	
	/**
	 * Method to validate if search results were displayed 
	 */
	
	public boolean verifySearchResults(){				
		boolean isDisplayed = 
				isElementPresent(labelSearchResults);
		return isDisplayed;
	}
}
