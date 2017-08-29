package com.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Class for Google's home page
 *
 */
public class GoogleHome extends BasePage
{
	
	public GoogleHome(WebDriver d){
		super(d);
	}
	
	private By textBoxSearchBar = By.id("lst-ib");
	private By buttonGoogleSearch = By.name("btnK");
	private By buttonImFeelingLucky = By.name("btnI");
		

	/**
	 * Method to Search for a keyword through Google Search
	 */
	
	public void searchFor(String keyword){
		System.out.println("Searching for: " + keyword);
		waitAndType(textBoxSearchBar,keyword);
	}
	
	/**
	 * Method to click 'Google Search' button
	 */
	
	public void clickGoogleSearch(){
		System.out.println("Click 'Google Search' button");
		waitAndClick(buttonGoogleSearch);
	}
	
	/**
	 * Method to click 'I'm Feeling Lucky' button
	 */
	
	public void clickImFeelingLucky(){
		System.out.println("Click 'I'm Feeling Lucky' button");
		waitAndClick(buttonImFeelingLucky);
	}
}
