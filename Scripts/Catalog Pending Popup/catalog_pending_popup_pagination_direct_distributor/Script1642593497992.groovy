import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.sql.Driver
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
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
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.WebElement

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)

//Click on Pending Popup
WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pending_popup_button'))
	//Verify Catalog Direct Tab scope
	if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/direct_catalog_pending_popup_scope'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
		{	
			//Getting count of Pagintion
			int paginationcount=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_count_direct_pending_popup'),2).size()
			println("Number of elements:" +paginationcount)

			String page_count=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_count_direct_pending_popup'),2).get(paginationcount-1).getText()
			int page_count_final = Integer.parseInt(page_count)
			println("Number of elements:" +page_count_final)
			
			//Check while next is clickable and page text match with last match
			while(WebUI.verifyElementClickable(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_next_button_direct'), FailureHandling.CONTINUE_ON_FAILURE)==true)
				{
					//Click on Next page
					WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_next_button_direct'))
					println(WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_page_current_direct'),2).getText())
					//getting text of current page
					String page_count1 = WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_page_current_direct'),2).getText()
					int page_count_verify = Integer.parseInt(page_count1)
					if(page_count_final==page_count_verify)	
						{
	
							break
							WebUI.delay(5)
						}
	
				}
		}
	else 
		{
			println("NO MORE PAGINATION")
		}
	


WebUI.refresh()	
	
	
//Click on Pending Popup
WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pending_popup_button'))
	//Verify Catalog Distributor Tab scope
	if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/distributor_catalog_pending_popup_scope'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
		{
			//Getting count of Pagintion
			int paginationcount=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_count_distributor_pending_popup'),2).size()
			println("Number of elements:" +paginationcount)
	
			String page_count=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_count_distributor_pending_popup'),2).get(paginationcount-1).getText()
			int page_count_final = Integer.parseInt(page_count)
			println("Number of elements:" +page_count_final)
			//Check while next is clickable and page text match with last match
			while(WebUI.verifyElementClickable(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_next_button_distributor'), FailureHandling.CONTINUE_ON_FAILURE)==true)
				{
					//Click on Next page
					WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_next_button_distributor'))
					println(WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_page_current_distributor'),2).getText())
					//getting text of current page
					String page_count1 = WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pagination_page_current_distributor'),2).getText()
					int page_count_verify = Integer.parseInt(page_count1)
					if(page_count_final==page_count_verify)
						{
		
							break
							WebUI.delay(5)
						}
		
				}
		}
	else
		{
			println("NO MORE PAGINATION")
		}