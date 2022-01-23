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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)
//select catalog for which we need to create
CustomKeywords.'com.catalog.createcatalog.createSubcatalog.subcatalogSelection'()
//check the count of catalogs + getting titles
int catalog_count1 = WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/get_catalog_name'),2).size()

println('No.of Catalogs including Subcatalogs:' + catalog_count1)

for (int k = 0; k < catalog_count1; k++) 
	{
	String catalogs_name1 = WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/get_catalog_name'),2).get(k).getText()

		List<WebElement> mydata=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/get_catalog_name'),2).get(k).getAttribute('data-catalog-id')
			println(mydata)
			
	println('Catalogs Headings:' + catalogs_name1)

	}

CustomKeywords.'com.catalog.createcatalog.createSubcatalog.subcatalogUtilities'()

WebUI.click(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/create_catalog_button'))

//After create catalog then verify if created or not
int catalog_count = WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/get_catalog_name'),2).size()

println('No.of Catalogs including Subcatalogs:' + catalog_count)

for (int i = 0; i < catalog_count; i++) 
	{
	String catalogs_name = WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/get_catalog_name'),2).get(i).getText()

	println('Catalogs Headings:' + catalogs_name)

	if (catalogs_name == GlobalVariable.create_subcatalog_name) 
		{
		println('Subcatalog Created Successfully')

		break
		}
	}


