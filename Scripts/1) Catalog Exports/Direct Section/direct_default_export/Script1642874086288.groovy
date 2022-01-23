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
import com.kms.katalon.core.logging.KeywordLogger


//Built-In LOG Class
KeywordLogger log = new KeywordLogger()
String message

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 2 seconds
WebUI.delay(2)


//Click on pending popup
WebUI.click(findTestObject('1) Catalog Exports Objects/Direct Section/direct_main_catalog'))
log.logInfo("Direct Tile Section Clicked Successfully")

WebUI.takeFullPageScreenshot()

	//Verify Element Present
	if(WebUI.verifyElementPresent(findTestObject('1) Catalog Exports Objects/catalog_exports_object'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
		{
			//Verify element selected is clickable or not
			if(WebUI.verifyElementClickable(findTestObject('1) Catalog Exports Objects/catalog_exports_object'), FailureHandling.CONTINUE_ON_FAILURE)==true)
				{
					WebUI.delay(3)
					//Export File
					WebUI.click(findTestObject('1) Catalog Exports Objects/catalog_exports_object'))
					
					println("Direct Export Exist on Direct Tile Default and able to export")
					message="Direct Export Exist on Direct Tile Default and able to export"
					
					WebUI.takeFullPageScreenshot()
				}
	
			else
				{
					println("ERROR:Not Able to Export Direct CSV on Direct Tile Default Section")
					message="ERROR:Not Able to Export Direct CSV on Direct Tile Default Section"
					
					WebUI.takeFullPageScreenshot()
				}
		}
		

	else
		{
			println("No Direct Export Exist on Direct Tile Default Section")
			message="No Direct Export Exist on Direct Tile Default Section"
			
			WebUI.takeFullPageScreenshot()
		}
		log.logInfo(message)
