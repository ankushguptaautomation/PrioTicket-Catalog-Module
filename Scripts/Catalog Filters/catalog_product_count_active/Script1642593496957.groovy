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

import org.openqa.selenium.By
import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.WebElement

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)

CustomKeywords.'com.catalog.filters.filters.catalogselection'()

WebUI.delay(5)

//Clicking on Active Filter
WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_active_button'))

//Count Active Products to verify if there is pagination and dropdown selector
int active_products = WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/toggle_button_listing'), 
    2).size()

println('No.of Active Products:' + active_products)

if (active_products >= 10) {
    CustomKeywords.'com.catalog.filters.filters.paginationdropdownselector'()
}
int active_products123=0
int active_products12345

int active_products1=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/toggle_button_listing'),2).size()
println("No.of Active Products:"+active_products1)
int total_active_products=active_products1


int paginationcount=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_catalog_active'),2).size()
println("Number of elements:" +paginationcount)

if(WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_catalog_active'),2).get(paginationcount-1).getText()=="") 
	{
		println("Total No of Products"+total_active_products)
		println("NO MORE PAGINATION AND PRODUCT COUNT MATCHED WITH DEFAULT PAGINATION")
	}
	else
	{
String page_count=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_catalog_active'),2).get(paginationcount-1).getText()
int page_count_final = Integer.parseInt(page_count)
println("Number of elements:" +page_count_final)
//
//
//
if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_next_button'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
{
while(WebUI.verifyElementClickable(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_next_button'), FailureHandling.CONTINUE_ON_FAILURE)==true)
{
//
		WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_next_button'))
		println(WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_current_page'),2).getText())
		
 active_products12345=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/toggle_button_listing'),2).size()
	 active_products123=active_products123+active_products12345
	println("Number of elements:" +active_products123)


	
	
			
	String page_count1 = WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_pagination_current_page'),2).getText()
	int page_count_verify = Integer.parseInt(page_count1)
	
	
	if(page_count_final==page_count_verify)
	{
		println("NO MORE PAGINATION EXIST")
	
	break
	}
//	
}
int Totals=total_active_products+active_products123
println("Total No. of Active Products:"+Totals)

String sum=String.valueOf(Totals)
String product_count = WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/product_count_on_catalogs'),2).getText()
if(product_count.contains(sum))
{
	print('Active Product count is Matched and Product Count='+Totals+'    ')
}
else
{
	println("Error in Active Product Filter")
}
}

	}