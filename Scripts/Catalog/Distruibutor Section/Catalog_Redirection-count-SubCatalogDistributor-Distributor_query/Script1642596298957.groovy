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

def getpricefrom_distributor = "select ticket_id, ticketpriceschedule_id, ROUND(ticket_list_price,2) as ticket_list_price, ROUND(ticket_new_price,2) as ticket_new_price, ROUND(museum_gross_commission,2) as museum_gross_commission from ticket_level_commission where hotel_id = " + distributorid + " and ticketpriceschedule_id = " + GlobalVariable.final_tps_ids + " and is_adjust_pricing = '1'"

						KeywordUtil.logInfo(" Query to get Prices from Subcatalog :::: ${getpricefrom_distributor}")

					
						queryfordistributor = CustomKeywords.
						'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, "$getpricefrom_distributor")


						'Example: check result set is empty'
						println CustomKeywords.
						'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryfordistributor)
						
						if (CustomKeywords.
							'com.katalon.plugin.keyword.connection.ResultSetKeywords.isEmptyResult'(queryfordistributor) == false) {

							def ticket_id_clc = CustomKeywords.
							'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfordistributor, 1, 'ticket_id')

							def ticketpriceschedule_id_clc = CustomKeywords.
							'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfordistributor, 1, 'ticketpriceschedule_id')

							def ticket_list_price_clc = CustomKeywords.
							'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfordistributor, 1, 'ticket_list_price')
							Float ticket_list_price_float_clc = Float.parseFloat(ticket_list_price_clc)
							println(ticket_list_price_float_clc)

							def ticket_new_price_clc = CustomKeywords.
							'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfordistributor, 1, 'ticket_new_price')
							Float ticket_new_price_float_clc = Float.parseFloat(ticket_new_price_clc)
							println(ticket_new_price_float_clc)

							def museum_gross_commission_clc = CustomKeywords.
							'com.katalon.plugin.keyword.connection.ResultSetKeywords.getSingleCellValue'(queryfordistributor, 1, 'museum_gross_commission')
							Float museum_gross_commission_float_clc = Float.parseFloat(museum_gross_commission_clc)
							println(museum_gross_commission_float_clc)

							KeywordUtil.logInfo(" Query to get ticket_id from TPS Table  :::: ${ticket_id_clc}")
							KeywordUtil.logInfo(" Query to get ticketpriceschedule_id from TPS Table :::: ${ticketpriceschedule_id_clc}")
							KeywordUtil.logInfo(" Query to get ticket_list_price from TPS Table :::: ${ticket_list_price_clc}")
							KeywordUtil.logInfo(" Query to get ticket_new_price from TPS Table :::: ${ticket_new_price_clc}")
							KeywordUtil.logInfo(" Query to get museum_gross_commission from TPS Table :::: ${museum_gross_commission_clc}")

							/// Mathing data with View

							if (list_price_float == ticket_list_price_float_clc) {

									KeywordUtil.logInfo(" Listing Price Matched if entry Exist in Account Level :::: View::  -- TPS List Price :: ")

							} else {

									KeywordUtil.logInfo(" Entry Exist in distributor and List Price with TPS not matched")
							}

							if (salesPrice_float == ticket_new_price_float_clc) {

									KeywordUtil.logInfo(" Sale Price Matched if entry Exist in Account level :::: View:: -- TPS List Price :: ")

							} else {

									KeywordUtil.logInfo(" Entry Exist in Account Level and Sale Price with TPS not matched")
							}

							if (purchase_float == museum_gross_commission_float_clc) {

									KeywordUtil.logInfo(" Purchase Matched if entry Exist in Account Level :::: View:: -- TPS List Price :: ")

							} else {

									KeywordUtil.logInfo(" Entry Exist in Account Level and Purchase Price with TPS not matched")
							}

							if (margin_float == (ticket_new_price_float_clc - museum_gross_commission_float_clc)) {

									KeywordUtil.logInfo(" Margin Matched if entry Exist in Account Level :::: View:: -- TPS List Price :: ")

							} else {

									KeywordUtil.logInfo(" Entry  Exist in Account Level and Margin with TPS not matched")
							}

					}