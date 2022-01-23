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

//Get Catalog ID from The element direct tiles

WebElement direct_catalog_id = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/CatalogTiles/CatalogTile', [('catalogtiles'): 'direct_catalog']), 10)

WebElement catalog_ids = direct_catalog_id
String catalog_id = catalog_ids.getAttribute('data-tile_catalog_id')

println(catalog_id)

int sizeofsubcatalog = WebUI.findWebElements(findTestObject('Object Repository/Catalog/SubCatalogs/ParamertizedSubCatalog - withoutIndex', [('tiletype'): 'direct']), 2).size()
println("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" + sizeofsubcatalog);

GlobalVariable.gv_sizeofsubcatalog = sizeofsubcatalog

for (int m = 1; m <= sizeofsubcatalog; m++) {

        GlobalVariable.lengthh = m

        WebElement direct_sub_catalog_id = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/SubCatalogs/ParamertizedSubCatalog', [('tiletype'): 'direct', ('position'): m]), 10)

        WebElement Sub_catalog_ids = direct_sub_catalog_id
        String sub_catalog_id = Sub_catalog_ids.getAttribute('data-catalog-id')

        GlobalVariable.GV_sub_catalog_id = sub_catalog_id

        println(sub_catalog_id)

        //Get Catalog ID from The element direct tile Ended

        // Redirect To Direct Tiles

        WebUI.click(findTestObject('Object Repository/Catalog/SubCatalogs/ParamertizedSubCatalog', [('tiletype'): 'direct', ('position'): m]))

        WebUI.takeScreenshot()

        //Check Count for Product List

        //Get TicketPriceSchedule ID

        if (WebUI.waitForElementPresent(findTestObject('Catalog/DirectDistributorSection-SelectDistributor/SelectDistributor-Subcatalog', [('distributorid'): distributorid]), 10))

        {

                WebUI.click(findTestObject('Catalog/DirectDistributorSection-SelectDistributor/SelectDistributor-Subcatalog', [('distributorid'): distributorid]))

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

                        GlobalVariable.gv_tps_id = tps_ids
                        GlobalVariable.gv_channel_id = channel_ids

                        def final_channel_id = channel_ids
                        def final_tps_ids = tps_ids

                        KeywordUtil.logInfo("Ticketpriceschedule id for clicked product ${final_tps_ids}")
                        KeywordUtil.logInfo(" channel id for the clicked product ${final_channel_id}")

                        WebUI.click(findTestObject('Object Repository/Catalog/ProductList-RightPanel/EditButton', [('productid'): product_id, ('position'): product_type_length]))
                        WebUI.delay(5)
                        WebUI.takeScreenshot()

                        WebUI.callTestCase(findTestCase('Catalog/Case With Main and Sub Catalog/Catalog_detail-popup-SeperateForDetailPopup-CLC-TPS'), [: ], FailureHandling.CONTINUE_ON_FAILURE)

                        WebUI.callTestCase(findTestCase('Catalog/Case With Main and Sub Catalog/SubCatalogCode-SeperationForDetailPop-SubCatalog'), [: ], FailureHandling.CONTINUE_ON_FAILURE)
						
						WebUI.callTestCase(findTestCase('Test Cases/Catalog/Case With Main and Sub Catalog/Catalog_detail-popup-QueryForTLC'), [: ], FailureHandling.CONTINUE_ON_FAILURE)

                        if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/CancelButton'), 10))

                        {

                                WebUI.click(findTestObject('Object Repository/Catalog/DetailedPop-up/CancelButton'))

                        }

                }

                WebUI.callTestCase(findTestCase('Admin-LeftNavigation/Catalogs-LeftNavigation'), [: ], FailureHandling.CONTINUE_ON_FAILURE)
        }
}