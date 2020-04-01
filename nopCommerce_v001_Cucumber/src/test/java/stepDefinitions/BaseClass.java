package stepDefinitions;

import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pageObject.AddCustomerPage;
import pageObject.LoginPage;
import pageObject.SearchCustomerPage;

public class BaseClass {

	public WebDriver driver;
	public LoginPage lp;
	public AddCustomerPage acp;
	public SearchCustomerPage scp;
	public Logger logger;
	public Properties prop;
	
	public static String randomestring() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;		
	}
}
