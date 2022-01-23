package com.homepage

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
import java.sql.Connection
import java.sql.ResultSet
import com.kms.katalon.core.configuration.RunConfiguration
import com.katalon.plugin.keyword.connection.DatabaseKeywords




//public class homepage_data_verify
//{
//
////	@Keyword
////	def pending_product_count()
////	{
////		Connection globalConnection = null;
////	ResultSet actorData;
////
////	globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
////		actorData= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, 'select count(*) as pending_product_count from (select (CASE WHEN tlt.publish_catalog != "" THEN tlt.publish_catalog ELSE "0" END ) AS publish_catalog_main,mc.mec_id,tp.default_listing from modeventcontent mc LEFT JOIN template_level_tickets tlt on mc.mec_id = tlt.ticket_id and tlt.template_id = "1008" and tlt.catalog_id = "0" LEFT JOIN ticketpriceschedule tp on tp.ticket_id = mc.mec_id and tp.deleted = "0" and tp.default_listing = "1" and ( (tp.start_date <= "1640199900" AND tp.end_date >= "1640199900") OR (tp.start_date > "1640199900")) LEFT JOIN qr_codes qc on mc.cod_id = qc.cod_id where mc.deleted="0" and tp.deleted = "0" and mc.parent_ticket_id = "0" and mc.product_visibility = "1" and (mc.reseller_id = "794" || tlt.template_id = "1008") GROUP BY mc.mec_id HAVING publish_catalog_main = "0" order by totalClaim Desc, created_on Desc,start_date Desc) as base')
////
////		println(actorData)
////		}
//}
