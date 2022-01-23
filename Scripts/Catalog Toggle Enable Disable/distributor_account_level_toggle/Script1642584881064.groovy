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
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import java.sql.Connection
import java.sql.ResultSet

//Built-In LOG Class
KeywordLogger log = new KeywordLogger()
String message
ArrayList<String> list = new ArrayList<String>()

Connection globalConnection = null
ResultSet actorData
globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)


//Getting Login Reseller ID
String reseller_id=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_login_reseller_id'),2).getAttribute('value')
println("Reseller ID : "+reseller_id)
log.logInfo("Reseller ID : "+reseller_id)
	 

//Click on pending popup
WebUI.click(findTestObject('1) Catalog Exports Objects/Distributor Section/distributor_main_catalog'))
log.logInfo("Direct Tile Section Clicked Successfully")

WebUI.delay(5)

//Getting Session values
String primary_temp=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_session_template_id'),2).getAttribute('value')
println("PRIMARY TEMPLATE ID: : "+primary_temp)
log.logInfo("PRIMARY TEMPLATE ID: : "+primary_temp)

String sec_temp=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_distributor_template_id'),2).getAttribute('value')
println("SECONDARY TEMPLATE ID: : "+sec_temp)
log.logInfo("SECONDARY TEMPLATE ID: : "+sec_temp)

String primary_channel=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_session_channel_id'),2).getAttribute('value')
println("PRIMARY CHANNEL ID: : "+primary_channel)
log.logInfo("PRIMARY CHANNEL ID: : "+primary_channel)

String sec_channel=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_distributor_channel_id'),2).getAttribute('value')
println("SECONDARY CHANNEL ID: : "+sec_channel)
log.logInfo("SECONDARY CHANNEL ID: : "+sec_channel)


//Getting Distributor ID from DB for Account Export
query_direct_account_id="SELECT cod_id FROM `qr_codes` where reseller_id="+reseller_id+" and own_supplier_id='0' and cashier_type='1' and sub_catalog_id='0' and template_id="+sec_temp+" and channel_id="+sec_channel+" order by last_modified_at DESC Limit 1"

println(query_direct_account_id)
log.logInfo("QUERY : "+query_direct_account_id)

database_account_id= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query_direct_account_id)

	//Getting Count from Database and store in Variable
	 distributor_id = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(database_account_id, 1, 1)
	 println(distributor_id)
	 log.logInfo("Distributor ID for Export : "+distributor_id)

//Distributor ID to searh in search box
WebUI.setText(findTestObject('1) Catalog Exports Objects/search_button'), distributor_id)
WebUI.delay(5)
log.logInfo("Distributor ID for which CSV Exported : "+distributor_id)
WebUI.takeFullPageScreenshot()

WebUI.sendKeys(findTestObject('1) Catalog Exports Objects/search_button'), Keys.chord(Keys.ENTER))

WebUI.delay(5)


WebUI.setText(findTestObject('Object Repository/Catalog Toggle Enable Disable Objects/search_product'), GlobalVariable.enable_disable_product)
WebUI.sendKeys(findTestObject('Object Repository/Catalog Toggle Enable Disable Objects/search_product'), Keys.chord(Keys.ENTER))
WebUI.takeFullPageScreenshot()

//Toggle Button Action
List<WebElement> toggle = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Catalog Toggle Enable Disable Objects/toggle_button_listing'), 10)

for (int i = 0; i < toggle.size(); i++) 
	{
		WebElement el=toggle.get(i)
		
		if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/Catalog Toggle Enable Disable Objects/toggle_button_listing'), 10,FailureHandling.OPTIONAL)==true) {
			println("Product ID : "+el.getAttribute('data-mec-id')+" is disabled  on Catalogs")
	
			WebUI.findWebElements(findTestObject("Object Repository/Catalog Toggle Enable Disable Objects/toggle_button_listing"),2).get(i).click()
	

			//String data=	log.logInfo("Product ID : "+el.getAttribute('data-mec-id')+" is Enabled and shows correcton Active Filter on Catalogs")
			String product_id=el.getAttribute('data-mec-id')
			//list.add(data)
			println(product_id)
	
			WebUI.delay(5)
			//Query to find is_pos_list in template_level_tickets
			query="select is_pos_list from template_level_tickets where ticket_id="+product_id+" and catalog_id='0' and deleted='0' and template_id ="+sec_temp
			println("Query : "+query)
	
			catalogs= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query)
	
			//Getting pos_list from TLT
			String database_tlt_pos_status = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(catalogs, 1, 1)
			println("POS STATUS IN TLT TABLE : "+database_tlt_pos_status)
	
			if(database_tlt_pos_status=='1')
				{
					println("DATABASE UPDATED AS TLT TABLE POS STATUS UPDATED AS 1 ")
					
				}
			else
				{
					println("DATABASE UPDATED AS TLT TABLE POS STATUS UPDATED AS 0 ")
				}
			
			
			
