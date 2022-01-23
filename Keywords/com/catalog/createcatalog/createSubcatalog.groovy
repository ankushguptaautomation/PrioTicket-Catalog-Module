package com.catalog.createcatalog

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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class createSubcatalog {

	@Keyword
	def subcatalogUtilities() {
		//fill name of catalog
		WebUI.setText(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/create_subcatalog_catalog_name'), GlobalVariable.create_subcatalog_name)
		//Description
		WebUI.setText(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/create_subcatalog_catalog_desc'), GlobalVariable.create_subcatalog_desc)
		//Verify If toggle not enabled or not
		if(WebUI.verifyElementNotChecked(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/product_content_description_toggle'), 10,FailureHandling.CONTINUE_ON_FAILURE)==true)
		{

			WebUI.check(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/product_content_description_toggle'))

			if (WebUI.verifyElementChecked(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/product_content_description_toggle'), 10,FailureHandling.CONTINUE_ON_FAILURE)==true)
			{
				println("Product Content Description is Enabled")
			}
		}

		else
		{
			println("Product Content Description is Enabled")
		}
	}


	@Keyword
	def subcatalogSelection()
	{
		//getting values of catalogs
		String create_direct_subcatalog=WebUI.getAttribute(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/create_direct_subcatalog'), "data-type")
		println("Direct SubCatalog Value="+create_direct_subcatalog)

		String create_distributor_subcatalog=WebUI.getAttribute(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/create_distributor_subcatalog'), "data-type")
		println("Distributor SubCatalog Value="+create_distributor_subcatalog)

		String create_reseller_subcatalog=WebUI.getAttribute(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/create_reseller_subcatalog'), "data-type")
		println("Reseller SubCatalog Value="+create_reseller_subcatalog)

		//verify the categories for which we need to create subcatalog
		if(create_direct_subcatalog == GlobalVariable.subcatalog_create_category)
		{
			WebUI.click(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/create_direct_subcatalog'))
		}
		else if(create_distributor_subcatalog == GlobalVariable.subcatalog_create_category)
		{
			WebUI.click(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/create_distributor_subcatalog'))
		}

		else if (create_reseller_subcatalog == GlobalVariable.subcatalog_create_category)
		{
			WebUI.click(findTestObject('PrioTicket Catalog Objects/Subcatalog Creation Objects/create_reseller_subcatalog'))
		}

		else
		{
			println("No such Catalogs Exist in Catalogs Module")
		}
	}
}
