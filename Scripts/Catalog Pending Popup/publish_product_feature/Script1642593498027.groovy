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
import java.sql.Connection
import java.sql.ResultSet

import com.kms.katalon.core.configuration.RunConfiguration
Connection globalConnection = null
ResultSet actorData

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)

String product


//Click on Pending Popup
WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pending_popup_button'))
WebUI.setText(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/search_pending_popup'), GlobalVariable.search_prod_pending)
WebUI.sendKeys(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/search_pending_popup'), Keys.chord(Keys.ENTER))



//getting product id
product1=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_direct'),2).getAttribute('data-ticket-id')
println("PRODUCT ID IN DIRECT CATALOG : "+product1)

String primary_temp=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Session Related Objects/getting_session_template_id'),2).getAttribute('value')
println("PRIMARY TEMPLATE ID: : "+primary_temp)

String sec_temp=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Session Related Objects/getting_distributor_template_id'),2).getAttribute('value')
println("SECONDARY TEMPLATE ID: : "+sec_temp)

//Query to find publish catalog in DB
primary_db_pub="select publish_catalog from template_level_tickets where template_id="+primary_temp+" and ticket_id="+product1
println(primary_db_pub)

sec_db_pub="select publish_catalog from template_level_tickets where template_id="+sec_temp+" and ticket_id="+product1
println(sec_db_pub)

//DataBase Connection
globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
primary_publish_data= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, primary_db_pub)
sec_publish_data= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, sec_db_pub)


//Publish catalog in DB
final_publish_catalog_value_pri = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(primary_publish_data, 1, 1)
println("PUBLISH CATALOG VALUE IN DATABASE FOR PRIMARY TEMPLATE : "+final_publish_catalog_value_pri)

final_publish_catalog_value_sec = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(sec_publish_data, 1, 1)
println("PUBLISH CATALOG VALUE IN DATABASE FOR SECONDARY TEMPLATE : "+final_publish_catalog_value_sec)

	//verify direct product list
	if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_direct'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
		{
			//getting product id
			product=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_direct'),2).getAttribute('data-ticket-id')
			println("PRODUCT ID IN DIRECT CATALOG : "+product)
			
			WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_direct'))
			//product type list count
			List<WebElement> product_type_count = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_type_list_button_direct'), 10)
			println("PRODUCT TYPE COUNT:"+product_type_count)
			
			for(int i = 0; i < product_type_count.size(); i++)
			{
				//update prices for every type
				String type_id=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_type_list_button_direct'),2).get(i).getAttribute("data-ticketpriceschedule_id")
				println("TYPE ID:"+type_id)
				WebUI.delay(6)
				WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_type_list_button_direct'),2).get(i).click()
				WebUI.delay(6)
				WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/update_pricing_button'))
				WebUI.delay(6)
				WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_direct'))
			}
			
		}

	else
		{
			println("PRODUCT NOT EXIST IN DIRECT PENDING POPUP")
		}


	//verify distributor product list
	if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_distributor'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
		{
				//getting product id
				product=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_distributor'),2).getAttribute('data-ticket-id')
				println("PRODUCT ID IN DISTRIBUTOR CATALOG : "+product)
				
				WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_distributor'))
				//product type list count
				List<WebElement> product_type_count = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_type_list_button_distributor'), 10)
				println("PRODUCT TYPE COUNT:"+product_type_count)
				
				for(int i = 0; i < product_type_count.size(); i++)
				{
					//update prices for every type
					String type_id=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_type_list_button_distributor'),2).get(i).getAttribute("data-ticketpriceschedule_id")
					println("TYPE ID:"+type_id)
					WebUI.delay(6)
					WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_type_list_button_distributor'),2).get(i).click()
					WebUI.delay(6)
					WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/update_pricing_button'))
					WebUI.delay(6)
					WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_distributor'))
				}
				
		}
	
	else
		{
			println("PRODUCT NOT EXIST IN DISTRIBUTOR PENDING POPUP")
		}
		
		WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/product_list_distributor'))
		
		
		
WebUI.delay(10)
	//verify checkbox + publish product
	if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pending_popup_product_checkbox'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
		{
			WebUI.check(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pending_popup_product_checkbox'))
			WebUI.delay(5)
			WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pending_popup_publish_button'))
		
			
//Query to find publish catalog in DB
primary_db_pub="select publish_catalog from template_level_tickets where template_id="+primary_temp+" and ticket_id="+product1
println(primary_db_pub)

sec_db_pub="select publish_catalog from template_level_tickets where template_id="+sec_temp+" and ticket_id="+product1
println(sec_db_pub)

//DataBase Connection
globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
primary_publish_data= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, primary_db_pub)
sec_publish_data= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, sec_db_pub)


//Publish catalog in DB
final_publish_catalog_value_pri = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(primary_publish_data, 1, 1)
println("PUBLISH CATALOG VALUE IN DATABASE FOR PRIMARY TEMPLATE : "+final_publish_catalog_value_pri)

final_publish_catalog_value_sec = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(sec_publish_data, 1, 1)
println("PUBLISH CATALOG VALUE IN DATABASE FOR SECONDARY TEMPLATE : "+final_publish_catalog_value_sec)


		//Verify publish catalog in DB	
		if(final_publish_catalog_value_pri=='1' && final_publish_catalog_value_sec=='1')
			{
				println("PRODUCT PUBLISHED")	
			}
		else 
			{
				println("PRODUCT NOT PUBLISHED")
			}
			
		}
	else
		{
			println("All TYPE OF PRODUCT ID:"+product+ "PRICES NOT SETUP")
		}