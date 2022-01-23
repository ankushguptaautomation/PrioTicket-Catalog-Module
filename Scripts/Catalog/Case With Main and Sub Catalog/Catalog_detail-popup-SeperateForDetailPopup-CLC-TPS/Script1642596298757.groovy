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



globalConnection = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

def supplierprices_query1 = "SELECT ticket_gross_price as saleprice FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and resale_currency_level = '1'"
KeywordUtil.logInfo("${supplierprices_query1}")
queryforclc = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierprices_query1")

def supplierprices_query1_sub_catalog = "SELECT ticket_gross_price as saleprice FROM channel_level_commission where catalog_id = " + "'" + GlobalVariable.GV_sub_catalog_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and resale_currency_level = '1'"
KeywordUtil.logInfo("${supplierprices_query1_sub_catalog}")
queryforsub_catalog = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierprices_query1_sub_catalog")

if (CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryforsub_catalog) == true) {

        if (CustomKeywords.
                'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryforclc) == false) {

                if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'supplier_sale_price']), 10))

                {

                        //Get Sale Price for the Supplier Row
                        WebElement Saleprice_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'supplier_sale_price']), 10)

                        WebElement Saleprice_get1 = Saleprice_get

                        String supplier_sale_price1 = Saleprice_get1.getAttribute('value')
                        def supplier_sale_price = supplier_sale_price1.replaceAll(",", ".")
                        Float supplier_sale_price_float = Float.parseFloat(supplier_sale_price)
                        println(supplier_sale_price_float)

                        Connection globalConnection = null;
                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def supplierprices_query = "SELECT ticket_gross_price as saleprice FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and resale_currency_level = '1'"

                        supplier_prices = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierprices_query")

                        saleprice_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(supplier_prices, 1, 'saleprice')
                        Float saleprice_fromdb = Float.parseFloat(saleprice_fromdb1)
                        println(saleprice_fromdb)

                        println(supplierprices_query)

                        if (saleprice_fromdb == supplier_sale_price_float) {

                                KeywordUtil.logInfo("Sale Price from the Supplier row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("Sale Price from the Supplier row NOT Matched with view and database")

                        }

                        //Get Sale Price for the Supplier Row

                        // Get Resale Price and get it from the database

                        WebElement resale_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'supplier_resale_price']), 10)

                        WebElement resaleprice_get1 = resale_get

                        String supplier_resale_price1 = resaleprice_get1.getAttribute('value')
                        def supplier_resale_price = supplier_resale_price1.replaceAll(",", ".")
                        Float supplier_resale_price_float = Float.parseFloat(supplier_resale_price)
                        println(supplier_resale_price_float)

                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def supplierresaleprices_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price FROM `channel_level_commission` where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and resale_currency_level = '1'"
                        println(supplierresaleprices_query)

                        supplier_resaleprices = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierresaleprices_query")

                        resaleprice_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(supplier_resaleprices, 1, 'resale_price')
                        Float resaleprice_fromdb = Float.parseFloat(resaleprice_fromdb1)
                        println(resaleprice_fromdb)

                        if (resaleprice_fromdb == supplier_resale_price_float) {

                                KeywordUtil.logInfo("ReSale Price from the Supplier row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("ReSale Price from the Supplier row NOT Matched with view and database")

                        }
                        // Get Resale Price and get it from the database

                        // Get Purchase Price and get it from the database

                        WebElement purchase_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'supplier_purchase_price']), 10)

                        WebElement purchaseprice_get1 = purchase_get

                        String supplier_purchase_price1 = purchaseprice_get1.getAttribute('value')
                        def supplier_purchase_price = supplier_purchase_price1.replaceAll(",", ".")
                        Float supplier_purchase_price_float = Float.parseFloat(supplier_purchase_price)
                        println(supplier_purchase_price_float)

                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def supplierpurchaseprices_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price FROM `channel_level_commission` where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and resale_currency_level = '1'"
                        println(supplierpurchaseprices_query)

                        supplier_purchaseprices = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierpurchaseprices_query")

                        purchaseprice_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(supplier_purchaseprices, 1, 'product_cost')
                        Float purchaseprice_fromdb = Float.parseFloat(purchaseprice_fromdb1)
                        println(purchaseprice_fromdb)

                        if (purchaseprice_fromdb == supplier_purchase_price_float) {

                                KeywordUtil.logInfo("Purchase Price from the Supplier row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("Purchase Price from the Supplier row NOT Matched with view and database")

                        }
                        // Get Purchase Price and get it from the database

                        // Get Purchase Price and get it from the database

                        WebElement Reseller_commission_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/reseller_commission_percentage', [('commissionpercentage'): 'supplier_distributor_commission']), 10)

                        WebElement Reseller_commission_get1 = Reseller_commission_get

                        String Reseller_commission_get2 = Reseller_commission_get1.getAttribute('value')
                        def reseller_commission = Reseller_commission_get2.replaceAll(",", ".")
                        Float reseller_commission_float = Float.parseFloat(reseller_commission)
                        println(reseller_commission_float)

                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def reseller_commission_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price, hgs_prepaid_commission_percentage FROM `channel_level_commission` where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and resale_currency_level = '1'"
                        println(reseller_commission_query)

                        reseller_commission_date = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$reseller_commission_query")

                        reseller_commission_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(reseller_commission_date, 1, 'hgs_prepaid_commission_percentage')
                        Float reseller_commission_fromdb = Float.parseFloat(reseller_commission_fromdb1)
                        println(reseller_commission_fromdb)

                        if (reseller_commission_fromdb == reseller_commission_float) {

                                KeywordUtil.logInfo("reseller_commission from the Supplier row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("reseller_commission from the Supplier row NOT Matched with view and database")

                        }
                        // Get Purchase Price and get it from the database

                }

                //------------------------Section FOr the Base Currency-------------///////////

                if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']), 10))

                {

                        //Get Sale Price for the Supplier Row
                        WebElement Saleprice_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'newprice']), 10)

                        WebElement Saleprice_get1 = Saleprice_get

                        String supplier_sale_price1 = Saleprice_get1.getAttribute('value')
                        def supplier_sale_price = supplier_sale_price1.replaceAll(",", ".")
                        Float supplier_sale_price_float = Float.parseFloat(supplier_sale_price)
                        println(supplier_sale_price_float)

                        Connection globalConnection = null;
                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def supplierprices_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price, hgs_prepaid_commission_percentage FROM channel_level_commission clc join (SELECT channel_id, ticketpriceschedule_id, max(resale_currency_level) as resale_currency_level FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and deleted = 0 and resale_currency_level in (1,2)) as base on clc.channel_id = base.channel_id and clc.ticketpriceschedule_id = base.ticketpriceschedule_id and clc.resale_currency_level = base.resale_currency_level"

                        supplier_prices = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierprices_query")

                        saleprice_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(supplier_prices, 1, 'saleprice')
                        Float saleprice_fromdb = Float.parseFloat(saleprice_fromdb1)
                        println(saleprice_fromdb)

                        println(supplierprices_query)

                        if (saleprice_fromdb == supplier_sale_price_float) {

                                KeywordUtil.logInfo("Sale Price from the Base row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("Sale Price from the Base row NOT Matched with view and database")

                        }

                        //Get Sale Price for the Supplier Row

                        // Get Resale Price and get it from the database

                        WebElement resale_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'resale_price']), 10)

                        WebElement resaleprice_get1 = resale_get

                        String supplier_resale_price1 = resaleprice_get1.getAttribute('value')
                        def supplier_resale_price = supplier_resale_price1.replaceAll(",", ".")
                        Float supplier_resale_price_float = Float.parseFloat(supplier_resale_price)
                        println(supplier_resale_price_float)

                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def supplierresaleprices_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price, hgs_prepaid_commission_percentage FROM channel_level_commission clc join (SELECT channel_id, ticketpriceschedule_id, max(resale_currency_level) as resale_currency_level FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and deleted = 0 and resale_currency_level in (1,2)) as base on clc.channel_id = base.channel_id and clc.ticketpriceschedule_id = base.ticketpriceschedule_id and clc.resale_currency_level = base.resale_currency_level"
                        println(supplierresaleprices_query)

                        supplier_resaleprices = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierresaleprices_query")

                        resaleprice_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(supplier_resaleprices, 1, 'resale_price')
                        Float resaleprice_fromdb = Float.parseFloat(resaleprice_fromdb1)
                        println(resaleprice_fromdb)

                        if (resaleprice_fromdb == supplier_resale_price_float) {

                                KeywordUtil.logInfo("ReSale Price from the Base row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("ReSale Price from the Base row NOT Matched with view and database")

                        }
                        // Get Resale Price and get it from the database

                        // Get Purchase Price and get it from the database

                        WebElement purchase_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'purchase_price']), 10)

                        WebElement purchaseprice_get1 = purchase_get

                        String supplier_purchase_price1 = purchaseprice_get1.getAttribute('value')
                        def supplier_purchase_price = supplier_purchase_price1.replaceAll(",", ".")
                        Float supplier_purchase_price_float = Float.parseFloat(supplier_purchase_price)
                        println(supplier_purchase_price_float)

                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def supplierpurchaseprices_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price, hgs_prepaid_commission_percentage FROM channel_level_commission clc join (SELECT channel_id, ticketpriceschedule_id, max(resale_currency_level) as resale_currency_level FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and deleted = 0 and resale_currency_level in (1,2)) as base on clc.channel_id = base.channel_id and clc.ticketpriceschedule_id = base.ticketpriceschedule_id and clc.resale_currency_level = base.resale_currency_level"
                        println(supplierpurchaseprices_query)

                        supplier_purchaseprices = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierpurchaseprices_query")

                        purchaseprice_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(supplier_purchaseprices, 1, 'product_cost')
                        Float purchaseprice_fromdb = Float.parseFloat(purchaseprice_fromdb1)
                        println(purchaseprice_fromdb)

                        if (purchaseprice_fromdb == supplier_purchase_price_float) {

                                KeywordUtil.logInfo("Purchase Price from the Base row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("Purchase Price from the Base row NOT Matched with view and database")

                        }
                        // Get Purchase Price and get it from the database

                        // Get Purchase Price and get it from the database

                        WebElement Reseller_commission_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/reseller_commission_percentage', [('commissionpercentage'): 'hotel_prepaid_commission_percentage']), 10)

                        WebElement Reseller_commission_get1 = Reseller_commission_get

                        String Reseller_commission_get2 = Reseller_commission_get1.getAttribute('value')
                        def reseller_commission = Reseller_commission_get2.replaceAll(",", ".")
                        Float reseller_commission_float = Float.parseFloat(reseller_commission)
                        println(reseller_commission_float)

                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def reseller_commission_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price, hgs_prepaid_commission_percentage FROM channel_level_commission clc join (SELECT channel_id, ticketpriceschedule_id, max(resale_currency_level) as resale_currency_level FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and deleted = 0 and resale_currency_level in (1,2)) as base on clc.channel_id = base.channel_id and clc.ticketpriceschedule_id = base.ticketpriceschedule_id and clc.resale_currency_level = base.resale_currency_level"
                        println(reseller_commission_query)

                        reseller_commission_date = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$reseller_commission_query")

                        reseller_commission_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(reseller_commission_date, 1, 'hgs_prepaid_commission_percentage')
                        Float reseller_commission_fromdb = Float.parseFloat(reseller_commission_fromdb1)
                        println(reseller_commission_fromdb)

                        if (reseller_commission_fromdb == reseller_commission_float) {

                                KeywordUtil.logInfo("reseller_commission from the Base row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("reseller_commission from the Base row NOT Matched with view and database")

                        }
                        // Get Purchase Price and get it from the database

                }

                //------------------------Section FOr the New Currency-------------///////////

                if (WebUI.waitForElementPresent(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'new_newprice']), 10))

                {

                        //Get Sale Price for the Supplier Row
                        WebElement Saleprice_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Saleprice', [('pricetype'): 'new_newprice']), 10)

                        WebElement Saleprice_get1 = Saleprice_get

                        String supplier_sale_price1 = Saleprice_get1.getAttribute('value')
                        def supplier_sale_price = supplier_sale_price1.replaceAll(",", ".")
                        Float supplier_sale_price_float = Float.parseFloat(supplier_sale_price)
                        println(supplier_sale_price_float)

                        Connection globalConnection = null;
                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def supplierprices_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price, hgs_prepaid_commission_percentage FROM channel_level_commission clc join (SELECT channel_id, ticketpriceschedule_id, max(resale_currency_level) as resale_currency_level FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and deleted = 0 and resale_currency_level in (1,2,3)) as base on clc.channel_id = base.channel_id and clc.ticketpriceschedule_id = base.ticketpriceschedule_id and clc.resale_currency_level = base.resale_currency_level"

                        supplier_prices = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierprices_query")

                        saleprice_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(supplier_prices, 1, 'saleprice')
                        Float saleprice_fromdb = Float.parseFloat(saleprice_fromdb1)
                        println(saleprice_fromdb)

                        println(supplierprices_query)

                        if (saleprice_fromdb == supplier_sale_price_float) {

                                KeywordUtil.logInfo("Sale Price from the New currency row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("Sale Price from the New currency row NOT Matched with view and database")

                        }

                        //Get Sale Price for the Supplier Row

                        // Get Resale Price and get it from the database

                        WebElement resale_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/Resaleprice', [('resalepricetype'): 'new_resale_price']), 10)

                        WebElement resaleprice_get1 = resale_get

                        String supplier_resale_price1 = resaleprice_get1.getAttribute('value')
                        def supplier_resale_price = supplier_resale_price1.replaceAll(",", ".")
                        Float supplier_resale_price_float = Float.parseFloat(supplier_resale_price)
                        println(supplier_resale_price_float)

                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def supplierresaleprices_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price, hgs_prepaid_commission_percentage FROM channel_level_commission clc join (SELECT channel_id, ticketpriceschedule_id, max(resale_currency_level) as resale_currency_level FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and deleted = 0 and resale_currency_level in (1,2,3)) as base on clc.channel_id = base.channel_id and clc.ticketpriceschedule_id = base.ticketpriceschedule_id and clc.resale_currency_level = base.resale_currency_level"
                        println(supplierresaleprices_query)

                        supplier_resaleprices = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierresaleprices_query")

                        resaleprice_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(supplier_resaleprices, 1, 'resale_price')
                        Float resaleprice_fromdb = Float.parseFloat(resaleprice_fromdb1)
                        println(resaleprice_fromdb)

                        if (resaleprice_fromdb == supplier_resale_price_float) {

                                KeywordUtil.logInfo("ReSale Price from the New currency row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("ReSale Price from the New currency row NOT Matched with view and database")

                        }
                        // Get Resale Price and get it from the database

                        // Get Purchase Price and get it from the database

                        WebElement purchase_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/PurchasePrice', [('purchasepricetype'): 'new_purchase_price']), 10)

                        WebElement purchaseprice_get1 = purchase_get

                        String supplier_purchase_price1 = purchaseprice_get1.getAttribute('value')
                        def supplier_purchase_price = supplier_purchase_price1.replaceAll(",", ".")
                        Float supplier_purchase_price_float = Float.parseFloat(supplier_purchase_price)
                        println(supplier_purchase_price_float)

                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def supplierpurchaseprices_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price, hgs_prepaid_commission_percentage FROM channel_level_commission clc join (SELECT channel_id, ticketpriceschedule_id, max(resale_currency_level) as resale_currency_level FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and deleted = 0 and resale_currency_level in (1,2,3)) as base on clc.channel_id = base.channel_id and clc.ticketpriceschedule_id = base.ticketpriceschedule_id and clc.resale_currency_level = base.resale_currency_level"
                        println(supplierpurchaseprices_query)

                        supplier_purchaseprices = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$supplierpurchaseprices_query")

                        purchaseprice_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(supplier_purchaseprices, 1, 'product_cost')
                        Float purchaseprice_fromdb = Float.parseFloat(purchaseprice_fromdb1)
                        println(purchaseprice_fromdb)

                        if (purchaseprice_fromdb == supplier_purchase_price_float) {

                                KeywordUtil.logInfo("Purchase Price from the New currency row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("Purchase Price from the New currency row NOT Matched with view and database")

                        }
                        // Get Purchase Price and get it from the database

                        // Get Purchase Price and get it from the database

                        WebElement Reseller_commission_get = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Catalog/DetailedPop-up/reseller_commission_percentage', [('commissionpercentage'): 'new_hotel_prepaid_commission_percentage']), 10)

                        WebElement Reseller_commission_get1 = Reseller_commission_get

                        String Reseller_commission_get2 = Reseller_commission_get1.getAttribute('value')
                        def reseller_commission = Reseller_commission_get2.replaceAll(",", ".")
                        Float reseller_commission_float = Float.parseFloat(reseller_commission)
                        println(reseller_commission_float)

                        globalConnection = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()

                        def reseller_commission_query = "SELECT Round(ticket_gross_price,2) as saleprice, ROUND((hgs_commission_net_price*(100+ticket_tax_value)/100),2) as reseller_margin, ROUND((hotel_commission_net_price*(100+ticket_tax_value)/100),2) as distributor_commission, ROUND((museum_gross_commission),2) as product_cost, ROUND(ticket_gross_price-hotel_commission_net_price*(100+ticket_tax_value)/100,2) as resale_price, hgs_prepaid_commission_percentage FROM channel_level_commission clc join (SELECT channel_id, ticketpriceschedule_id, max(resale_currency_level) as resale_currency_level FROM channel_level_commission where channel_id = " + "'" + GlobalVariable.gv_channel_id + "'" + " and ticketpriceschedule_id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and deleted = 0 and resale_currency_level in (1,2,3)) as base on clc.channel_id = base.channel_id and clc.ticketpriceschedule_id = base.ticketpriceschedule_id and clc.resale_currency_level = base.resale_currency_level"
                        println(reseller_commission_query)

                        reseller_commission_date = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$reseller_commission_query")

                        reseller_commission_fromdb1 = CustomKeywords.
                        'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(reseller_commission_date, 1, 'hgs_prepaid_commission_percentage')
                        Float reseller_commission_fromdb = Float.parseFloat(reseller_commission_fromdb1)
                        println(reseller_commission_fromdb)

                        if (reseller_commission_fromdb == reseller_commission_float) {

                                KeywordUtil.logInfo("reseller_commission from the New currency row matched with view and database")

                        } else {

                                KeywordUtil.logInfo("reseller_commission from the New currency row NOT Matched with view and database")

                        }
                        // Get Purchase Price and get it from the database

                }
        }

}

if (CustomKeywords.
        'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryforsub_catalog) == true) {

        if (CustomKeywords.
                'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryforclc) == true) {

                KeywordUtil.logInfo("No Records Return From the Channel Level Commission")

                def getpricefromtps = "select ticket_id, id as ticketpriceschedule_id,ROUND(pricetext,2) as ticket_list_price,ROUND(newPrice,2) as ticket_new_price,ROUND(museumCommission,2) as museum_gross_commission from ticketpriceschedule where id = " + "'" + GlobalVariable.gv_tps_id + "'" + " and deleted = '0'"

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

                        if (supplier_purchase_price1.isEmpty()) {

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

}




