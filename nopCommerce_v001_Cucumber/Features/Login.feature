Feature: Login

Scenario: Scucessful Login with Valid Credentails
	Given User Launch Chrome Browser
	When User Opens URL "https://admin-demo.nopcommerce.com/login"
	And User enters Email as "admin@yourstore.com" and Password as "admin"
	And Click on Login
	Then Page Title should be "Dashboard / nopCommerce administration"
	When User click on Log out link
	Then Page Title should be "Your store. Login"
	And close browser
	
Scenario Outline: Scucessful Login with Valid Credentails
	Given User Launch Chrome Browser
	When User Opens URL "https://admin-demo.nopcommerce.com/login"
	And User enters Email as "<UserName>" and Password as "<PassWord>"
	And Click on Login
	Then Page Title should be "Dashboard / nopCommerce administration"
	When User click on Log out link
	Then Page Title should be "Your store. Login"
	And close browser

 Examples:
 |UserName            |PassWord|
 |Admin@gmail.com     |admin12 |
 |admin@yourstore.com|admin|
 |Admin@gmail.com     |admin12 |
