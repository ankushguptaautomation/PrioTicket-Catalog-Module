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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import java.sql.Connection
import java.sql.ResultSet

import com.kms.katalon.core.configuration.RunConfiguration

Connection globalConnection = null
ResultSet actorData 
globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

//Built-In LOG Class
KeywordLogger log = new KeywordLogger()
String message
String distributor_subcatalog_id_accounts

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 2 seconds
WebUI.delay(2)
String distributor_subcatalog_id_delete
String database_deleted_status

//getting count of subcatalog
int distributor_subcatalog=WebUI.findWebElements(findTestObject('1) Catalog Exports Objects/Distributor Section/distributor_sub_catalog'),2).size()
println("No.of Distributor Subcatalogs:" +distributor_subcatalog);
log.logInfo("No.of Distributor Subcatalogs:" +distributor_subcatalog)


//To calculate Current Time
println(System.currentTimeMillis())
log.logInfo("Current Time In System : "+System.currentTimeMillis())

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
 
println(current_time)
log.logInfo("Current EPOCH Time : "+current_time)


//Process to Delete Subcatalog Based on the Subcatalog Name and fetching catalog ID
List<WebElement> distributor_subcatalog_name = WebUiCommonHelper.findWebElements(findTestObject('1) Catalog Exports Objects/Distributor Section/distributor_sub_catalog'), 10)


	for (int i = 0; i < distributor_subcatalog_name.size(); i++) 
		{
			WebElement el=distributor_subcatalog_name.get(i)
			String distributor_subcatalog_delete =el.getAttribute('data-catalog-name')
			distributor_subcatalog_id_delete =el.getAttribute('data-catalog-id')
			distributor_subcatalog_id_accounts =el.getAttribute('data-cod-id')
			
			if (distributor_subcatalog_id_accounts =="")
				{
					distributor_subcatalog_id_accounts = '0'
				}
			else
				{
					distributor_subcatalog_id_accounts = distributor_subcatalog_id_accounts;
				}
			
			println("Distributor Subcatalog name : "+el.getAttribute('data-catalog-name'))
			println("Distributor Subcatalog ID : "+el.getAttribute('data-catalog-id'))
			println("Distributor Subcatalog ID Accounts Linked : "+distributor_subcatalog_id_accounts)
	
			if(distributor_subcatalog_delete==GlobalVariable.delete_subcatalog)
				{
					WebUI.findWebElements(findTestObject('Object Repository/Catalog Delete SubCatalog Objects/distributor_more_action_button'),2).get(i).click()
			
					if(WebUI.verifyElementPresent(findTestObject('Object Repository/Catalog Delete SubCatalog Objects/distributor_subcatalog_plus_focus'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
						{

							List<WebElement> distributor_listing = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Catalog Delete SubCatalog Objects/distributor_plus_button_listing'), 10)
							for (int j = 1; j < distributor_listing.size(); j++)
								{
									WebElement eld=distributor_listing.get(j)
									String distributor_subcatalog_delete_button =eld.getText()
									println("Distributor Subcatalog name : "+eld.getText())
						
									if(distributor_subcatalog_delete_button=="Delete")
										{
											
											WebUI.findWebElements(findTestObject('Object Repository/Catalog Delete SubCatalog Objects/distributor_plus_button_listing'),2).get(j).click()
											WebUI.click(findTestObject('Object Repository/Catalog Delete SubCatalog Objects/confirm_delete_button'))
											println("Subcatalog ID which deleted from Catalogs : "+distributor_subcatalog_id_delete)
											
											WebUI.delay(5)
											
											//Query to find count of Pending Products in Database
											query="select is_deleted from catalogs where catalog_id = "+distributor_subcatalog_id_delete
											println("Query : "+query)
														
											qr_codes_query="Select sub_catalog_id from qr_codes where cod_id in ("+distributor_subcatalog_id_accounts+")"
											println("Query : "+qr_codes_query)
											//DataBase Connection
											
											catalogs= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query)
											qr_codes_subcatalog= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, qr_codes_query)
											
											//Getting Count from Database and store in Variable
											database_deleted_status = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(catalogs, 1, 1)
											println("DELETED STATUS IN DATABASE : "+database_deleted_status)
											
												if(database_deleted_status=='1')
													{
														println("DATABASE UPDATED AS CATALOGS TABLE DELETED STATUS UPDATED AS 1 ")
													}
												else
													{
														println("DATABASE NOT UPDATED FOR DELETED ID")
													}
													
													database_subcatalog_value = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(qr_codes_subcatalog, 1, 1)
													println("QRCODES SUBCATALOG ID : "+database_subcatalog_value)
													
													if(database_subcatalog_value=='0')
													{
														println("ACCOUNTS UNLINKED FROM SUBCATALOG ,Account ID's :"+distributor_subcatalog_id_accounts)
													}
													else
													{
														println("ACCOUNTS NOT UNLINKED AFTER DELETE SUBCATALOG")
													}
										}
								}
					
					
						}
				}
			else
				{
					println("NO SUCH CATALOG EXIST AND NOT ABLE TO DELETE")
				}
	
	
		}
	
	//Process To Verify Deleted Catalog ID with Direct Subcatalog Existing Catalog ID
	List<WebElement> distributor_subcatalog_name_after_delete = WebUiCommonHelper.findWebElements(findTestObject('1) Catalog Exports Objects/Distributor Section/distributor_sub_catalog'), 10)
	
		for (int k = 0; k < distributor_subcatalog_name_after_delete.size(); k++)
			{
				WebElement el12=distributor_subcatalog_name_after_delete.get(k)
				String distributor_subcatalog_name1 =el12.getAttribute('data-catalog-name')
				String distributor_subcatalog_id =el12.getAttribute('data-catalog-id')
				println("Direct Subcatalog name : "+el12.getAttribute('data-catalog-name'))
				println("Direct Subcatalog ID : "+el12.getAttribute('data-catalog-id'))
		
				//Creating arraylist
				ArrayList<String> list=new ArrayList<String>()
				list.add(distributor_subcatalog_id);
				
				//Traversing list through Iterator
				Iterator itr=list.iterator();//getting the Iterator
				//check if iterator has the elements
				while(itr.hasNext())
					{
					System.out.println(itr.next());//printing the element and move to next
					if(itr.hasNext()==distributor_subcatalog_id_delete)
						{
							println("CATALOG ID STILL EXIST AFTER DELETION")
							break
						}
					else
						{
							println("CATALOG ID DELETED AFTER DELETED FROM FRONTEND "+distributor_subcatalog_id_delete)
						}
					}
			}

			String primary_temp=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_session_template_id'),2).getAttribute('value')
			println("PRIMARY TEMPLATE ID: : "+primary_temp)
			
			String sec_temp=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_distributor_template_id'),2).getAttribute('value')
			println("SECONDARY TEMPLATE ID: : "+sec_temp)
			
			
			String primary_channel=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_session_channel_id'),2).getAttribute('value')
			println("PRIMARY CHANNEL ID: : "+primary_channel)
			
			String sec_channel=WebUI.findWebElement(findTestObject('Object Repository/Catalog Session Related Objects/getting_distributor_channel_id'),2).getAttribute('value')
			println("SECONDARY CHANNEL ID: : "+sec_channel)

			
			
			//Query to get Products from Database
			product_list_query="select DISTINCT (mec.mec_id) as ticket_id from modeventcontent mec LEFT JOIN qr_codes qc on mec.cod_id=qc.cod_id and qc.service_name=1 LEFT JOIN ticketpriceschedule tp on tp.ticket_id = mec.mec_id and tp.default_listing = '1' and tp.deleted = '0' and ((tp.start_date <= '"+current_time+"' AND tp.end_date >= '"+current_time+"') OR tp.start_date > '"+current_time+"') LEFT JOIN channel_level_commission clc on mec.mec_id=clc.ticket_id and clc.channel_id = "+sec_channel+" and (clc.ticketpriceschedule_id = '' or clc.ticketpriceschedule_id = '0' or clc.ticketpriceschedule_id = tp.id)JOIN template_level_tickets tlt on mec.mec_id = tlt.ticket_id and tlt.template_id = "+sec_temp+" where mec.deleted='0' and mec.parent_ticket_id = '0' and tp.deleted = '0' AND tlt.deleted = '0' and tlt.publish_catalog = '1' and (mec.is_commission_assigned='1' or tp.is_commission_assigned='1') and ( tlt.is_pos_list='1' || tlt.is_pos_list='0' )"
			println("Query : "+product_list_query)
			product_list= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, product_list_query)
			
			
			//Getting Count from Database and store in Variable
			String database_product_list = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(product_list,1)
			String relace_database_product_list=database_product_list.replace('[','(')
			String relace_database_product_list12=relace_database_product_list.replace(']',')')
			println("PRODUCT LIST IN DATABASE : "+relace_database_product_list12)
			
			
			
			
			String query_pos_data="select concat('--Ticket_id-- : ',pos.mec_id,' --Hotel ID-- : ',pos.hotel_id,' --POS PosList-- : ',pos.is_pos_list,' --Template Pos List-- : ',tlt.is_pos_list) as Result, pos.mec_id , pos.hotel_id,pos.is_pos_list, tlt.is_pos_list  from pos_tickets pos LEFT join template_level_tickets tlt on pos.mec_id=tlt.ticket_id  where pos.mec_id in "+relace_database_product_list12+" and pos.hotel_id in ("+distributor_subcatalog_id_accounts+") and tlt.template_id="+sec_temp+" and tlt.catalog_id='0' and tlt.is_pos_list!=pos.is_pos_list"
			println(query_pos_data)
			
			pos_list_result= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, query_pos_data)
			String database_pos_data = CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(pos_list_result,'Result')
			println("POS LIST IN DATABASE : "+database_pos_data)
			
				log.logInfo(database_pos_data)
					
			
			
			