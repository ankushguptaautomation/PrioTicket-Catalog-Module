import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.WebElement as WebElement

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)

CustomKeywords.'com.catalog.filters.filters.catalogselection'()

WebUI.delay(5)

//Clicking on Inactive Filter
WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_inactive_button'))

//Count Inactive Products to verify if there is pagination and dropdown selector
int inactive_products = WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/toggle_button_listing'), 
    2).size()

println('No.of Inactive Products:' + inactive_products)

if (inactive_products >= 10) {
    CustomKeywords.'com.catalog.filters.filters.paginationdropdownselector'()
}


List<WebElement> toggle = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/toggle_button_listing'), 10)

for (int i = 0; i < toggle.size(); i++) {
	WebElement el=toggle.get(i)

	if (WebUI.verifyElementNotChecked(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/toggle_button_listing'), 10,FailureHandling.CONTINUE_ON_FAILURE)==true) {
		println("Product ID : "+el.getAttribute('data-mec-id')+" is disabled and shows correcton Inactive Filter on Catalogs")
	}
	
}





int paginationcount=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_catalog_active'),2).size()
println("Number of elements:" +paginationcount)

if(WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_catalog_active'),2).get(paginationcount-1).getText()=="") 
	{
		println("NO MORE PAGINATION")
	}
	else
	{
String page_count=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_catalog_active'),2).get(paginationcount-1).getText()
int page_count_final = Integer.parseInt(page_count)
println("Number of elements:" +page_count_final)



if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_next_button'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
{
while(WebUI.verifyElementClickable(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_next_button'), FailureHandling.CONTINUE_ON_FAILURE)==true)
{

		WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_next_button'))
		println(WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_current_page'),2).getText())
		
		List<WebElement> toggle1 = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/toggle_button_listing'), 10)
		
		for (int i = 0; i < toggle1.size(); i++) {
			WebElement el=toggle1.get(i)

		
		if (WebUI.verifyElementNotChecked(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/toggle_button_listing'), 10,FailureHandling.CONTINUE_ON_FAILURE)==true) {
			println("Product ID : "+el.getAttribute('data-mec-id')+" is Disabled and shows correcton Inactive Filter on Catalogs")
		}
			
		}
		
	String page_count1 = WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_current_page'),2).getText()
	int page_count_verify = Integer.parseInt(page_count1)
	
	
	if(page_count_final==page_count_verify)
	{
		println("NO MORE PAGINATION EXIST")
	
	break
	}
	
}
}

	}
