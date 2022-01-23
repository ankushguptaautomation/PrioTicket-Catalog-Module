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
ArrayList<String> list = new ArrayList<String>();
String message

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 2 seconds
WebUI.delay(2)


//Getting Size of all Direct Sub Catalogs
int default_direct_Sub_catalogs=WebUI.findWebElements(findTestObject('1) Catalog Exports Objects/Direct Section/direct_sub_catalog'),2).size()
println("No.of Direct Subcatalogs:" +default_direct_Sub_catalogs);
log.logInfo("No.of Direct Subcatalogs : " +default_direct_Sub_catalogs)
WebUI.takeFullPageScreenshot()

//Fetching Selected Catalogs and getting Default Direct and Distributor Export CSV
for(int i=0;i<default_direct_Sub_catalogs;i++)

	{
		//getting catalog name
		String catalogs_name=WebUI.findWebElements(findTestObject('1) Catalog Exports Objects/Direct Section/direct_sub_catalog'),2).get(i).getAttribute('data-catalog-name')
		println("Catalogs Headings:"+catalogs_name)
		WebUI.takeFullPageScreenshot()
		
		//Verify Element Present
		if(WebUI.verifyElementPresent(findTestObject('1) Catalog Exports Objects/Direct Section/direct_sub_catalog'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
			{
				//Verify element selected is clickable or not
				if(WebUI.verifyElementClickable(findTestObject('1) Catalog Exports Objects/Direct Section/direct_sub_catalog'), FailureHandling.CONTINUE_ON_FAILURE)==true)
					{
						//Delay
						WebUI.delay(5)
						//Click on catalog
						WebUI.findWebElements(findTestObject('1) Catalog Exports Objects/Direct Section/direct_sub_catalog'),2).get(i).click()
						//Delay
						WebUI.delay(5)
						
						//fetching Subcatalog ID
						String catalog_data=WebUI.findWebElements(findTestObject('1) Catalog Exports Objects/Direct Section/direct_sub_catalog'),2).get(i).getAttribute('data-catalog-id')
						list.add(catalog_data)
						
						WebUI.delay(3)
						//Export File from every tile in loop
						WebUI.click(findTestObject('1) Catalog Exports Objects/catalog_exports_object'))
						
						WebUI.takeFullPageScreenshot()
						
						println("Export Exist on Direct Subcatalog Default Tiles and able to export")
						message="Export Exist on Direct Subcatalog Default Tiles and able to export"
						WebUI.back()

					}

				else
					{
						println("ERROR:Not Able to Export Direct Subcatalog Default CSV")
						
						WebUI.takeFullPageScreenshot()
						
						message="ERROR:Not Able to Export Direct Subcatalog Default CSV"
					}
			}
	}
log.logInfo(message)
log.logInfo("Export Exist on Direct Subcatalog Default Tiles and able to export having Catalog ID's : "+list)