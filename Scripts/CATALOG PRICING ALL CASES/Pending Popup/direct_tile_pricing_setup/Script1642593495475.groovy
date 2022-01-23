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

CustomKeywords.'com.catalogpricing.direct_pending_pricing_listing.search_product'()

WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Tile expand view/list_click_direct'))

List<WebElement> count=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Tile expand view/expand_list'),2)

for(int i=0;i<count.size();i++)
{
	String text = WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Tile expand view/expand_list'),2).get(i).getAttribute('id')
println(text)

WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Edit Pricing/edit_button_click',['tps_value':text]))

WebUI.delay(10)

WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Edit Pricing/update_button'))
WebUI.delay(5)
WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Tile expand view/list_click_direct'))
WebUI.delay(5)
WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Edit Pricing/edit_button_click',['tps_value':text]))

WebUI.delay(10)


String listing_sale_price=WebUI.getText(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Edit Pricing/sale_net_price'))
String relace_listing_sale_price=listing_sale_price.replace(',','.')
float final_sale_price=Float.parseFloat(relace_listing_sale_price);
double sale_price = (double) Math.round(final_sale_price * 100) / 100
println("SALE PRICE ON FRONTEND PRICE POPUP : "+sale_price)


String listing_resale_price=WebUI.getText(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Edit Pricing/resale_net_price'))
String relace_listing_resale_price=listing_resale_price.replace(',','.')
float final_resale_price=Float.parseFloat(relace_listing_resale_price);
double resale_price = (double) Math.round(final_resale_price * 100) / 100
println("RESALE PRICE ON FRONTEND PRICE POPUP : "+resale_price)



String listing_purchase_price=WebUI.getText(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Edit Pricing/purchase_net_price'))
String relace_listing_purchase_price=listing_purchase_price.replace(',','.')
float final_purchase_price=Float.parseFloat(relace_listing_purchase_price);
double purchase_price = (double) Math.round(final_purchase_price * 100) / 100
println("PURCHASE PRICE ON FRONTEND PRICE POPUP : "+purchase_price)



String listing_hotel_price=WebUI.getText(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Edit Pricing/hotel_net_price'))
String relace_listing_hotel_price=listing_hotel_price.replace(',','.')
float final_hotel_price=Float.parseFloat(relace_listing_hotel_price);
double hotel_price = (double) Math.round(final_hotel_price * 100) / 100
println("HOTEL PRICE ON FRONTEND PRICE POPUP : "+hotel_price)

String listing_hgs_price=WebUI.getText(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Edit Pricing/hgs_net_price'))
String relace_listing_hgs_price=listing_hgs_price.replace(',','.')
float final_hgs_price=Float.parseFloat(relace_listing_hgs_price);
double hgs_price = (double) Math.round(final_hgs_price * 100) / 100
println("HGS PRICE PRICE ON FRONTEND PRICE POPUP : "+hgs_price)





WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Edit Pricing/update_button'))
WebUI.delay(5)
WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Tile expand view/list_click_direct'))



}