//			//Query to get Products from Database
//			hotel_list_query="Select cod_id from qr_codes where reseller_id=794 and own_supplier_id!='0' and cashier_type='1'"
//			println("Query : "+hotel_list_query)
//			hotel_list= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, hotel_list_query)
//			
//			
//			//Getting Count from Database and store in Variable
//			String database_hotel_list = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(hotel_list,'cod_id')
//			String relace_database_hotel_list=database_hotel_list.replace('[','(')
//			String relace_database_hotel_list12=relace_database_hotel_list.replace(']',')')
//			println("Hotel LIST IN DATABASE : "+relace_database_hotel_list12)
			
			
			
			
			String query_pos_data="select base349.*, mec.postingEventTitle as product_title,concat('\r\n--TICKET ID :-- ',mec.mec_id,'--HOTEL ID :-- ',pos_hotel_id,'--TLC POS LIST :-- ',ifnull(tlc_is_pos_list,''),'--TLT POS LIST :-- ',tlt_is_pos_list,'--POS POS LIST :-- ',pos_is_pos_list ) as Result from (select * from (select base.*, 'Product on Main Catalog' as type2,tlt.ticket_id as main_linked_ticket_id, tlt.template_id as main_template_id, tlt.is_pos_list as main_template_pos_list from (select pos.hotel_id as pos_hotel_id, tlc.hotel_id as tlc_hotel_id,qc.cod_id as company_hotel_id,qc.template_id as company_template_id, tlt.template_id as template_template_id, pos.is_pos_list as pos_is_pos_list, (tlc.is_pos_list+0-1) as tlc_is_pos_list, tlt.is_pos_list as tlt_is_pos_list, pos.mec_id as pos_ticket_id, tlc.ticket_id as tlc_ticket_id, tlt.ticket_id as tlt_ticket_id,qc.sub_catalog_id as sub_catalog_id, 'If Sub Catalog Assigned' as type from pos_tickets pos left join ticket_level_commission tlc on tlc.hotel_id = pos.hotel_id and tlc.deleted = '0' and tlc.ticket_id = pos.mec_id left join qr_codes qc on pos.hotel_id = qc.cod_id and qc.cashier_type = '1' left join template_level_tickets tlt on if(ifnull(qc.sub_catalog_id, 0) > 0, qc.template_id, qc.sub_catalog_id) = if(tlt.template_id = '0', tlt.catalog_id, tlt.template_id) and pos.mec_id = tlt.ticket_id where qc.cod_id in ("+distributor_id+") and qc.cashier_type = '1') as base left join template_level_tickets tlt on tlt.template_id = base.company_template_id and tlt.ticket_id = base.pos_ticket_id and tlt.deleted = '0' left join qr_codes qrc on qrc.template_id = tlt.template_id and qrc.cod_id = base.pos_hotel_id) as base2 where  (if(tlc_ticket_id IS NOT NULL and pos_is_pos_list != tlc_is_pos_list, 1, 0) = 1 or if((tlc_ticket_id IS NULL and tlt_ticket_id IS NOT NULL) and pos_is_pos_list != tlt_is_pos_list, 1, 0) = 1 or if((tlc_ticket_id is NULL and tlt_ticket_id IS  NULL) and pos_is_pos_list != main_template_pos_list, 1, 0) = 1)) as base349 left join modeventcontent mec on base349.pos_ticket_id = mec.mec_id where mec.deleted = '0' and mec.mec_id="+product_id+";"
			println(query_pos_data)
			
			pos_list_result= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query_pos_data)
			String database_pos_data = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(pos_list_result,'Result')
			message=database_pos_data
			
	
	
		}

