package stepDefinitions;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import pageObject.AddCustomerPage;
import pageObject.LoginPage;
import pageObject.SearchCustomerPage;

public class Steps extends BaseClass {
	
	@Before
	public void setup() throws IOException {
		
		prop = new Properties();
		FileInputStream file = new FileInputStream("config.properties");
		prop.load(file);
		
		logger = Logger.getLogger("NopCommerce");
		PropertyConfigurator.configure("log4j.properties");
		
		String br = prop.getProperty("Browser");
		
		if(br.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", prop.getProperty("ChromePath"));
			driver = new ChromeDriver();}
		
		else if(br.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", prop.getProperty("FireFoxPath"));
			driver = new FirefoxDriver();}
		
		else if(br.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", prop.getProperty("IEPath"));
			driver = new InternetExplorerDriver();}
		logger.info("*********** Launchng browser *********");
		
	}

	@Given("^User Launch Chrome Browser$")
	public void user_Launch_Chrome_Browser() throws Throwable {		
		
		lp = new LoginPage(driver);			
	}

	@When("^User Opens URL \"([^\"]*)\"$")
	public void user_Opens_URL(String url) throws Throwable {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		logger.info("*********** Opening the URL *********");
		driver.get(url);
	}

	@When("^User enters Email as \"([^\"]*)\" and Password as \"([^\"]*)\"$")
	public void user_enters_Email_as_and_Password_as(String userName, String passWord) throws Throwable {
		logger.info("*********** Providing login details *********");
		lp.enterUsername(userName);
		lp.enterPassword(passWord);	

	}

	@When("^Click on Login$")
	public void click_on_Login() throws Throwable {
		logger.info("*********** Started Login *********");
		lp.clickLogin();
		Thread.sleep(3000);
	}

	@Then("^Page Title should be \"([^\"]*)\"$")
	public void page_Title_should_be(String title) throws Throwable {
		if(driver.getPageSource().contains("Login was unsuccessful.")) {
			logger.info("*********** Incorrect credentials entered *********");
			driver.close();
			Assert.assertTrue(false);
		}
		else
			Assert.assertEquals(title, driver.getTitle());
		logger.info("*********** Login Passed *********");
		Thread.sleep(3000);
	}


	@When("^User click on Log out link$")
	public void user_click_on_Log_out_link() throws Throwable {
		logger.info("*********** Clicking on Logout *********");
		lp.clickLogout();
		Thread.sleep(3000);
	}

	@Then("^close browser$")
	public void close_browser() throws Throwable {
		logger.info("*********** Completed the test, closing the browser *********");
		driver.quit();
	}

	//Customers feature step Definitions:

	@Then("^User can view Dashboard$")
	public void user_can_view_Dashboard() throws Throwable {
		acp = new AddCustomerPage(driver);
		logger.info("*********** User can view the Dashboard *********");
		Assert.assertEquals("Dashboard / nopCommerce administration", driver.getTitle());
	}

	@When("^User click on customers Menu Item$")
	public void user_click_on_customers_Menu_Item() throws Throwable {
		logger.info("*********** Opening the Customer Link *********");
		acp.clickOnCustomersMenu();
		Thread.sleep(2000);
		acp.clickOnCustomersMenuItem();	    
	}

	@When("^Click on Add new button$")
	public void click_on_Add_new_button() throws Throwable {
		logger.info("*********** Clicked on the Add New Customer *********");
		acp.clickOnAddnew();
		Thread.sleep(2000);
	}

	@Then("^User can view Add new Customer page$")
	public void user_can_view_Add_new_Customer_page() throws Throwable {
		logger.info("*********** Verifying thr Customer page Title *********");
		Assert.assertEquals("Add a new customer / nopCommerce administration", driver.getTitle());
	}

	@When("^User enter Customer info$")
	public void user_enter_Customer_info() throws Throwable {
		logger.info("*********** Adding New Customer *********");
		String email = randomestring()+"@gmail.com";
		acp.setEmail(email);
		acp.setPassword("test123");
		acp.setFirstname(randomestring());
		acp.setLastname(randomestring());
		acp.setGender("Male");
		acp.setDOB("7/05/1985");
		acp.setCompanyname(randomestring());
		acp.setCustomerRoles("Guests");
		acp.setManagerOfVendor("Vendor 1");
		acp.setAdminComment(randomestring());

	}

	@When("^Click on Save Button$")
	public void click_on_Save_Button() throws Throwable {
		logger.info("*********** Clicked on the Save Button *********");
		acp.clickOnSave();
	}

	@Then("^User can view confirmaion message \"([^\"]*)\"$")
	public void user_can_view_confirmaion_message(String conMessage) throws Throwable {
		Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains(conMessage));
	}

	@When("^Enter Customer Email$")
	public void enter_Customer_Email() throws Throwable {
		logger.info("*********** Searching customer by Email *********");
		scp = new SearchCustomerPage(driver);
		Thread.sleep(3000);
		scp.setEmail("admin@yourStore.com");

	}

	@Then("^Click on search button$")
	public void click_on_search_button() throws Throwable {
		logger.info("*********** Clicked on the search Button *********");
		scp.clickSearch();
		Thread.sleep(3000);
	}

	@Then("^User should find the customer email in the search tble$")
	public void user_should_find_the_customer_email_in_the_search_tble() throws Throwable {
		System.out.println("Number of rows in the table is: "+scp.getRowNum());
		boolean status = scp.searchCustomerByEmail("admin@yourStore.com");
		Assert.assertEquals(true, status);
	}
	
	@When("^Enter Customer Firstname$")
	public void enter_Customer_Firstname() throws Throwable {
		scp = new SearchCustomerPage(driver);
		scp.setFirstname("John");	    
	}

	@When("^Enter Customer Lastname$")
	public void enter_Customer_Lastname() throws Throwable {
		scp.setLasttname("Smith");	    
	}

	@Then("^User should find the Name in the search table$")
	public void user_should_find_the_Name_in_the_search_table() throws Throwable {
		logger.info("*********** Searching customer by Name *********");
	    boolean status = scp.searchCustomerByName("John Smith");
	    Assert.assertEquals(true, status);
	}


}