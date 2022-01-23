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
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)

if(GlobalVariable.select_catalog_for_assign_distributor=="DIRECT")
{
	WebUI.click(findTestObject('Object Repository/Assign To Subcatalog/direct_main_catalog'))
WebUI.setText(findTestObject('Object Repository/Assign To Subcatalog/search_button'), GlobalVariable.distributor_id)
	
	WebUI.sendKeys(findTestObject('Object Repository/Assign To Subcatalog/search_button'), Keys.chord(Keys.ENTER))
	WebUI.delay(5)
	WebUI.check(findTestObject('Object Repository/Assign To Subcatalog/checkbox_click_direct_tile'))
	
	int subcatalog_count=WebUI.findWebElements(findTestObject('Object Repository/Catalog Delete SubCatalog Objects/direct_subcatalog_focus'),2).size()
	println("No.of Direct Subcatalogs:" +subcatalog_count);
	
	List<WebElement> subcatalog_name = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Assign To Subcatalog/assign_to_catalog_name'), 10)
	
	for (int i = 0; i < subcatalog_name.size(); i++) {
		WebElement el=subcatalog_name.get(i)
		String direct_subcatalog_delete =el.getAttribute('data-catalog-name')
		println("Direct Subcatalog name : "+el.getAttribute('data-catalog-name'))
		
		if(direct_subcatalog_delete=="Sub Pricing 1")
		{
			WebUI.findWebElements(findTestObject('Object Repository/Assign To Subcatalog/assign_to_catalog_name'),2).get(i).click()
			WebUI.click(findTestObject('Object Repository/Assign To Subcatalog/save_button_assign_distributor'))

			
			WebUI.delay(5)
			
			WebUI.refresh()
			break
		}
		else
			{
				println("NO SUCH SUBCATALOG EXIST TO ASSIGN DISTRIBUTOR")
			}
	}
	
}
else 
	{
		WebUI.click(findTestObject('Object Repository/Assign To Subcatalog/distributor_main_catalog'))
		WebUI.setText(findTestObject('Object Repository/Assign To Subcatalog/search_button'), GlobalVariable.distributor_id)
		
		WebUI.sendKeys(findTestObject('Object Repository/Assign To Subcatalog/search_button'), Keys.chord(Keys.ENTER))
		WebUI.delay(5)
		WebUI.check(findTestObject('Object Repository/Assign To Subcatalog/checkbox_click_distributor_tile'))
		
		int subcatalog_count=WebUI.findWebElements(findTestObject('Object Repository/Catalog Delete SubCatalog Objects/distributor_subcatalog_focus'),2).size()
		println("No.of Direct Subcatalogs:" +subcatalog_count);
		
		List<WebElement> subcatalog_name = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Assign To Subcatalog/assign_to_catalog_distributor'), 10)
		
		for (int i = 0; i < subcatalog_name.size(); i++) {
			WebElement el=subcatalog_name.get(i)
			String direct_subcatalog_delete =el.getAttribute('data-catalog-name')
			println("Direct Subcatalog name : "+el.getAttribute('data-catalog-name'))
			
			if(direct_subcatalog_delete=="test1 copy")
			{
				WebUI.findWebElements(findTestObject('Object Repository/Assign To Subcatalog/assign_to_catalog_name'),2).get(i).click()
				WebUI.click(findTestObject('Object Repository/Assign To Subcatalog/save_button_assign_distributor'))
				WebUI.refresh()
			}
			else
			{
				println("NO SUCH SUBCATALOG EXIST TO ASSIGN DISTRIBUTOR")
			}
		}
	}