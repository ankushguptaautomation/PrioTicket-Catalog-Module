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
import java.sql.Connection
import java.sql.ResultSet
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.util.KeywordUtil

//Built-In LOG Class
KeywordLogger log = new KeywordLogger()
String message

//DataBase Connectivity
Connection globalConnection = null
ResultSet actorData
globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
log.logInfo("DATABASE CONNECTION IMPLEMENTED")


//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 2 seconds
WebUI.delay(2)

//Getting Session values
String sec_temp=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_distributor_template_id'),2).getAttribute('value')
println("SECONDARY TEMPLATE ID: : "+sec_temp)
log.logInfo("SECONDARY TEMPLATE ID: : "+sec_temp)

String sec_channel=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_distributor_channel_id'),2).getAttribute('value')
println("SECONDARY CHANNEL ID: : "+sec_channel)
log.logInfo("SECONDARY CHANNEL ID: : "+sec_channel)

//Getting Login Reseller ID
String reseller_id=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_login_reseller_id'),2).getAttribute('value')
println("Reseller ID : "+reseller_id)
log.logInfo("Reseller ID : "+reseller_id)

//Getting Distributor ID from DB for Account Export
query_distributor_account_id="SELECT cod_id FROM `qr_codes` where reseller_id="+reseller_id+" and own_supplier_id='0' and cashier_type='1' and sub_catalog_id='0' and template_id="+sec_temp+" and channel_id="+sec_channel+" order by last_modified_at DESC Limit 1"

println(query_distributor_account_id)
log.logInfo("QUERY : "+query_distributor_account_id)

database_account_id= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query_distributor_account_id)

if (CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(database_account_id) == true) 
	{
	
	println("No Distributor Account Level Export Exist on Distributor Tile Account Level Section")
		message="No Distributor Account Level Export Exist on Distributor Tile Account Level Section"
				
		WebUI.takeFullPageScreenshot()
	}

else
{		

	//Getting Count from Database and store in Variable
	 distributor_id = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(database_account_id, 1, 1)
	 println(distributor_id)
	 log.logInfo("Distributor ID for Export : "+distributor_id)

//Select Distributor Tile
WebUI.click(findTestObject('1) Catalog Exports Objects/Distributor Section/distributor_main_catalog'))

//Distributor ID to searh in search box
WebUI.setText(findTestObject('1) Catalog Exports Objects/search_button'), distributor_id)
log.logInfo("Distributor ID for which CSV Exported : "+distributor_id)
WebUI.takeFullPageScreenshot()

WebUI.sendKeys(findTestObject('1) Catalog Exports Objects/search_button'), Keys.chord(Keys.ENTER))

WebUI.delay(5)


//Verify Element Present
if(WebUI.verifyElementPresent(findTestObject('1) Catalog Exports Objects/account_level_export_csv'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
	{
		//Verify element selected is clickable or not
		if(WebUI.verifyElementClickable(findTestObject('1) Catalog Exports Objects/account_level_export_csv'), FailureHandling.CONTINUE_ON_FAILURE)==true)
			{
				WebUI.delay(3)
				//Distributor Export
				WebUI.click(findTestObject('1) Catalog Exports Objects/account_level_export_csv'))
				println("Export Exist on Distributor Account Level and able to export")
				WebUI.takeFullPageScreenshot()
				message="Export Exist on Distributor Account Level and able to export"
				WebUI.delay(5)
				

			}

		else
			{
				println("ERROR:Not Able to Export Distributor Account Level")
				WebUI.takeFullPageScreenshot()
				message="ERROR:Not Able to Export Distributor Account Level"
			}
	}
	
else
	{
		println("No Distributor Account Level Export Exist on Distributor Tile Account Level Section")
		message="No Distributor Account Level Export Exist on Distributor Tile Account Level Section"
				
		WebUI.takeFullPageScreenshot()
	}
}	
	log.logInfo(message)
