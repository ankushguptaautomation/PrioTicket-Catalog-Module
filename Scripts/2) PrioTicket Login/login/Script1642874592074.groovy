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
import com.kms.katalon.core.logging.KeywordLogger
import org.openqa.selenium.By
import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.WebElement

//Built-In LOG Class 
KeywordLogger log = new KeywordLogger()
String message

//Open Browser
WebUI.openBrowser(GlobalVariable.urlLogin)
log.logInfo("Input URL : "+GlobalVariable.urlLogin)
WebUI.takeFullPageScreenshot()

//To maximize the window of the browser
WebUI.maximizeWindow()

//Waiting time
WebUI.delay(3)

//Username of the user who is login with Prioticket
WebUI.sendKeys(findTestObject('2) PrioTicket Login Objects/Username'), Keys.chord(GlobalVariable.username, Keys.TAB))
log.logInfo("Username : "+GlobalVariable.username)
WebUI.takeFullPageScreenshot()

//Password of the user who is login with Prioticket
WebUI.setText(findTestObject('2) PrioTicket Login Objects/Password'), GlobalVariable.password)
log.logInfo("Password : "+GlobalVariable.password)
WebUI.takeFullPageScreenshot()

//Need to check the Domain as Priohub using Terms and Conditions 
String domain_verify=WebUI.getAttribute(findTestObject('2) PrioTicket Login Objects/domain_verify'), "src")
log.logInfo("Domain Verify : "+domain_verify)

if(domain_verify.contains("phub"))
	{
		//Once Verified it will click on Terms and Condition Checkbox
		WebUI.check(findTestObject('2) PrioTicket Login Objects/Terms_condition_checkbox'))
	
		WebUI.click(findTestObject('2) PrioTicket Login Objects/SignIn_Button'))
	
		println("Successfully Login With Priohub")
		message="Successfully Login With Priohub"
				
	
	}

 else 
	 {
	 
		 WebUI.click(findTestObject('2) PrioTicket Login Objects/SignIn_Button'))
		 println("Successfully Login With PrioTicket")
		 message="Successfully Login With PrioTicket"
		 
	 }
	 
	 WebUI.delay(10)
	 //getting current url after logged in
	 currentURL=WebUI.getUrl()
	 WebUI.takeFullPageScreenshot()
	 log.logInfo(message)