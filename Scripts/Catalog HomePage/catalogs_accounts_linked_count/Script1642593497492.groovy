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
import java.sql.Connection
import java.sql.ResultSet
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.util.KeywordUtil


Connection globalConnection = null
ResultSet actorData

globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'),
	null)

//Delay of 5 Seconds
WebUI.delay(5)

//Getting Login Reseller ID
String reseller_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Session Related Objects/getting_login_reseller_id'),2).getAttribute('value')
println("Reseller ID : "+reseller_id)




//DIRECT MAIN CATALOGS AGENTS
String direct_main_catalog_agent = WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/direct_main_catalog_agents_count'),2).getText()
println('Direct Main Catalog Agents Count : ' + direct_main_catalog_agent)

//Getting Agent count from DB for Direct Main Catalogs
query_main_direct_count="SELECT count(*) FROM `qr_codes` where reseller_id="+reseller_id+" and own_supplier_id!='0' and cashier_type='1'"

println(query_main_direct_count)

main_direct_agent_count= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query_main_direct_count)

	//Getting Count from Database and store in Variable
	 database_main_direct_agent = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(main_direct_agent_count, 1, 1)
	 println(database_main_direct_agent)

	 //Verify FrontEnd Count with Database Count
	 if(database_main_direct_agent==direct_main_catalog_agent)
		 {
			 println("Agents Count of Direct Main Catalogs matched" +"\n"+ "FrontEnd Agents Count : "+direct_main_catalog_agent+ "  " +"\n"+ "Database Agents Count : "+database_main_direct_agent)
		 }
	 else
		 {
			 println("Agents Count of Direct Main Catalogs not matched" +"\n"+ "FrontEnd Agents Count : "+direct_main_catalog_agent+ "  " +"\n"+ "Database Agents Count : "+database_main_direct_agent)
	
		 }
	 

		 		 
//DISTRIBUTOR MAIN CATALOGS AGENTS
String distributor_main_catalog_agent = WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/distributor_main_catalog_agents_count'),2).getText()
println('Distributor Main Catalog Agents Count : ' + distributor_main_catalog_agent)

//Getting Agent count from DB for Distributor Main Catalogs
query_main_distributor_count="SELECT count(*) FROM `qr_codes` where reseller_id="+reseller_id+" and own_supplier_id='0' and cashier_type='1'"

println(query_main_distributor_count)

main_distributor_agent_count= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query_main_distributor_count)

	//Getting Count from Database and store in Variable
	 database_main_distributor_agent = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(main_distributor_agent_count, 1, 1)
	 println(database_main_distributor_agent)

	 //Verify FrontEnd Count with Database Count
	 if(database_main_distributor_agent==distributor_main_catalog_agent)
		 {
			 println("Agents Count of Distributor Main Catalogs matched" +"\n"+ "FrontEnd Agents Count : "+distributor_main_catalog_agent+ "  " +"\n"+ "Database Agents Count : "+database_main_distributor_agent)
		 }
	 else
		 {
			 println("Agents Count of Distributor Main Catalogs not matched" +"\n"+ "FrontEnd Agents Count : "+distributor_main_catalog_agent+ "  " +"\n"+ "Database Agents Count : "+database_main_distributor_agent)
	
		 }
		 
		 
//DIRECT SUBCATALOGS AGENTS
		 
List<WebElement> direct_subcatalog = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/direct_subcatalog_agent_count'), 10)
println("LIST COUNT:"+direct_subcatalog)

for(int i = 0; i < direct_subcatalog.size(); i++)
{
	String sub_cat_data=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/direct_subcatalog_agent_count'),2)get(i).getAttribute('class')
	String[] parts = sub_cat_data.split("_", 2)
	String direct_subcatalog_id = parts[1]
	println(direct_subcatalog_id)
	
	String direct_subcatalog_agent_count = WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/direct_subcatalog_agent_count'),2).get(i).getText()
	println(direct_subcatalog_agent_count)
	
	//Getting Agent count from DB for Direct Subcatalogs Catalogs
	query_subcatalog_agent_count="SELECT count(*) FROM `qr_codes` where reseller_id="+reseller_id+" and own_supplier_id!='0' and cashier_type='1' and sub_catalog_id ="+direct_subcatalog_id+" "
	
	println(query_subcatalog_agent_count)
	
	subcatalog_direct_agent_count= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query_subcatalog_agent_count)

	//Getting Count from Database and store in Variable
	database_subcatalog_direct_agent = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(subcatalog_direct_agent_count, 1, 1)
	println(database_subcatalog_direct_agent)
	
	//Verify FrontEnd Count with Database Count
	if(database_subcatalog_direct_agent==direct_subcatalog_agent_count)
		{
			println("Agents Count of Direct SubCatalogs matched" +"\n"+ "FrontEnd Agents Count : "+direct_subcatalog_agent_count+ "  " +"\n"+ "Database Agents Count : "+database_subcatalog_direct_agent)
		}
	else
		{
			println("Agents Count of Direct SubCatalogs Catalogs not matched" +"\n"+ "FrontEnd Agents Count : "+direct_subcatalog_agent_count+ "  " +"\n"+ "Database Agents Count : "+database_subcatalog_direct_agent)
   
		}
	
}		 



//DISTRIBUTOR SUBCATALOGS AGENTS

List<WebElement> distributor_subcatalog = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/distributor_subcatalog_agent_count'), 10)
println("LIST COUNT:"+distributor_subcatalog)

for(int i = 0; i < distributor_subcatalog.size(); i++)
{
String sub_cat_data_dis=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/distributor_subcatalog_agent_count'),2)get(i).getAttribute('class')
String[] parts = sub_cat_data_dis.split("_", 2)
String distributor_subcatalog_id = parts[1]
println(distributor_subcatalog_id)

String distributor_subcatalog_agent_count = WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/distributor_subcatalog_agent_count'),2).get(i).getText()
println(distributor_subcatalog_agent_count)

//Getting Agent count from DB for Distributor Subcatalogs Catalogs
query_subcatalog_dis_agent_count="SELECT count(*) FROM `qr_codes` where reseller_id="+reseller_id+" and own_supplier_id='0' and cashier_type='1' and sub_catalog_id ="+distributor_subcatalog_id+" "

println(query_subcatalog_dis_agent_count)

subcatalog_distributor_agent_count= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query_subcatalog_dis_agent_count)

//Getting Count from Database and store in Variable
database_subcatalog_distributor_agent = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(subcatalog_distributor_agent_count, 1, 1)
println(database_subcatalog_distributor_agent)

//Verify FrontEnd Count with Database Count
if(database_subcatalog_distributor_agent==distributor_subcatalog_agent_count)
{
   println("Agents Count of Distributor SubCatalogs matched" +"\n"+ "FrontEnd Agents Count : "+distributor_subcatalog_agent_count+ "  " +"\n"+ "Database Agents Count : "+database_subcatalog_distributor_agent)
   
   }
else
{
   println("Agents Count of Distributor SubCatalogs Catalogs not matched" +"\n"+ "FrontEnd Agents Count : "+distributor_subcatalog_agent_count+ "  " +"\n"+ "Database Agents Count : "+database_subcatalog_distributor_agent)

}

}