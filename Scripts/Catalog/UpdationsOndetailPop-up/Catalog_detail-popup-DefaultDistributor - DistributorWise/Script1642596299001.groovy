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

Maintilelength = 2

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

if (WebUI.waitForElementPresent(findTestObject('Catalog/DirectDistributorSection-SelectDistributor/SelectDistributor-Subcatalog', [('distributorid'): distributorid]), 10))

{

        WebUI.click(findTestObject('Catalog/DirectDistributorSection-SelectDistributor/SelectDistributor-Subcatalog', [('distributorid'): distributorid]))

        if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/distributorAssignedtoSubcatalog'), 10))

        {
                KeywordUtil.logInfo("Distributor Assigned to Sub Catalog So exit with all condition - Distributorid : ${distributorid}")
        } else {

                String product_id = catalog_product

                WebUI.setText(findTestObject('Object Repository/Catalog/Search/InputSearch'), product_id)

                WebUI.sendKeys(findTestObject('Object Repository/Catalog/Search/InputSearch'), Keys.chord(Keys.ENTER))

                WebUI.delay(5)
                WebUI.takeScreenshot()

                WebUI.click(findTestObject('Object Repository/Catalog/ProductList-RightPanel/SelectOneProductFromProductList', [('productids'): product_id]))

                int sizeoftps = WebUI.findWebElements(findTestObject('Object Repository/Catalog/ProductList-RightPanel/GetTicketPriceScheduleId -Length', [('productid'): product_id]), 2).size()
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

                        if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'supplier_sale_price']), 10))

                        {

                                //Get Sale Price for the Supplier Row
                                WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'supplier_sale_price']), '100.00')

                                WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'supplier_resale_price']), '80.00')

                                WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'supplier_purchase_price']), '50.00')

                        }

                        if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']), 10))

                        {

                                WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/Listprice'))
                                WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Listprice'), Keys.chord(Keys.CONTROL + 'Arrow Right'))
                                WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Listprice'), Keys.chord(Keys.CONTROL + 'a'))
                                WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Listprice'), Keys.chord(Keys.BACK_SPACE))
                                WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/Listprice'), '200')

                                WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']))
                                WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']), Keys.chord(Keys.CONTROL + 'Arrow Right'))
                                WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']), Keys.chord(Keys.CONTROL + 'a'))
                                WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']), Keys.chord(Keys.BACK_SPACE))

                                WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']), '200.00')

                                WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'resale_price']))
                                WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'resale_price']), Keys.chord(Keys.CONTROL + 'Arrow Right'))
                                WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'resale_price']), Keys.chord(Keys.CONTROL + 'a'))
                                WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'resale_price']), Keys.chord(Keys.BACK_SPACE))
                                WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'resale_price']), '180.00')

                                if (WebUI.waitForElementClickable(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'purchase_price']), 10))

                                {
                                        WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'purchase_price']))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'purchase_price']), Keys.chord(Keys.CONTROL + 'Arrow Right'))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'purchase_price']), Keys.chord(Keys.CONTROL + 'a'))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'purchase_price']), Keys.chord(Keys.BACK_SPACE))
                                        WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'purchase_price']), '140.00')

                                }
                        }

                        if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'new_newprice']), 10))

                        {

                                if (WebUI.waitForElementVisible(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'new_newprice']), 10))

                                {
                                        //Get Sale Price for the Supplier Row

                                        WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'new_newprice']))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'new_newprice']), Keys.chord(Keys.CONTROL + 'Arrow Right'))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'new_newprice']), Keys.chord(Keys.CONTROL + 'a'))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'new_newprice']), Keys.chord(Keys.BACK_SPACE))
                                        WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'new_newprice']), '300.00')

                                        WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'new_resale_price']))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'new_resale_price']), Keys.chord(Keys.CONTROL + 'Arrow Right'))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'new_resale_price']), Keys.chord(Keys.CONTROL + 'a'))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'new_resale_price']), Keys.chord(Keys.BACK_SPACE))
                                        WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'new_resale_price']), '270.00')

                                        WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'new_purchase_price']))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'new_purchase_price']), Keys.chord(Keys.CONTROL + 'Arrow Right'))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'new_purchase_price']), Keys.chord(Keys.CONTROL + 'a'))
                                        WebUI.sendKeys(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'new_purchase_price']), Keys.chord(Keys.BACK_SPACE))
                                        WebUI.setText(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'new_purchase_price']), '240.00')

                                }

                        }

                        if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/UpdateProductPricing'), 10))

                        {

                                WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/UpdateProductPricing'))

                        }

                        WebUI.delay(5)
                        WebUI.click(findTestObject('Object Repository/Catalog/ProductList-RightPanel/SelectOneProductFromProductList', [('productids'): product_id]))
                }
        }
}
WebUI.callTestCase(findTestCase('Admin-LeftNavigation/Catalogs-LeftNavigation'), [: ], FailureHandling.CONTINUE_ON_FAILURE)