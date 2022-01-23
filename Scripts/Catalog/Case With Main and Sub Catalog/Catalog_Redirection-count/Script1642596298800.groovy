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

        WebElement direct_catalog_id = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/CatalogTiles/CatalogTile', [('catalogtiles'): GlobalVariable.catalog_type]), 10)

        WebElement catalog_ids = direct_catalog_id
        String catalog_id = catalog_ids.getAttribute('data-tile_catalog_id')

        println(catalog_id)

        //Get Catalog ID from The element direct tile Ended

        // Redirect To Direct Tiles

        WebUI.click(findTestObject('Object Repository/Catalog/CatalogTiles/CatalogTile', [('catalogtiles'): GlobalVariable.catalog_type]))

        WebUI.takeScreenshot()

        //Check Count for Product List
        int ProductListCount1 = WebUI.findWebElements(findTestObject('Object Repository/Catalog/ProductList-RightPanel/ProductListTr'), 2).size()

        def ProductListCountPrint = ProductListCount1

        KeywordUtil.logInfo("Product List Count ${ProductListCountPrint}")

        //Check Count of Pagination

        if (ProductListCountPrint > 9) {

                PaginationCount = WebUI.getText(findTestObject('Object Repository/Catalog/ProductList-RightPanel/PaginationMaxNumber'))

                int PaginationCountPrint = Integer.parseInt(PaginationCount)

                KeywordUtil.logInfo("Pagination Count ${PaginationCountPrint}")

                //Loop to count product on each pagination and get total sun of product lists

                int intitalvalue = 0

                for (int i = 0; i < PaginationCountPrint; i++) {

                        int ProductListCount = WebUI.findWebElements(findTestObject('Object Repository/Catalog/ProductList-RightPanel/ProductListTr'), 2).size()

                        //def productListSum = (1 + ProductListCount - 1)

                        intitalvalue = intitalvalue + ProductListCount

                        println("My product List Count get from the pagination : " + intitalvalue)

                        def sumofproducts = intitalvalue

                        KeywordUtil.logInfo("${sumofproducts}")

                        if (i + 1 == PaginationCountPrint)

                                break
                        WebUI.click(findTestObject('Object Repository/Catalog/ProductList-RightPanel/PaginationNextButton'))

                        println("Pagination Count Run For" + i)
                }

                ///Loop Ended

                /// Count of Products Shown in the preview need to get

                countfromview = WebUI.getText(findTestObject('Object Repository/Catalog/ProductList-RightPanel/PaginationTotalProductCount'))
                def paginationcount = countfromview

                KeywordUtil.logInfo("${paginationcount}")

                String productscount = countfromview

                String[] parts = productscount.split("of", 2)

                String part1 = parts[0]
                String part2 = parts[1]
                //String part3 = parts[2]

                println(part1)
                println(part2)

                String[] partcount = part2.split("entries", 2)

                String partcountfinal = partcount[0]
                def finalcountproducts = partcountfinal

                println("Count of Products in the right section" + partcountfinal)

                KeywordUtil.logInfo("Count of Products in the right section ${finalcountproducts}")

                int ProductcountfinalInteger = Integer.parseInt(finalcountproducts.trim())
				
				GlobalVariable.ProductcountfinalInteger1 = ProductcountfinalInteger
				
                println(ProductcountfinalInteger)

                /// Count of Products Shown in the preview need to get ended

                /// Match each coubt product on pagination and total colunt shown at the footer

                if (ProductcountfinalInteger == intitalvalue) {

                        KeywordUtil.logInfo("Pagination Count Matched with total number of products according to each pagination")
                } else {

                        KeywordUtil.logInfo("Not Matched")
                }

        }

        /// Match each coubt product on pagination and total colunt shown at the footer

        Connection globalConnection = null;

        globalConnection = CustomKeywords.
        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

        def runtemplate = "SELECT template_id FROM `templates` where catalog_id = " + "'" + catalog_id + "'"

        template_idss = CustomKeywords.
        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$runtemplate")

        template_ids = CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(template_idss, 1, 'template_id')

        def Querystring = "select count(*) as count from (SELECT * FROM (SELECT mec.mec_id FROM modeventcontent mec LEFT JOIN template_level_tickets tlt ON mec.mec_id = tlt.ticket_id AND tlt.template_id = " + template_ids + " AND tlt.catalog_id = '0' LEFT JOIN ticketpriceschedule tp ON tp.ticket_id = mec.mec_id AND tp.default_listing = '1' AND tp.deleted = '0' AND((DATE(from_unixtime(if(tp.start_date is NULL, 1640418622, tp.start_date))) <= CURRENT_DATE AND date(from_unixtime(if(tp.end_date is NULL or tp.end_date like '%999999%', 1766649022, tp.end_date))) >= CURRENT_DATE) OR(DATE(from_unixtime(if(tp.start_date is NULL, 1640418622, tp.start_date))) > CURRENT_DATE)) WHERE mec.deleted = '0' AND mec.parent_ticket_id = '0' AND tp.deleted = '0' AND tlt.deleted = '0' AND tlt.publish_catalog = '1' AND(mec.is_commission_assigned = '1' OR tp.is_commission_assigned = '1') AND(tlt.is_pos_list = '1' || tlt.is_pos_list = '0') GROUP BY mec.mec_id)  main_tb GROUP BY mec_id) as base"

        println(Querystring)

        salesaccountcount = CustomKeywords.
        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$Querystring")

        'Example: Get single cell value using row and column index'
        println CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(salesaccountcount, 1, 'count')
        countofproducts = CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(salesaccountcount, 1, 'count')

        int databasecount = Integer.parseInt(countofproducts)

        if (ProductListCountPrint > 9) {

                if (GlobalVariable.ProductcountfinalInteger1 == databasecount) {

                        KeywordUtil.logInfo("Pagination Count Matched with Database Count Via Running Queries")
                } else {

                        KeywordUtil.logInfo("Not Matched")
                }

        }

        if (ProductListCountPrint < 9) {

                if (ProductListCountPrint == databasecount) {

                        KeywordUtil.logInfo("Pagination Count Matched with Database Count Via Running Queries")
                } else {

                        KeywordUtil.logInfo("Not Matched")
                }

        }
		
        String product_id = catalog_product

        println(product_id)

        //WebUI.setText(findTestObject('Object Repository/Catalog/Search/InputSearch'), GlobalVariable.catalog_product)

        WebUI.setText(findTestObject('Object Repository/Catalog/Search/InputSearch'), product_id)

        WebUI.sendKeys(findTestObject('Object Repository/Catalog/Search/InputSearch'), Keys.chord(Keys.ENTER))

        WebUI.takeScreenshot()

        WebUI.click(findTestObject('Object Repository/Catalog/ProductList-RightPanel/SelectOneProductFromProductList', [('productids'): product_id]))

        //WebUI.click(findTestObject('Object Repository/Catalog/ProductList-RightPanel/EditButton', [('productid') : product_id, ('position') : '1']))

        //Get TicketPriceSchedule ID

        int sizeoftps = WebUI.findWebElements(findTestObject('Object Repository/Catalog/ProductList-RightPanel/GetTicketPriceScheduleId -Length', [('productid'): product_id]), 2).size()
        println("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" + sizeoftps);

        for (int product_type_length = 1; product_type_length <= sizeoftps; product_type_length++) {

                WebElement ticketpriceschedule = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/ProductList-RightPanel/GetTicketPriceScheduleId', [('productid'): product_id, ('position'): product_type_length]), 10)

                WebElement tps_id = ticketpriceschedule
                WebElement channel_id = ticketpriceschedule
                String tps_ids = tps_id.getAttribute('data-ticketpriceschedule_id')
                String channel_ids = channel_id.getAttribute('data-channel_id')

                def final_channel_id = channel_ids
                def final_tps_ids = tps_ids

                KeywordUtil.logInfo("Ticketpriceschedule id for clicked product ${final_tps_ids}")
                KeywordUtil.logInfo(" channel id for the clicked product ${final_channel_id}")

                //Get TicketPriceSchedule Id

                get_product_type = WebUI.getText(findTestObject('Object Repository/Catalog/Pricing', [('tps_id'): final_tps_ids, ('position'): '1']))
                get_list_price = WebUI.getText(findTestObject('Object Repository/Catalog/Pricing', [('tps_id'): final_tps_ids, ('position'): '2']))
                get_sales_price = WebUI.getText(findTestObject('Object Repository/Catalog/Pricing', [('tps_id'): final_tps_ids, ('position'): '3']))
                get_margin = WebUI.getText(findTestObject('Object Repository/Catalog/Pricing', [('tps_id'): final_tps_ids, ('position'): '4']))
                get_discount = WebUI.getText(findTestObject('Object Repository/Catalog/Pricing', [('tps_id'): final_tps_ids, ('position'): '5']))
                get_purchase = WebUI.getText(findTestObject('Object Repository/Catalog/Pricing', [('tps_id'): final_tps_ids, ('position'): '6']))

                def product_type = get_product_type
                def listPrice = get_list_price.replaceAll(",", ".")
                Float list_price_float = Float.parseFloat(listPrice)
                println(list_price_float)

                def salesPrice = get_sales_price.replaceAll(",", ".")
                Float salesPrice_float = Float.parseFloat(salesPrice)
                println(salesPrice_float)

                def margin = get_margin.replaceAll(",", ".")
                Float margin_float = Float.parseFloat(margin)
                println(margin_float)

                def discount = get_discount.replaceAll(",", ".")
                Float discount_float = Float.parseFloat(discount)
                println(discount_float)

                def purchase = get_purchase.replaceAll(",", ".")
                Float purchase_float = Float.parseFloat(purchase)
                println(purchase_float)

                KeywordUtil.logInfo(" product Type in List View :::: ${product_type}")
                KeywordUtil.logInfo(" List Price in List View :::: ${listPrice}")
                KeywordUtil.logInfo(" Sale Price in List View :::: ${salesPrice}")
                KeywordUtil.logInfo(" Margin in List View :::: ${margin}")
                KeywordUtil.logInfo(" Discount in List View :::: ${discount}")
                KeywordUtil.logInfo(" Purchase in List View :::: ${purchase}")

                def getpricefromclc = "select ticket_id, ticketpriceschedule_id, ROUND(ticket_list_price,2) as ticket_list_price, ROUND(ticket_new_price,2) as ticket_new_price, ROUND(museum_gross_commission,2) as museum_gross_commission from channel_level_commission where channel_id = " + final_channel_id + " and ticketpriceschedule_id = " + final_tps_ids + " and is_adjust_pricing = '1'"

                KeywordUtil.logInfo(" Query to get Prices from clc :::: ${getpricefromclc}")

                globalConnection = CustomKeywords.
                'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
                queryforclc = CustomKeywords.
                'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$getpricefromclc")

                'Example: check result set is empty'
                println CustomKeywords.
                'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryforclc)

                if (CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryforclc) == true) {

                        KeywordUtil.logInfo("No Records Return From the Channel Level Commission")

                        def getpricefromtps = "select ticket_id, id as ticketpriceschedule_id,ROUND(pricetext,2) as ticket_list_price,ROUND(newPrice,2) as ticket_new_price,ROUND(museumCommission,2) as museum_gross_commission from ticketpriceschedule where id = " + final_tps_ids + " and deleted = '0'"

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

                        if (list_price_float == ticket_list_price_float) {

                                KeywordUtil.logInfo(" Listing Price Match if entry not Exist in CLC :::: View:: ${listPrice} -- TPS List Price :: ${ticket_list_price}")

                        } else {

                                KeywordUtil.logInfo(" Entry Not Exist in CLC and List Price with TPS not matched")
                        }

                        if (salesPrice_float == ticket_new_price_float) {

                                KeywordUtil.logInfo(" Sale Price Match if entry not Exist in CLC :::: View:: ${salesPrice} -- TPS List Price :: ${ticket_new_price}")

                        } else {

                                KeywordUtil.logInfo(" Entry Not Exist in CLC and Sale Price with TPS not matched")
                        }

                        if (purchase_float == museum_gross_commission_float) {

                                KeywordUtil.logInfo(" Purchase Match if entry not Exist in CLC :::: View:: ${purchase_float} -- TPS List Price :: ${museum_gross_commission}")

                        } else {

                                KeywordUtil.logInfo(" Entry Not Exist in CLC and Purchase Price with TPS not matched")
                        }

                        if (margin_float == (ticket_new_price_float - museum_gross_commission_float)) {

                                KeywordUtil.logInfo(" Margin Match if entry not Exist in CLC :::: View:: -- TPS List Price :: ")

                        } else {

                                KeywordUtil.logInfo(" Entry Not Exist in CLC and Margin with TPS not matched")
                        }

                }

                if (CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryforclc) == false) {

                        def ticket_id_clc = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryforclc, 1, 'ticket_id')

                        def ticketpriceschedule_id_clc = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryforclc, 1, 'ticketpriceschedule_id')

                        def ticket_list_price_clc = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryforclc, 1, 'ticket_list_price')
                        Float ticket_list_price_float_clc = Float.parseFloat(ticket_list_price_clc)
                        println(ticket_list_price_float_clc)

                        def ticket_new_price_clc = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryforclc, 1, 'ticket_new_price')
                        Float ticket_new_price_float_clc = Float.parseFloat(ticket_new_price_clc)
                        println(ticket_new_price_float_clc)

                        def museum_gross_commission_clc = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryforclc, 1, 'museum_gross_commission')
                        Float museum_gross_commission_float_clc = Float.parseFloat(museum_gross_commission_clc)
                        println(museum_gross_commission_float_clc)

                        KeywordUtil.logInfo(" Query to get ticket_id from TPS Table  :::: ${ticket_id_clc}")
                        KeywordUtil.logInfo(" Query to get ticketpriceschedule_id from TPS Table :::: ${ticketpriceschedule_id_clc}")
                        KeywordUtil.logInfo(" Query to get ticket_list_price from TPS Table :::: ${ticket_list_price_clc}")
                        KeywordUtil.logInfo(" Query to get ticket_new_price from TPS Table :::: ${ticket_new_price_clc}")
                        KeywordUtil.logInfo(" Query to get museum_gross_commission from TPS Table :::: ${museum_gross_commission_clc}")

                        /// Mathing data with View

                        if (list_price_float == ticket_list_price_float_clc) {

                                KeywordUtil.logInfo(" Listing Price Matched if entry Exist in CLC :::: View::  -- TPS List Price :: ")

                        } else {

                                KeywordUtil.logInfo(" Entry Exist in CLC and List Price with TPS not matched")
                        }

                        if (salesPrice_float == ticket_new_price_float_clc) {

                                KeywordUtil.logInfo(" Sale Price Matched if entry Exist in CLC :::: View:: -- TPS List Price :: ")

                        } else {

                                KeywordUtil.logInfo(" Entry Exist in CLC and Sale Price with TPS not matched")
                        }

                        if (purchase_float == museum_gross_commission_float_clc) {

                                KeywordUtil.logInfo(" Purchase Matched if entry Exist in CLC :::: View:: -- TPS List Price :: ")

                        } else {

                                KeywordUtil.logInfo(" Entry Exist in CLC and Purchase Price with TPS not matched")
                        }

                        if (margin_float == (ticket_new_price_float_clc - museum_gross_commission_float_clc)) {

                                KeywordUtil.logInfo(" Margin Matched if entry Exist in CLC :::: View:: -- TPS List Price :: ")

                        } else {

                                KeywordUtil.logInfo(" Entry  Exist in CLC and Margin with TPS not matched")
                        }

                }

        }

        WebUI.callTestCase(findTestCase('Admin-LeftNavigation/Catalogs-LeftNavigation'), [: ], FailureHandling.CONTINUE_ON_FAILURE)

}