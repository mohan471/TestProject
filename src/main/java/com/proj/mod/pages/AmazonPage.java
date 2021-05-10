package com.proj.mod.pages;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonPage {



	@FindBy(id="twotabsearchtextbox")
	private WebElement searchText;

	@FindBy(id="nav-search-submit-button")
	private WebElement searchButon;

	@FindBy(xpath="//*[@id='search']//a[@class='a-link-normal a-text-normal']")
	private List<WebElement> search_result_First;


	@FindBy(xpath="//th[contains(text(),'Manufacturer')]//following-sibling :: td")
	private WebElement manufacturer;

	@FindBy(xpath="//th[contains(text(),'ASIN')]//following-sibling :: td")
	private WebElement asinnumber;

	@FindBy(xpath="//th[contains(text(),'Product Dimensions')]//following-sibling :: td")
	private WebElement productDimensions;

	@FindBy(id="nav-cart-count")
	private WebElement addtoCart;

	@FindBy(id="add-to-cart-button")
	private WebElement addtoCartbutton;
	
	@FindBy(id="hlb-view-cart-announce")
	private WebElement addedtoCartbutton;


	//	WebDriver driver;
	WebDriverWait wait ;
	String currentwindow ;
	Set<String> allWindows ;

	public AmazonPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}

	public void launchURL(WebDriver driver) {

		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();

	}



	public boolean searchItem(WebDriver driver,String searchgivenText) {
		boolean value = false;
		wait= new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(searchText));
		searchText.sendKeys(searchgivenText);
		searchButon.click();
		String text=driver.getTitle();
		if(text.contains(searchgivenText)) {
			value=true;
		}
		return value;

	}

	public boolean clickonFirstProduct(WebDriver driver) {
		boolean value=false;
		currentwindow = driver.getWindowHandle();
		wait.until(ExpectedConditions.visibilityOf(search_result_First.get(1)));
		if(search_result_First.size() > 0) {
			for (WebElement webElement : search_result_First) {
				webElement.click();
				value=true;
				break;
			}
		}else {
			System.out.println("Search results displayed empty");
		}
		return value;
	}

	public String getproductinfoManufacture(WebDriver driver) {
		String manufacturerValue = null;
		manufacturerValue=manufacturer.getText();
		System.out.println("manufacturerValue is "+manufacturerValue);
		return manufacturerValue;
	}

	public void switchToWindow(WebDriver driver) {
		allWindows = driver.getWindowHandles();
		Iterator<String> i = allWindows.iterator();
		while(i.hasNext()){
			String childwindow = i.next();
			if(!childwindow.equalsIgnoreCase(currentwindow)){
				driver.switchTo().window(childwindow);
				System.out.println("The child window is "+childwindow);
			} else {
				System.out.println("There are no children");
			}     
		}
	}

	public String getproductinfoAsin(WebDriver driver) {
		String asinnumberValue = null;
		asinnumberValue=asinnumber.getText();
		System.out.println("asinnumberValue is "+asinnumberValue);
		return asinnumberValue;
	}

	public String getproductinfoproductDimension(WebDriver driver) {
		String productDimensionsValue = null;
		productDimensionsValue=productDimensions.getText();
		System.out.println("productDimensionsValue is "+productDimensionsValue);
		return productDimensionsValue;

	}


	public boolean addToCart(WebDriver driver) {
		boolean cartvalue=false;
		String getcartCurrentValue=addtoCart.getText();
		if(!addtoCartbutton.isEnabled()) {
			System.out.println("Add to cart button is not enabled");

		}else {
			addtoCartbutton.click();
			wait.until(ExpectedConditions.visibilityOf(addedtoCartbutton));
			String getcartincrementValue=addtoCart.getText();
			int currentvalue=Integer.parseInt(getcartCurrentValue);
			int incrementvalue=Integer.parseInt(getcartincrementValue);
			if(incrementvalue>currentvalue) {
				cartvalue=true;
			}
		}
		return cartvalue;

	}
}
