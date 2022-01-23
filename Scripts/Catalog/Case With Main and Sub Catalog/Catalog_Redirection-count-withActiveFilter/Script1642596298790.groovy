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

        WebUI.delay(5)

        WebUI.click(findTestObject('Object Repository/Catalog/ProductList-RightPanel/ActiveFilter'))

        WebUI.takeScreenshot()

        WebUI.delay(5)
        //Check Count for Product List
        int ProductListCount1 = WebUI.findWebElements(findTestObject('Object Repository/Catalog/ProductList-RightPanel/ProductListTr'), 2).size()

        def ProductListCountPrint = ProductListCount1

        KeywordUtil.logInfo("Product List Count ${ProductListCountPrint}")

        //Check Count of Pagination

        if (ProductListCountPrint > 9) {

                PaginationCount = WebUI.getText(findTestObject('Object Repository/Catalog/ProductList-RightPanel/PaginationMaxNumber'))

                println(PaginationCount)

                int PaginationCountPrint = Integer.parseInt(PaginationCount)

                KeywordUtil.logInfo("Pagination Count ${PaginationCountPrint}")

                //Loop to count product on each pagination and get total sun of product lists

                int intitalvalue = 0
				int inactiveproduct = 0

                for (int i = 0; i < PaginationCountPrint; i++) {

                        int ProductListCount = WebUI.findWebElements(findTestObject('Catalog/ProductList-RightPanel/VerifyToggleButtonEnabled'), 2).size()
						
						int Inactive_product = WebUI.findWebElements(findTestObject('Object Repository/Catalog/ProductList-RightPanel/VerifyToggleButtonDisabled'), 2).size()
                        //def productListSum = (1 + ProductListCount - 1)

                        intitalvalue = intitalvalue + ProductListCount
						
						inactiveproduct = inactiveproduct + Inactive_product

                        println("My product List Count get from the pagination : " + intitalvalue)
						
						println("My product List Count get from the pagination : " + inactiveproduct)

                        def sumofproducts = intitalvalue
						def sumofInactiveproducts = inactiveproduct

                        KeywordUtil.logInfo("${sumofproducts}")
						
						KeywordUtil.logInfo("${sumofInactiveproducts}")

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

                KeywordUtil.logInfo("Count of InActive Products in the right section ${finalcountproducts}")

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
				
				if (inactiveproduct == 0) {
					
						KeywordUtil.logInfo("Active Filter Not Contains InActive Products")
				} else {

						KeywordUtil.logInfo("Active Filter Contains InActive Products")
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

        def Querystring = "select count(*) as count from (SELECT * FROM (SELECT mec.mec_id FROM modeventcontent mec LEFT JOIN template_level_tickets tlt ON mec.mec_id = tlt.ticket_id AND tlt.template_id = " + template_ids + " AND tlt.catalog_id = '0' LEFT JOIN ticketpriceschedule tp ON tp.ticket_id = mec.mec_id AND tp.default_listing = '1' AND tp.deleted = '0' AND((DATE(from_unixtime(if(tp.start_date is NULL, 1640418622, tp.start_date))) <= CURRENT_DATE AND date(from_unixtime(if(tp.end_date is NULL or tp.end_date like '%999999%', 1766649022, tp.end_date))) >= CURRENT_DATE) OR(DATE(from_unixtime(if(tp.start_date is NULL, 1640418622, tp.start_date))) > CURRENT_DATE)) WHERE mec.deleted = '0' AND mec.parent_ticket_id = '0' AND tp.deleted = '0' AND tlt.deleted = '0' AND tlt.publish_catalog = '1' AND tlt.is_pos_list = '1'  AND (mec.is_commission_assigned = '1' OR tp.is_commission_assigned = '1') AND(tlt.is_pos_list = '1' || tlt.is_pos_list = '0') GROUP BY mec.mec_id)  main_tb GROUP BY mec_id) as base"

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

        WebUI.callTestCase(findTestCase('Admin-LeftNavigation/Catalogs-LeftNavigation'), [: ], FailureHandling.CONTINUE_ON_FAILURE)

}