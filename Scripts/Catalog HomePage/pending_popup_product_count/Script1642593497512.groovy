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

Connection globalConnection = null
ResultSet actorData

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), 
    null)

//Delay of 5 Seconds
WebUI.delay(5)

//Getting Count of Product from Pending Popup Button
String product_count = WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog HomePage Objects/pending_popup_count'),2).getText()

println('Product Count on Pending Popup : ' + product_count)

//To calculate Current Time
System.out.println(System.currentTimeMillis());

String input = System.currentTimeMillis();     
String current_time = "";     

	//Trim to 10 Digit for current time
	if (input.length() > 10)
		{
			current_time = input.substring(0, 10);
		}
	else
		{
			current_time = input;
		}
 
System.out.println(current_time);


//Getting Primary Template ID
String primary_template=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Session Related Objects/getting_session_template_id'),2).getAttribute('value')
println("Primary Template ID : "+primary_template)

//Getting Login Reseller ID
String reseller_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Session Related Objects/getting_login_reseller_id'),2).getAttribute('value')
println("Reseller ID : "+reseller_id)

//Query to find count of Pending Products in Database
query="select count(*) as pending_product_count from (select (CASE WHEN tlt.publish_catalog != '' THEN tlt.publish_catalog ELSE '0' END ) AS publish_catalog_main,mc.mec_id,tp.default_listing from modeventcontent mc LEFT JOIN template_level_tickets tlt on mc.mec_id = tlt.ticket_id and tlt.template_id ="+primary_template+" and tlt.catalog_id = '0' LEFT JOIN ticketpriceschedule tp on tp.ticket_id = mc.mec_id and tp.deleted = '0' and tp.default_listing = '1' and ( (tp.start_date <="+current_time+" AND tp.end_date >="+current_time+") OR (tp.start_date >"+current_time+")) LEFT JOIN qr_codes qc on mc.cod_id = qc.cod_id where mc.deleted='0' and tp.deleted = '0' and mc.parent_ticket_id = '0' and mc.product_visibility = '1' and (mc.reseller_id ="+reseller_id+" || tlt.template_id ="+primary_template+") GROUP BY mc.mec_id HAVING publish_catalog_main = '0' order by totalClaim Desc, created_on Desc,start_date Desc) as base"

println(query)

	//DataBase Connection
	globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
	actorData= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query)

	//Getting Count from Database and store in Variable
	 database_pending_product_count = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(actorData, 1, 1)
	 println(database_pending_product_count)
	 
	 //Verify FrontEnd Count with Database Count
	 if(database_pending_product_count==product_count)
		 {
			 println("Product Count of Pending popup matched" +"\n"+ "FrontEnd Pending Product Count : "+product_count+ "  " +"\n"+ "Database Pending Product Count : "+database_pending_product_count)
		 }
	 else
		 {
			 println("Product Count of Pending popup not matched" +"\n"+ "FrontEnd Pending Product Count : "+product_count+ "  " +"\n"+ "Database Pending Product Count : "+database_pending_product_count)
	
		 }