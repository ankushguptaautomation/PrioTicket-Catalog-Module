package com.catalog.filters

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
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

public class filters {

	@Keyword
	def catalogselection() {
		//Getting Count of Catalogs Direct and Distributor Catalogs including Subcatalogs
		int catalog_count=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_tile_click'),2).size()
		println("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" +catalog_count);



		//Fetching Selected Catalogs
		for(int i=0;i<catalog_count;i++)

		{
			String catalogs_name=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_tile_click'),2).get(i).getText();
			println("Catalogs Headings:"+catalogs_name)

			if(catalogs_name==GlobalVariable.catalogname)
			{
				println('Selected Catalog Verified')
				WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/catalog_tile_click'),2).get(i).click()
				println('Catalog Clicked')
				break
			}
		}
	}

	@Keyword
	def paginationdropdownselector()
	{
		//Verify Dropdown selector and select the last selection of dropdown

		WebUI.click(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/dropdown_pagination'))
		int paginationdropdown=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_dropdown_limit_selector'),2).size()
		println("Number of elements:" +paginationdropdown)

		for(int i=0;i<paginationdropdown;i++)

		{
			String pageselect=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_dropdown_limit_selector'),2).get(i).getText();
			println("Page Selection Number:"+pageselect)

			if(pageselect=='50')
			{
				WebUI.delay(5)
				WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_dropdown_limit_selector'),2).get(i).click()
				println("Pagination Clicked")
				break
			}
		}

	}

	@Keyword
	def paginationCount()
	{
		//Getting Pagination count
		int paginationcount

		for(int i=0;i<paginationcount;i++)

		{
			String textdata1=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Catalog Filters Objects/pagination_catalog_active'),2).get(i).getText();
			System.out.println('PaginationNumber:'+textdata1)

		}
	}

}
