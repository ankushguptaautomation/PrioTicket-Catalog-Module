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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement

import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)

if(GlobalVariable.delete_subcatalog_type=="DIRECT")
{

int direct_subcatalog=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/direct_subcatalog_focus'),2).size()
println("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" +direct_subcatalog);

List<WebElement> direct_subcatalog_name = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/direct_subcatalog_focus'), 10)

//for (int i = 0; i < direct_subcatalog_name.size(); i++) {
//	WebElement el=direct_subcatalog_name.get(i)
//	String direct_subcatalog_delete =el.getAttribute('data-catalog-name')
//	println("Direct Subcatalog name : "+el.getAttribute('data-catalog-name'))


for(int i = 1; i < direct_subcatalog_name.size(); i++)
	
{
	String direct_subcatalog_delete=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/direct_subcatalog_focus'),2).get(i).getAttribute('data-catalog-name')
	println("Direct Subcatalog name : "+direct_subcatalog_delete)
	
	if(direct_subcatalog_delete==GlobalVariable.delete_subcatalog)
	{		
			WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/direct_more_action_button'),2).get(i).click()
			
			if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/direct_subcatalog_plus_focus'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
				{

					List<WebElement> direct_listing = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/direct_plus_button_listing'), 10)
//					for (int j = 1; j < direct_listing.size(); j++) 
//						{
//						WebElement eld=direct_listing.get(j)
//						String direct_subcatalog_delete_button =eld.getText()
//						println("Direct Subcatalog name : "+eld.getText())
					
					
					for(int j = 1; j < direct_listing.size(); j++)
						
					{
						String direct_subcatalog_delete_button=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/direct_plus_button_listing'),2).get(j).getText();
						println("Direct Subcatalog name : "+direct_subcatalog_delete_button)
						
						if(direct_subcatalog_delete_button=="Duplicate Default")
						{
							WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/direct_plus_button_listing'),2).get(j).click()
							
							
						}
					}
					
					
				}
	}
	else
	{
		println("NO SUCH CATALOG EXIST AND NOT ABLE TO DELETE")
	}
}

}
else
	 if(GlobalVariable.delete_subcatalog_type=="DISTRIBUTOR")
{

int distributor_subcatalog=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/distributor_subcatalog_focus'),2).size()
println("No.of Direct catalog and Distributor Catalogs including Subcatalogs:" +distributor_subcatalog);

List<WebElement> distributor_subcatalog_name = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/distributor_subcatalog_focus'), 10)

//for (int i = 0; i < distributor_subcatalog_name.size(); i++) {
//	WebElement el=distributor_subcatalog_name.get(i)
//	String distributor_subcatalog_delete =el.getAttribute('data-catalog-name')
//	println("Distributor Subcatalog name : "+el.getAttribute('data-catalog-name'))



for(int i = 1; i < distributor_subcatalog_name.size(); i++)
	
{
	String distributor_subcatalog_delete=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/distributor_subcatalog_focus'),2).get(i).getAttribute('data-catalog-name')
	println("Distributor Subcatalog name : "+distributor_subcatalog_delete)
	
	if(distributor_subcatalog_delete==GlobalVariable.delete_subcatalog)
		{
			WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/distributor_more_action_button'),2).get(i).click()
			
			if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/distributor_subcatalog_plus_focus'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
				{

					List<WebElement> distributor_listing = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/distributor_plus_button_listing'), 10)
//					for (int j = 1; j < distributor_listing.size(); j++) 
//						{
//						WebElement eld=distributor_listing.get(j)
//						String distributor_subcatalog_delete_button =eld.getText()
//						println("Distributor Subcatalog name : "+eld.getText())
					
					
					for(int j = 1; j < distributor_listing.size(); j++)
						
					{
						String distributor_subcatalog_delete_button=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/distributor_plus_button_listing'),2).get(j).getText();
						println("Distributor Subcatalog name : "+distributor_subcatalog_delete_button)
						
						if(distributor_subcatalog_delete_button=="Duplicate Default")
						{
							WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/distributor_plus_button_listing'),2).get(j).click()
							
						}
					}
					
					
				}	
		}
		
		else
			{
				println("NO SUCH CATALOG EXIST AND NOT ABLE TO DELETE")
			}
}

}
else
	 if(GlobalVariable.delete_subcatalog_type=="RESELLER")
{

int reseller_subcatalog=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/reseller_subcatalog_focus'),2).size()
println("No.of Reseller Catalogs including Subcatalogs:" +reseller_subcatalog);




List<WebElement> reseller_subcatalog_name = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/reseller_subcatalog_focus'), 10)

//for (int i = 1; i < reseller_subcatalog_name.size(); i++) {
//	WebElement el=reseller_subcatalog_name.get(i)
//	String reseller_subcatalog_delete =el.getAttribute('data-catalog-name')
//	println("Reseller Subcatalog name : "+el.getAttribute('data-catalog-name'))
	
	
	for(int i = 1; i < reseller_subcatalog_name.size(); i++)
		
	{
		String reseller_subcatalog_delete=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/reseller_subcatalog_focus'),2).get(i).getAttribute('data-catalog-name')
		println("Reseller Subcatalog name : "+reseller_subcatalog_delete)
	
	
	
	
	if(reseller_subcatalog_delete==GlobalVariable.delete_subcatalog)
		{
			WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/reseller_more_action_button'),2).get(i-1).click()
			
			if(WebUI.verifyElementPresent(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/reseller_subcatalog_plus_focus'), 5, FailureHandling.CONTINUE_ON_FAILURE)==true)
				{

					List<WebElement> reseller_listing = WebUiCommonHelper.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/reseller_plus_button_listing'), 10)
//					for (int j = 1; j < reseller_listing.size(); j++) 
//						{
//						WebElement eld=reseller_listing.get(j)
//						String reseller_subcatalog_delete_button =eld.getText()
//						println("Reseller Subcatalog name : "+eld.getText())
						
						for(int j = 1; j < reseller_listing.size(); j++)
							
						{
							String reseller_subcatalog_delete_button=WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/reseller_plus_button_listing'),2).get(j).getText();
							println("Reseller Subcatalog name : "+reseller_subcatalog_delete_button)
						
						
						
						
						if(reseller_subcatalog_delete_button=="Duplicate Default")
						{
							WebUI.findWebElements(findTestObject('PrioTicket Catalog Objects/Delete Subcatalog/reseller_plus_button_listing'),2).get(j).click()
						
						}
					

						
					}
					
					
				}
			

		}
				
		else
			{
				println("NO SUCH CATALOG EXIST AND NOT ABLE TO DELETE")
			}

		
		
}

}

else
{
	println("NO SUCH CATALOG EXIST AND NOT ABLE TO DELETE")
}