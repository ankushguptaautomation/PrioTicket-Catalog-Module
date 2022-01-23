package com.prio_redirection

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
import org.openqa.selenium.WebElement
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.webui.common.WebUiCommonHelper

import internal.GlobalVariable

public class prio_redirection {
	
	
		int count
		int i
		//Built-In LOG Class
		KeywordLogger log = new KeywordLogger()
		String message
		@Keyword
		def marketplace_redirect_admin() {
			//Count Headings in Navigation
			count=WebUI.findWebElements(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/superadmin_marketplace_redirection_catalogs'),2).size()
			println("Number of Elements:" +count)
	
			//Loop to traverse each Heading in navigtion
			for(i=0;i<count;i++)
	
			{
				//Getting Text of Heading
				String Navigation_text=WebUI.findWebElements(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/superadmin_marketplace_redirection_catalogs'),2).get(i).getText()
				println(Navigation_text)
	
	
				//If white Theme then first click on channels then on Catalogs
				if(Navigation_text==GlobalVariable.white_theme_redirect_left_navigation)
				{
					println('Successfully Clicked On :'+GlobalVariable.white_theme_redirect_left_navigation)
					message='Successfully Clicked On :'+GlobalVariable.white_theme_redirect_left_navigation
					WebUI.findWebElements(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/superadmin_marketplace_redirection_catalogs'),2).get(i).click()
					WebUI.takeFullPageScreenshot()
					break
				}
				//If Black Theme then direct Click to Catalogs
				else if(Navigation_text==GlobalVariable.black_theme_redirect_left_navigation)
				{
					println('Successfully Clicked On :'+GlobalVariable.black_theme_redirect_left_navigation)
					message = 'Successfully Clicked On :'+GlobalVariable.black_theme_redirect_left_navigation
					WebUI.findWebElements(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/superadmin_marketplace_redirection_catalogs'),2).get(i).click()
					WebUI.takeFullPageScreenshot()
					break
				}
	
	
			}
	
			log.logInfo(message)
		}
	
	
	
		@Keyword
		def getsuperadmin_tab()
		{
			//getting element of superadmin tabs
			List<WebElement> superadmintext = WebUiCommonHelper.findWebElements(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/admin_tab_superadmin'), 10)
	
			//traverse to each text and clicked to admins tab
			for ( i = 0; i < superadmintext.size(); i++)
			{
				WebElement el=superadmintext.get(i)
				String click_superadmin_list_text =el.getText()
				println("Superadmin Listing: "+click_superadmin_list_text)
	
				//compare if Admins Matches to the getting value from loop
				if(click_superadmin_list_text=="Admins")
				{
					WebUI.findWebElements(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/admin_tab_superadmin'),2).get(i).click()
					break
				}
	
			}
		}
	
}
