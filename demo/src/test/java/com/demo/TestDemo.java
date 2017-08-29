package com.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class TestDemo {
	
	private WebDriver driver;
	
	@BeforeTest
	public void setUp(){
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Guel\\drivers\\FireFox\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get("http://www.google.com");
	}
	
	@AfterTest
	public void closeBrowser(){
		driver.close();
	}
	
	
	@Test
	
	public void simpleTest(){
		
		GoogleHome gh = new GoogleHome(driver);
		SearchResults sr = new SearchResults(driver);
		
		String keyword = "Selenium";
		
		//Search for a keyword in Google Search
		gh.searchFor(keyword);
		gh.clickGoogleSearch();
		
		//Verify that search retrieve any results
		Assert.assertEquals(sr.verifySearchResults(), true,
				"No Search Result were retrieved for the keyword search: " + keyword);

	}
	
	
}



