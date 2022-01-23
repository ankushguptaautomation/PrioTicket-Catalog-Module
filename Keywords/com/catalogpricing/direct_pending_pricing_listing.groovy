package com.catalogpricing

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.WebElement as WebElement
import internal.GlobalVariable

public class direct_pending_pricing_listing {
	String combi_type

	@Keyword
	def search_product() {
		WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/pending_popup_button'))
		WebUI.setText(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/search_pending_popup'), GlobalVariable.search_prod_pending)
		WebUI.sendKeys(findTestObject('PrioTicket Catalog Objects/Catalog Pending Popup Objects/search_pending_popup'), Keys.chord(Keys.ENTER))
	}

	@Keyword
	def tpsdata() {
		String tps_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-tps-id')
		println("PRODUCT TPS ID : "+tps_id)
	}

	@Keyword
	def combitype() {
		String clc_is_combi_value=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/main_listing_values'),2).getAttribute('data-is_combi')
		if(clc_is_combi_value=='1') {
			combi_type='NORMAL'
		}

		else if(clc_is_combi_value=='2') {
			combi_type='COMBI'
		}

		else if(clc_is_combi_value=='3') {
			combi_type='CLLUSTER'
		}
		else {
			println(" ")
		}


		println("PRODUCT TYPE LEVEL : "+combi_type)
	}

	@Keyword
	def productID() {
		String product_id=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/product_id'),2).getText()
		println("PRODUCT ID : "+product_id)
	}

	@Keyword
	def productname() {

		String product_name=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/product_name'),2).getText()
		println("PRODUCT NAME : "+product_name)
	}

	@Keyword
	def suppliername() {
		String supplier_name=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/supplier_name'),2).getText()
		println("SUPPLIER NAME : "+supplier_name)
	}



	@Keyword
	def direct_listprice() {
		String listing_list_price=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Pricing/direct_pending_list_price_listing'),2).getText()
		String relace_listing_list_price=listing_list_price.replace(',','.')
		float final_list_price=Float.parseFloat(relace_listing_list_price);
		double list_price = (double) Math.round(final_list_price * 100) / 100
	}


	@Keyword
	def direct_saleprice() {
		String listing_sale_price=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Pricing/direct_pending_sale_price_listing'),2).getText()
		String relace_listing_sale_price=listing_sale_price.replace(',','.')
		float final_sale_price=Float.parseFloat(relace_listing_sale_price);
		double sale_price = (double) Math.round(final_sale_price * 100) / 100
	}

	@Keyword
	def direct_margin() {
		String listing_margin=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Pricing/direct_pending_margin_listing'),2).getText()
		String relace_listing_margin=listing_margin.replace(',','.')
		float final_margin=Float.parseFloat(relace_listing_margin);
		double margin = (double) Math.round(final_margin * 100) / 100
	}

	@Keyword
	def direct_discount() {
		String listing_discount=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Pricing/direct_pending_discount_listing'),2).getText()
		String relace_listing_discount=listing_discount.replace(',','.')
		float final_discount=Float.parseFloat(relace_listing_discount);
		double discount = (double) Math.round(final_discount * 100) / 100
	}

	@Keyword
	def direct_purchaseprice() {
		String listing_purchase_price=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Pending Popup Pricing Objects/Direct Pricing/direct_pending_purchase_price_listing'),2).getText()
		String relace_listing_purchase_price=listing_purchase_price.replace(',','.')
		float final_purchase_price=Float.parseFloat(relace_listing_purchase_price);
		double purchase_price = (double) Math.round(final_purchase_price * 100) / 100
	}

	@Keyword
	def direct_distributorfee() {
		String listing_distributor_fee=WebUI.findWebElement(findTestObject('PrioTicket Catalog Objects/Catalog Pricing Objects/Distributor Tile Main Listing Objects/distributor_fee'),2).getText()
		String relace_listing_distributor_fee_demo=listing_distributor_fee.replace(',','.')
		String splitdata = relace_listing_distributor_fee_demo
		String[] parts = splitdata.split(" ", 2)
		String relace_listing_distributor_fee = parts[0]
		float final_distributor_fee=Float.parseFloat(relace_listing_distributor_fee);
		double distributor_fee = (double) Math.round(final_distributor_fee * 100) / 100
		println("DISTRIBUTOR FEE ON FRONTEND LISTING : "+distributor_fee)
	}
}
