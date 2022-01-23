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
import org.openqa.selenium.WebElement as WebElement


//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)

//Click on Pending Popup
WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pending_popup_button'))
WebUI.setText(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/search_pending_popup'), GlobalVariable.search_prod_pending)
WebUI.sendKeys(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/search_pending_popup'), Keys.chord(Keys.ENTER))

	//verify direct product list
	if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_direct'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
		{
			//getting product id
			String product=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_direct'),2).getAttribute('data-ticket-id')
			println("PRODUCT ID IN DIRECT CATALOG : "+product)
		}

	else 
		{
			println("PRODUCT NOT EXIST IN DIRECT PENDING POPUP")
		}


	//verify distributor product list
	if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_distributor'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
		{
				//getting product id
				String product=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_distributor'),2).getAttribute('data-ticket-id')
				println("PRODUCT ID IN DISTRIBUTOR CATALOG : "+product)
		}
	
	else
		{
			println("PRODUCT NOT EXIST IN DISTRIBUTOR PENDING POPUP")
		}