else if (WebUI.verifyElementChecked(findTestObject('Object Repository/Catalog Toggle Enable Disable Objects/toggle_button_listing'), 10,FailureHandling.OPTIONAL)==true) 
		{
			println("Product ID : "+el.getAttribute('data-mec-id')+" is disabled  on Catalogs")
	
			WebUI.findWebElements(findTestObject("Object Repository/Catalog Toggle Enable Disable Objects/toggle_button_listing"),2).get(i).click()
	

			//String data=	log.logInfo("Product ID : "+el.getAttribute('data-mec-id')+" is Enabled and shows correcton Active Filter on Catalogs")
			String product_id=el.getAttribute('data-mec-id')
			//list.add(data)
			println(product_id)
	
			WebUI.delay(5)
			//Query to find is_pos_list in template_level_tickets
			query="select is_pos_list from template_level_tickets where ticket_id="+product_id+" and catalog_id='0' and deleted='0' and template_id ="+sec_temp
			println("Query : "+query)
	
			catalogs= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query)
	
			//Getting pos_list from TLT
			String database_tlt_pos_status = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(catalogs, 1, 1)
			println("POS STATUS IN TLT TABLE : "+database_tlt_pos_status)
	
			if(database_tlt_pos_status=='1')
				{
					println("DATABASE UPDATED AS TLT TABLE POS STATUS UPDATED AS 1 ")
				}
			else
				{
					println("DATABASE UPDATED AS TLT TABLE POS STATUS UPDATED AS 0 ")
				}
			
//			//Query to get Products from Database
//			hotel_list_query="Select cod_id from qr_codes where reseller_id=794 and own_supplier_id!='0' and cashier_type='1'"
//			println("Query : "+hotel_list_query)
//			hotel_list= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, hotel_list_query)
//			
//			
//			//Getting Count from Database and store in Variable
//			String database_hotel_list = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(hotel_list,'cod_id')
//			String relace_database_hotel_list=database_hotel_list.replace('[','(')
//			String relace_database_hotel_list12=relace_database_hotel_list.replace(']',')')
//			println("Hotel LIST IN DATABASE : "+relace_database_hotel_list12)
			
			
			
			
			String query_pos_data="select base349.*, mec.postingEventTitle as product_title,concat('\r\n--TICKET ID :-- ',mec.mec_id,'--HOTEL ID :-- ',pos_hotel_id,'--TLC POS LIST :-- ',ifnull(tlc_is_pos_list,''),'--TLT POS LIST :-- ',tlt_is_pos_list,'--POS POS LIST :-- ',pos_is_pos_list ) as Result from (select * from (select base.*, 'Product on Main Catalog' as type2,tlt.ticket_id as main_linked_ticket_id, tlt.template_id as main_template_id, tlt.is_pos_list as main_template_pos_list from (select pos.hotel_id as pos_hotel_id, tlc.hotel_id as tlc_hotel_id,qc.cod_id as company_hotel_id,qc.template_id as company_template_id, tlt.template_id as template_template_id, pos.is_pos_list as pos_is_pos_list, (tlc.is_pos_list+0-1) as tlc_is_pos_list, tlt.is_pos_list as tlt_is_pos_list, pos.mec_id as pos_ticket_id, tlc.ticket_id as tlc_ticket_id, tlt.ticket_id as tlt_ticket_id,qc.sub_catalog_id as sub_catalog_id, 'If Sub Catalog Assigned' as type from pos_tickets pos left join ticket_level_commission tlc on tlc.hotel_id = pos.hotel_id and tlc.deleted = '0' and tlc.ticket_id = pos.mec_id left join qr_codes qc on pos.hotel_id = qc.cod_id and qc.cashier_type = '1' left join template_level_tickets tlt on if(ifnull(qc.sub_catalog_id, 0) > 0, qc.template_id, qc.sub_catalog_id) = if(tlt.template_id = '0', tlt.catalog_id, tlt.template_id) and pos.mec_id = tlt.ticket_id where qc.cod_id in ("+distributor_id+") and qc.cashier_type = '1') as base left join template_level_tickets tlt on tlt.template_id = base.company_template_id and tlt.ticket_id = base.pos_ticket_id and tlt.deleted = '0' left join qr_codes qrc on qrc.template_id = tlt.template_id and qrc.cod_id = base.pos_hotel_id) as base2 where  (if(tlc_ticket_id IS NOT NULL and pos_is_pos_list != tlc_is_pos_list, 1, 0) = 1 or if((tlc_ticket_id IS NULL and tlt_ticket_id IS NOT NULL) and pos_is_pos_list != tlt_is_pos_list, 1, 0) = 1 or if((tlc_ticket_id is NULL and tlt_ticket_id IS  NULL) and pos_is_pos_list != main_template_pos_list, 1, 0) = 1)) as base349 left join modeventcontent mec on base349.pos_ticket_id = mec.mec_id where mec.deleted = '0' and mec.mec_id="+product_id+";"
			println(query_pos_data)
			
			pos_list_result= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query_pos_data)
			String database_pos_data = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(pos_list_result,'Result')
			message=database_pos_data
			
	
		}

else
		{
			println("Product not Exist or Any bug Occured while testing")
		}
	
	}


log.logInfo(message)