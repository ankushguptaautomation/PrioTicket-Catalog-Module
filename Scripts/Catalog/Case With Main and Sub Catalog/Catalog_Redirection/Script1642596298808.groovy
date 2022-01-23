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

WebUI.delay(10)

WebUI.takeScreenshot()

//Get Count of Pending products
pendingproduct = WebUI.getText(findTestObject('Catalog/Pending-Products/PendingProductCount/PendingProductCount'))

println(pendingproduct)

// Create Connection With Database and get COunt from repository via running query and match with get count value

Connection globalConnection = null;
ResultSet actorData;

def querydata = "SELECT template_id, channel_id FROM resellers where" + " template_id = " + GlobalVariable.searchadminid

println(querydata)

globalConnection = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
actorData = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "SELECT count(*) as count FROM `template_level_tickets` where template_id = '$admin' and deleted = '0' and publish_catalog = '0'")

templatedata = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$querydata")

'Example: Get list of cell value using row and column label'
println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(templatedata, 'template_id')

'Example: Get single cell value using row and column index'
println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(templatedata, 1, 'template_id')

template_id = CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(templatedata, 1, 'template_id')

println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(templatedata, 'channel_id')

'Example: Get single cell value using row and column index'
println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(templatedata, 1, 'channel_id')

actorData = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "SELECT count(*) as count FROM `template_level_tickets` where template_id = '$template_id' and deleted = '0' and publish_catalog = '0'")

'Example: check result set is empty'
println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(actorData)

'Example: Get single cell value using row and column index'
println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(actorData, 1, 'count')

countfromdatabase = CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(actorData, 1, 'count')

'Example: Get list of cell value using row and column index'
println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(actorData, 1)

'Example: Get list of cell value using row and column label'
println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(actorData, 'count')

if (pendingproduct != countfromdatabase) {
    println("Pending Product Count  " + pendingproduct + " and " + countfromdatabase + " do not match.")
}

if (pendingproduct == countfromdatabase) {

    println("Pending Product Count  " + pendingproduct + " and " + countfromdatabase + " matched.")
}

CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.closeConnection'(globalConnection)

/// Direct Catalog COunt

directcatalogcountview = WebUI.getText(findTestObject('Object Repository/Catalog/DirectCatalog/DirectCatalogCount'))

println(directcatalogcountview)

def queryforownsalesaccount = "SELECT count(*) as count FROM qr_codes where" + " reseller_id = " + GlobalVariable.searchadminid + " and own_supplier_id > '0' and cashier_type = '1'"

println(queryforownsalesaccount)

globalConnection = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
salesaccountcount = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$queryforownsalesaccount")

'Example: Get single cell value using row and column index'
println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(salesaccountcount, 1, 'count')
ownsaleaccountfromdb = CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(salesaccountcount, 1, 'count')

if (directcatalogcountview != ownsaleaccountfromdb) {
    println("Direct Sales Account Count  " + directcatalogcountview + " and " + ownsaleaccountfromdb + " do not match.")
}

if (directcatalogcountview == ownsaleaccountfromdb) {

    println("Direct Sales Account Count  " + directcatalogcountview + " and " + ownsaleaccountfromdb + " matched.")
}

CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.closeConnection'(globalConnection)

// Distributor Catalog COunt

distributorCatalogCountView = WebUI.getText(findTestObject('Object Repository/Catalog/DistributorCatalog/DistributorCatalogCount'))

println(distributorCatalogCountView)

def queryfordistributorsalesaccount = "SELECT count(*) as count FROM qr_codes where" + " reseller_id = " + GlobalVariable.searchadminid + " and own_supplier_id = '0' and cashier_type = '1'"

println(queryfordistributorsalesaccount)

globalConnection = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
distributoraccountcount = CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$queryfordistributorsalesaccount")

'Example: Get single cell value using row and column index'
println CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(distributoraccountcount, 1, 'count')
Distributorsaleaccountfromdb = CustomKeywords.
'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(distributoraccountcount, 1, 'count')

if (distributorCatalogCountView != Distributorsaleaccountfromdb) {
    println("Direct Sales Account Count  " + distributorCatalogCountView + " and " + Distributorsaleaccountfromdb + " do not match.")
}

if (distributorCatalogCountView == Distributorsaleaccountfromdb) {

    println("Direct Sales Account Count  " + distributorCatalogCountView + " and " + Distributorsaleaccountfromdb + " matched.")
}

CustomKeywords.
'com.katalon.plugin.keyword.connection.DatabaseKeywords.closeConnection'(globalConnection)

//// Check Count for the Sub catalogs

int subcatalog = WebUI.findWebElements(findTestObject('Object Repository/Catalog/SubCatalogs/SubCatalogLinkedCount'), 2).size()
println("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" + subcatalog);

List < WebElement > direct_subcatalog_name = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Catalog/SubCatalogs/SubCatalogLinkedCount'), 10)

println(direct_subcatalog_name.size())

for (int i = 0; i < direct_subcatalog_name.size(); i++) {
    WebElement catalog_ids = direct_subcatalog_name.get(i)
    String Sub_catalog_id = catalog_ids.getAttribute('data-catalog-id')

    println(Sub_catalog_id)

    catalogcount = WebUI.getText(findTestObject('Object Repository/Catalog/SubCatalogs/SubCatalogcount', [('catalogids'): Sub_catalog_id]))

    println(catalogcount)

    //Database Queries

    def queryString = "SELECT count(*) as count FROM qr_codes where" + " reseller_id = " + GlobalVariable.searchadminid + " and sub_catalog_id = " + Sub_catalog_id

    println(queryString)

    globalConnection = CustomKeywords.
    'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
    linkedaccount_count = CustomKeywords.
    'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$queryString")

    'Example: Get single cell value using row and column index'
    println CustomKeywords.
    'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(linkedaccount_count, 1, 'count')
    linked_accounts = CustomKeywords.
    'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(linkedaccount_count, 1, 'count')

    println(linked_accounts)

    WebUI.takeScreenshot()

    WebUI.verifyEqual(linked_accounts, catalogcount)

    def myVariable = Sub_catalog_id

    KeywordUtil.logInfo("My Value ${myVariable}")

    if (linked_accounts != catalogcount) {
        println("Catalog id " + Sub_catalog_id + " Count  " + catalogcount + " and " + linked_accounts + " do not match.")
    }

    if (linked_accounts == catalogcount) {

        println("Catalog id " + Sub_catalog_id + " Count  " + catalogcount + " and " + linked_accounts + " matched.")
    }

}

// Redirect To Direct Tiles

WebUI.click(findTestObject('Object Repository/Catalog/CatalogTiles/CatalogTile', [('catalogtiles'): 'direct_catalog']))

WebUI.takeScreenshot()