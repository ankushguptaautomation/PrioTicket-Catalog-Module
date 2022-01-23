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

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Count of Direct and Distributor Catalogs Including Subcatalogs
int direct_distributor_catalogs=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/count_direct_distributor_catalogs'),2).size()
println("Number of Direct catalog and Distributor Catalogs including Subcatalogs:" +direct_distributor_catalogs);

//Count of Reseller Catalogs Including Subcatalogs
int reseller_catalogs=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/count_reseller_catalogs'),2).size()
println("Number of Reseller Catalogs including Subcatalogs:" +reseller_catalogs);

//Count of Partner Catalog
int partner_catalogs=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/count_partner_catalogs'),2).size()
println("Number of Partner Catalogs:" +partner_catalogs);

//Total count of all above Catalogs
int total_catalogs=direct_distributor_catalogs+reseller_catalogs+partner_catalogs
println("Total Number of Catalogs:"+total_catalogs)


//Getting String Text from Homepage
String catalog_text_count=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/total_catalog_count_on_text'),2).getText()

//Splitting Value from Text
String Count = catalog_text_count
String[] parts = Count.split(" ", 2)
String catalog_count = parts[0]

//Casting Int to String
String total_catalogs_all=String.valueOf(total_catalogs);

	//Comparing Text value with Total count of catalogs
	if(WebUI.verifyMatch(catalog_count, total_catalogs_all, false, FailureHandling.CONTINUE_ON_FAILURE))
		{
	
			println('Catalog Total Count Matched with Text Value')
		}
	else
		{
			println('Catalog Total Count Not Matched with Text Value')
		}

