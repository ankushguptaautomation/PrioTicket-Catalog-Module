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
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.logging.KeywordLogger
import org.openqa.selenium.By
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.WebElement

//Built-In LOG Class
KeywordLogger log = new KeywordLogger()
String message

//Calling Login Page from PrioTicket Login Test Case
WebUI.callTestCase(findTestCase('2) PrioTicket Login/login'),null)

//Delay of 2 seconds
WebUI.delay(2)

//WebDriver Just for Reference if Needed
WebDriver driver = DriverFactory.getWebDriver()

//Delay of 5 Seconds
WebUI.delay(5)


	//If Redirection after login goes to marketplace view
	if(WebUI.verifyElementPresent(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/superadmin_marketplace_redirection_catalogs'), 5, FailureHandling.OPTIONAL)==true)
		{
			//Keyword call
			CustomKeywords.'com.prio_redirection.prio_redirection.marketplace_redirect_admin'()
			WebUI.takeFullPageScreenshot()
			
			//getting calaogs and clicked on it
			driver.findElement(By.linkText("Catalogs")).click()
			println("Successfully Clicked On Catalogs")
			message="Successfully Clicked On Catalogs"
			WebUI.takeFullPageScreenshot()
		}
		
	else
		{
			//Keyword call
			CustomKeywords.'com.prio_redirection.prio_redirection.getsuperadmin_tab'()
			WebUI.takeFullPageScreenshot()
			
			//Enter Admin ID
			WebUI.sendKeys(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/admin_tab_text_box'), GlobalVariable.admin_id)
			WebUI.takeFullPageScreenshot()
			
			//Delay of 5 Second
			WebUI.delay(5)

			//Getting all values in listing
			List<WebElement> admin_listing = WebUiCommonHelper.findWebElements(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/get_admin_for_click'), 10)
			
			//traverse each list and getting value by spliiting into 2 keywords
			for (int i = 0; i < admin_listing.size(); i++) 
				{
					WebElement el=admin_listing.get(i)
					String adminid=el.getText()
					println("ID and Name:"+adminid)
					String[] parts = adminid.split(" ", 2)
					String superadmin_admin_id = parts[0]
					println(superadmin_admin_id)
					
					WebUI.takeFullPageScreenshot()
					
					//Compare Admin ID with Splitted value
					if(superadmin_admin_id==GlobalVariable.admin_id)
					{
						WebUI.findWebElements(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/get_admin_for_click'),2).get(i).click()
						
						//If Redirection after login goes to marketplace view
						if(WebUI.verifyElementPresent(findTestObject('3) SuperAdmin Marketplace Redirection Catalogs Objects/superadmin_marketplace_redirection_catalogs'), 5, FailureHandling.OPTIONAL)==true)
							{
								//Keyword call
								CustomKeywords.'com.prio_redirection.prio_redirection.marketplace_redirect_admin'()
								WebUI.takeFullPageScreenshot()
								
								//getting calaogs and clicked on it
								driver.findElement(By.linkText("Catalogs")).click()
								println("Successfully Clicked On Catalogs")
								message="Successfully Clicked On Catalogs"
								WebUI.takeFullPageScreenshot()
							}
					}
					
					
				}
		}
		
		log.logInfo(message)
		WebUI.delay(10)
		//getting current url after logged in
		currentURL=WebUI.getUrl()