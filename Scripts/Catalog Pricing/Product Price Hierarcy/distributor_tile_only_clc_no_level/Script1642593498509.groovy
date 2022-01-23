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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.configuration.RunConfiguration

Connection globalConnection = null
ResultSet actorData

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)

//String value= [112233,12456,45698,126,5]
// for(j=0 ;j<value.size();j++)
// {
//	 String test=value.getAt(j)
//	 print(test)
// }



//DISTRIBUTOR MAIN CATALOGS
CustomKeywords.'com.catalog.filters.filters.catalogselection'()
String combi_type
WebUI.delay(5)

WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/search_product'))
WebUI.setText(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/search_product'), GlobalVariable.search_product_on_catalogs_hirarchy)
WebUI.sendKeys(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/search_product'), Keys.chord(Keys.ENTER))


//CLC DEFAULT PRICE VALUES
String clc_tps_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-tps-id')
println("TPS ID : "+clc_tps_id)

String clc_catalog_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-catalog-id')
println("CATALOG ID : "+clc_catalog_id)

String clc_is_combi_value=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-is_combi')
	if(clc_is_combi_value=='1')
		{
			 combi_type='NORMAL'
		}

		else if(clc_is_combi_value=='2')
			{
				 combi_type='COMBI'
			}
	
			else if(clc_is_combi_value=='3')
				{
					 combi_type='CLLUSTER'
				}
				else
				{
					println(" ")
				}
				
		
println("PRODUCT TYPE LEVEL : "+combi_type)

String clc_product_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/product_id'),2).getText()
println("PRODUCT ID : "+clc_product_id)

String clc_product_name=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/product_name'),2).getText()
println("PRODUCT NAME : "+clc_product_name)

String clc_supplier_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-supplier_id')
println("SUPPLIER ID : "+clc_supplier_id)

String clc_supplier_name=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/supplier_name'),2).getText()
println("SUPPLIER NAME : "+clc_supplier_name)

String clc_list_price=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/list_price'),2).getText()
println("LIST PRICE : "+clc_list_price)

String clc_sale_price=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/sale_price'),2).getText()
println("SALE PRICE : "+clc_sale_price)

String clc_margin=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/margin'),2).getText()
println("MARGIN : "+clc_margin)

String clc_discount=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/discount'),2).getText()
println("DISCOUNT : "+clc_discount)

String clc_purchase_price=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/purchase_price'),2).getText()
println("PURCHASE PRICE : "+clc_purchase_price)

String clc_distributor_fee1=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/distributor_fee'),2).getText()
//Splitting Value from Text
String text = clc_distributor_fee1
String[] parts = text.split(" ", 2)
String clc_distributor_fee = parts[0]

println("DISTRIBUTOR FEE : "+clc_distributor_fee)

String clc_distributor_fee_percentage=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/distributor_percent'),2).getText()
println("DISTRIBUTOR FEE PERCENTAGE : "+clc_distributor_fee_percentage)

WebUI.refresh()

//subcatalog

int distributor_subcatalog=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/Subcatalog_tile_details'),2).size()
println("No.of  Distributor  Subcatalogs:" +distributor_subcatalog);

//List<WebElement> distributor_subcatalog_name = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/Subcatalog_tile_details'), 10)

int distributor_subcatalog_name=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/Subcatalog_tile_details'),2).size()



for (int i = 0; i < distributor_subcatalog_name; i++) 
	{

		String distributor_subcatalog_details=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/Subcatalog_tile_details'),2).get(i).getAttribute('data-catalog-name')
		println("Distributor Subcatalog name : "+distributor_subcatalog_details)
		WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/Subcatalog_tile_details'),2).get(i).click()
		
		
		
		WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/search_product'))
		WebUI.setText(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/search_product'), GlobalVariable.search_product_on_catalogs_hirarchy)
		WebUI.sendKeys(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/search_product'), Keys.chord(Keys.ENTER))
		
		
		String sub_clc_tps_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-tps-id')
		println("TPS ID : "+sub_clc_tps_id)
		
		String sub_clc_catalog_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-catalog-id')
		println("CATALOG ID : "+sub_clc_catalog_id)
		
		String sub_clc_is_combi_value=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-is_combi')
			if(sub_clc_is_combi_value=='1')
				{
					 combi_type='NORMAL'
				}
		
				else if(sub_clc_is_combi_value=='2')
					{
						 combi_type='COMBI'
					}
			
					else if(sub_clc_is_combi_value=='3')
						{
							 combi_type='CLLUSTER'
						}
						else
						{
							println(" ")
						}
						
				
		println("PRODUCT TYPE LEVEL : "+combi_type)
		
		String sub_clc_product_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/product_id'),2).getText()
		println("PRODUCT ID : "+sub_clc_product_id)
		
		String sub_clc_product_name=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/product_name'),2).getText()
		println("PRODUCT NAME : "+sub_clc_product_name)
		
		String sub_clc_supplier_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-supplier_id')
		println("SUPPLIER ID : "+sub_clc_supplier_id)
		
		String sub_clc_supplier_name=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/supplier_name'),2).getText()
		println("SUPPLIER NAME : "+sub_clc_supplier_name)
		
		String sub_clc_list_price=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/list_price'),2).getText()
		println("LIST PRICE : "+sub_clc_list_price)
		
		String sub_clc_sale_price=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/sale_price'),2).getText()
		println("SALE PRICE : "+sub_clc_sale_price)
		
		String sub_clc_margin=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/margin'),2).getText()
		println("MARGIN : "+sub_clc_margin)
		
		String sub_clc_discount=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/discount'),2).getText()
		println("DISCOUNT : "+sub_clc_discount)
		
		String sub_clc_purchase_price=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/purchase_price'),2).getText()
		println("PURCHASE PRICE : "+sub_clc_purchase_price)
		
		String sub_clc_distributor_fee1=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/distributor_fee'),2).getText()
		//Splitting Value from Text
		String text1 = sub_clc_distributor_fee1
		String[] parts1 = text1.split(" ", 2)
		String sub_clc_distributor_fee = parts1[0]
		
		println("DISTRIBUTOR FEE : "+sub_clc_distributor_fee)
		
		String sub_clc_distributor_fee_percentage=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/distributor_percent'),2).getText()
		println("DISTRIBUTOR FEE PERCENTAGE : "+sub_clc_distributor_fee_percentage)
		
		WebUI.refresh()
		WebUI.delay(5)

if(clc_list_price==sub_clc_list_price && clc_sale_price==sub_clc_sale_price && clc_margin==sub_clc_margin && clc_discount==sub_clc_discount && clc_purchase_price==sub_clc_purchase_price && clc_distributor_fee==sub_clc_distributor_fee && clc_distributor_fee_percentage==sub_clc_distributor_fee_percentage)				
	{
		println("PRICES MATCHED")
	}
	
else
	{
		println("PRICES NOT MATCHED---> PRODUCT ID"+sub_clc_product_id+" FOR SUBCATALOG NAME AND ID "+distributor_subcatalog_details+"<-->"+sub_clc_catalog_id)
	}		
		
	}