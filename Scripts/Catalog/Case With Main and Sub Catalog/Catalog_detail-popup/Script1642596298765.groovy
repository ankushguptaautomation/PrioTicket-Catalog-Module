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

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

WebUI.delay(5)

WebUI.takeScreenshot()

int sizeofmaincatalog = WebUI.findWebElements(findTestObject('Object Repository/Catalog/CatalogTiles/LenghtForDistributorAndMainCatalog'), 2).size()
println("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" + sizeofmaincatalog);


for (int m = 1; m <= sizeofmaincatalog; m++) {

Maintilelength = m
	
if (Maintilelength == 1) {
	
	
	GlobalVariable.catalog_type = "direct_catalog"
}

if (Maintilelength == 2) {
	
	
	GlobalVariable.catalog_type = "distributor_catalog"
}

//Get Catalog ID from The element direct tiles

WebElement direct_catalog_id = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/CatalogTiles/CatalogTile', [('catalogtiles'): GlobalVariable.catalog_type]), 10)

WebElement catalog_ids = direct_catalog_id
String catalog_id = catalog_ids.getAttribute('data-tile_catalog_id')

println(catalog_id)

//Get Catalog ID from The element direct tile Ended

// Redirect To Direct Tiles

WebUI.click(findTestObject('Object Repository/Catalog/CatalogTiles/CatalogTile', [('catalogtiles'): GlobalVariable.catalog_type]))

WebUI.takeScreenshot()

//Check Count for Product List

//Get TicketPriceSchedule ID

String product_id = catalog_product

WebUI.setText(findTestObject('Object Repository/Catalog/Search/InputSearch'), product_id)

WebUI.sendKeys(findTestObject('Object Repository/Catalog/Search/InputSearch'), Keys.chord(Keys.ENTER))

WebUI.delay(5)
WebUI.takeScreenshot()



WebUI.click(findTestObject('Object Repository/Catalog/ProductList-RightPanel/SelectOneProductFromProductList', [('productids'): product_id]))

int sizeoftps = WebUI.findWebElements(findTestObject('Object Repository/Catalog/ProductList-RightPanel/GetTicketPriceScheduleId -Length',[('productid'): product_id]), 2).size()
println("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" + sizeoftps);


println("Before Product Type Loop")

for (int product_type_length = 1; product_type_length <= sizeoftps; product_type_length++) {

println("Entered Under Product Type Loop")

WebElement ticketpriceschedule = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/ProductList-RightPanel/GetTicketPriceScheduleId', [('productid'): product_id, ('position'): product_type_length]), 10)

WebElement tps_id = ticketpriceschedule
WebElement channel_id = ticketpriceschedule
String tps_ids = tps_id.getAttribute('data-ticketpriceschedule_id')
String channel_ids = channel_id.getAttribute('data-channel_id')

def final_channel_id = channel_ids
def final_tps_ids = tps_ids

GlobalVariable.gv_tps_id = tps_ids
GlobalVariable.gv_channel_id = channel_ids

KeywordUtil.logInfo("Ticketpriceschedule id for clicked product ${final_tps_ids}")
KeywordUtil.logInfo(" channel id for the clicked product ${final_channel_id}")

WebUI.click(findTestObject('Object Repository/Catalog/ProductList-RightPanel/EditButton', [('productid'): product_id, ('position'): product_type_length]))
WebUI.delay(1)
WebUI.takeScreenshot()

globalConnection = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

def supplierprices_query1 = "SELECT ticket_gross_price as saleprice FROM channel_level_commission where channel_id = " + "'" + channel_ids + "'" + " and ticketpriceschedule_id = " + "'" + tps_ids + "'" + " and resale_currency_level = '1'"
KeywordUtil.logInfo("${supplierprices_query1}")
queryforclc = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierprices_query1")



if (CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryforclc) == true) {

        KeywordUtil.logInfo("No Records Return From the Channel Level Commission")

        def getpricefromtps = "select ticket_id, id as ticketpriceschedule_id,ROUND(pricetext,2) as ticket_list_price,ROUND(newPrice,2) as ticket_new_price,ROUND(museumCommission,2) as museum_gross_commission from ticketpriceschedule where id = " + "'" + final_tps_ids + "'" + " and deleted = '0'"

        KeywordUtil.logInfo(" Query to get Prices from TPS Table :::: ${getpricefromtps}")

        queryfortps = CustomKeywords.
        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$getpricefromtps")

        'Example: check result set is empty'
        println CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryfortps)

        def ticket_id = CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfortps, 1, 'ticket_id')

        def ticketpriceschedule_id = CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfortps, 1, 'ticketpriceschedule_id')

        def ticket_list_price = CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfortps, 1, 'ticket_list_price')
        Float ticket_list_price_float = Float.parseFloat(ticket_list_price)
        println(ticket_list_price_float)

        def ticket_new_price = CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfortps, 1, 'ticket_new_price')
        Float ticket_new_price_float = Float.parseFloat(ticket_new_price)
        println(ticket_new_price_float)

        def museum_gross_commission = CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfortps, 1, 'museum_gross_commission')
        Float museum_gross_commission_float = Float.parseFloat(museum_gross_commission)
        println(museum_gross_commission_float)

        KeywordUtil.logInfo(" Query to get ticket_id from TPS Table  :::: ${ticket_id}")
        KeywordUtil.logInfo(" Query to get ticketpriceschedule_id from TPS Table :::: ${ticketpriceschedule_id}")
        KeywordUtil.logInfo(" Query to get ticket_list_price from TPS Table :::: ${ticket_list_price}")
        KeywordUtil.logInfo(" Query to get ticket_new_price from TPS Table :::: ${ticket_new_price}")
        KeywordUtil.logInfo(" Query to get museum_gross_commission from TPS Table :::: ${museum_gross_commission}")

        if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'supplier_sale_price']), 10))

        {

                //Get Sale Price for the Supplier Row
                WebElement Saleprice_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'supplier_sale_price']), 10)

                WebElement Saleprice_get1 = Saleprice_get

                String supplier_sale_price1 = Saleprice_get1.getAttribute('value')
                def supplier_sale_price = supplier_sale_price1.replaceAll(",", ".")
                Float supplier_sale_price_float1 = Float.parseFloat(supplier_sale_price)
                println(supplier_sale_price_float1)

                if (ticket_new_price_float == supplier_sale_price_float1) {

                        KeywordUtil.logInfo("Sale Price from the Supplier row matched with view and database")

                } else {

                        KeywordUtil.logInfo("Sale Price from the Supplier row NOT Matched with view and database")

                }

                WebElement resale_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'supplier_resale_price']), 10)

                WebElement resaleprice_get1 = resale_get

                String supplier_resale_price1 = resaleprice_get1.getAttribute('value')
                def supplier_resale_price = supplier_resale_price1.replaceAll(",", ".")
                Float supplier_resale_price_float = Float.parseFloat(supplier_resale_price)
                println(supplier_resale_price_float)

                if (ticket_new_price_float == supplier_resale_price_float) {

                        KeywordUtil.logInfo("ReSale Price from the Supplier row matched with view and database")

                } else {

                        KeywordUtil.logInfo("ReSale Price from the Supplier row NOT Matched with view and database")

                }

                WebElement purchase_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'supplier_purchase_price']), 10)

                WebElement purchaseprice_get1 = purchase_get

                String supplier_purchase_price1 = purchaseprice_get1.getAttribute('value')
                def supplier_purchase_price = supplier_purchase_price1.replaceAll(",", ".")
                Float supplier_purchase_price_float = Float.parseFloat(supplier_purchase_price)
                println(supplier_purchase_price_float)

                if (museum_gross_commission_float == supplier_purchase_price_float) {

                        KeywordUtil.logInfo("Purchase Price from the Supplier row matched with view and database")

                } else {

                        KeywordUtil.logInfo("Purchase Price from the Supplier row NOT Matched with view and database")

                }

        }

        if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']), 10))

        {
                WebElement Saleprice_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']), 10)

                WebElement Saleprice_get1 = Saleprice_get

                String supplier_sale_price1 = Saleprice_get1.getAttribute('value')
                def supplier_sale_price = supplier_sale_price1.replaceAll(",", ".")
                Float supplier_sale_price_float1 = Float.parseFloat(supplier_sale_price)
                println(supplier_sale_price_float1)

                if (ticket_new_price_float == supplier_sale_price_float1) {

                        KeywordUtil.logInfo("Sale Price from the Supplier row matched with view and database")

                } else {

                        KeywordUtil.logInfo("Sale Price from the Supplier row NOT Matched with view and database")

                }

                WebElement resale_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'resale_price']), 10)

                WebElement resaleprice_get1 = resale_get

                String supplier_resale_price1 = resaleprice_get1.getAttribute('value')
                def supplier_resale_price = supplier_resale_price1.replaceAll(",", ".")
                Float supplier_resale_price_float = Float.parseFloat(supplier_resale_price)
                println(supplier_resale_price_float)

                if (ticket_new_price_float == supplier_resale_price_float) {

                        KeywordUtil.logInfo("ReSale Price from the Supplier row matched with view and database")

                } else {

                        KeywordUtil.logInfo("ReSale Price from the Supplier row NOT Matched with view and database")

                }

                WebElement purchase_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'purchase_price']), 10)

                WebElement purchaseprice_get1 = purchase_get

                String supplier_purchase_price1 = purchaseprice_get1.getAttribute('value')
				
				if(supplier_purchase_price1.isEmpty())	{
						
						supplier_purchase_price1 = "0.00"
					}
				
                def supplier_purchase_price = supplier_purchase_price1.replaceAll(",", ".")
                Float supplier_purchase_price_float = Float.parseFloat(supplier_purchase_price)
                println(supplier_purchase_price_float)

                if (museum_gross_commission_float == supplier_purchase_price_float) {

                        KeywordUtil.logInfo("Purchase Price from the Supplier row matched with view and database")

                } else {

                        KeywordUtil.logInfo("Purchase Price from the Supplier row NOT Matched with view and database")

                }

        }

}

WebUI.callTestCase(findTestCase('Catalog/Case With Main and Sub Catalog/Catalog_detail-popup-QueryForCLC'), [: ], FailureHandling.CONTINUE_ON_FAILURE)

if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/CancelButton'), 10))
	
			{

WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/CancelButton'))

			}

}
WebUI.callTestCase(findTestCase('Admin-LeftNavigation/Catalogs-LeftNavigation'), [: ], FailureHandling.CONTINUE_ON_FAILURE)

}