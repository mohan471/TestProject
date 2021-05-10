package com.proj.mod.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.proj.mod.pages.AmazonPage;




public class AmazonPageTest {
	
	WebDriver driver;
	
	@BeforeMethod
	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\mohansai.chintana\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void searchProductAndGetDetails() {
		AmazonPage amazonPage = new AmazonPage(driver);
		amazonPage.launchURL(driver);
		boolean titlepage=amazonPage.searchItem(driver, "iphone11");
		Assert.assertTrue(titlepage, "serchtext page is not launched");
		boolean product=amazonPage.clickonFirstProduct(driver);
		Assert.assertTrue(product, "product is not available");
		amazonPage.switchToWindow(driver);
		amazonPage.getproductinfoAsin(driver);
		Assert.assertTrue(amazonPage.getproductinfoManufacture(driver).contains("Apple Inc. or Apple India Private Limited"));
		amazonPage.getproductinfoproductDimension(driver);
		Assert.assertTrue(amazonPage.addToCart(driver), "add to cart is not enabled");
		
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
	
	public static void main(String s[]) {
		AmazonPageTest testobj=new AmazonPageTest();
		testobj.launchBrowser();
		testobj.searchProductAndGetDetails();
		testobj.teardown();
		
	}

}
