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

KeywordLogger log = new KeywordLogger()
ArrayList<String> list_catalog_name = new ArrayList<String>();

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)

//Getting Catalogs count
int catalog_count=WebUI.findWebElements(findTestObject('Object Repository/4) Catalog Filters Objects/catalog_tile_click'),2).size()
println("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" +catalog_count);
log.logInfo("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" +catalog_count)

WebUI.takeFullPageScreenshot()

//Fetching Catalogs and Iterate to all catalogs one by one to check the filters
for(int i=0;i<catalog_count;i++)

{
	String catalogs_name=WebUI.findWebElements(findTestObject('Object Repository/4) Catalog Filters Objects/catalog_tile_click'),2).get(i).getText();
	println("Catalogs Headings:"+catalogs_name)
	list_catalog_name.add(catalogs_name)
	
		println('Selected Catalog Verified')
		WebUI.findWebElements(findTestObject('Object Repository/4) Catalog Filters Objects/catalog_tile_click'),2).get(i).click()
		println('Catalog '+catalogs_name+' Clicked')
		WebUI.takeFullPageScreenshot()
		
		WebUI.delay(4)
		
		//Clicking on Active Filter
		WebUI.click(findTestObject('Object Repository/4) Catalog Filters Objects/catalog_active_button'))
		println("Active Filters Working for "+catalogs_name+" Catalog")
		WebUI.takeFullPageScreenshot()
		
		WebUI.delay(3)
		
		//Clicking on Inactive Filter
		WebUI.click(findTestObject('Object Repository/4) Catalog Filters Objects/catalog_inactive_button'))
		println("Inactive Filters Working for "+catalogs_name+" Catalog")
		WebUI.takeFullPageScreenshot()
		
		WebUI.delay(3)
		
		//Clicking on All Filter
		WebUI.click(findTestObject('Object Repository/4) Catalog Filters Objects/catalog_all_button'))
		println("All Filters Working for "+catalogs_name+" Catalog")
		WebUI.takeFullPageScreenshot()
		
		WebUI.delay(3)
		
		WebUI.refresh()
}

log.logInfo("Catalogs which were selected to test Filters : "+list_catalog_name)
