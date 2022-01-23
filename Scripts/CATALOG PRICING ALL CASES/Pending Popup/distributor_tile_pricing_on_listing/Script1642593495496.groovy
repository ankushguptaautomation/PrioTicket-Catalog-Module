import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.catalogpricing.direct_pending_pricing_listing
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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.configuration.RunConfiguration

Connection globalConnection = null
ResultSet actorData

//Calling Redirection Case
WebUI.callTestCase(findTestCase('3) SuperAdmin Marketplace Redirection Catalogs/superadmin_marketplace_redirection_catalog'), null)

//Delay of 3 seconds
WebUI.delay(3)


CustomKeywords.'com.catalogpricing.direct_pending_pricing_listing.search_product'()

CustomKeywords.'com.catalogpricing.direct_pending_pricing_listing.tpsdata'()

CustomKeywords.'com.catalogpricing.direct_pending_pricing_listing.combitype'()

CustomKeywords.'com.catalogpricing.direct_pending_pricing_listing.productID'()

CustomKeywords.'com.catalogpricing.direct_pending_pricing_listing.productname'()

CustomKeywords.'com.catalogpricing.direct_pending_pricing_listing.suppliername'()


double list_price=CustomKeywords.'com.catalogpricing.distributor_pending_pricing_listing.distributor_listprice'()
println("LIST PRICE ON FRONTEND LISTING : "+list_price)

double sale_price=CustomKeywords.'com.catalogpricing.distributor_pending_pricing_listing.distributor_saleprice'()
println("SALE PRICE ON FRONTEND LISTING : "+sale_price)

double margin=CustomKeywords.'com.catalogpricing.distributor_pending_pricing_listing.distributor_margin'()
println("MARGIN ON FRONTEND LISTING : "+margin)

double discount=CustomKeywords.'com.catalogpricing.distributor_pending_pricing_listing.distributor_discount'()
println("DISCOUNT ON FRONTEND LISTING : "+discount)

double purchase_price=CustomKeywords.'com.catalogpricing.distributor_pending_pricing_listing.distributor_purchaseprice'()
println("PURCHASE PRICE ON FRONTEND LISTING : "+purchase_price)


double distributor_fee=CustomKeywords.'com.catalogpricing.distributor_pending_pricing_listing.distributor_distributorfee'()
println("DISTRIBUTOR FEE ON FRONTEND LISTING : "+distributor_fee)


price_query="select (CASE WHEN tlt.publish_catalog != '' THEN tlt.publish_catalog ELSE '0' END ) AS publish_catalog_main,mc.postingEventTitle as shortDesc,mc.cod_id AS Supplier_id,mc.mec_id,mc.museum_name,mc.product_type,mc.reseller_id,mc.is_combi as mec_is_combi,mc.currency_code as mec_currency_code,mc.parent_ticket_id,qc.company, qc.cod_id, qc.is_combi_ticket_allowed, qc.is_booking_combi_ticket_allowed, qc.currency_code as supplier_currency, qc.hex_code, qc.currency_position as currency_position, qc.reseller_id as supplier_reseller_id, qc.reseller_name as supplier_reseller_name, qc.market_merchant_id,tp.pricetext as ticketPrice,tp.newPrice as tp_salesprice,tp.saveamount as tp_discount,tp.museumCommission as tp_purchase,tp.hotelNetPrice,tp.ticket_tax_value,tp.ticket_type_label,round( tp.hotelNetPrice + ( tp.hotelNetPrice * tp.ticket_tax_value/100 ) ) as tp_distributor_fees,tp.hotelCommission as tp_dist_commission,tp.id as ticketpriceschedule_id,tp.start_date,tp.end_date,tp.timezone,tp.subtotal as tp_subtotal,tp.default_listing as tp_default_listing,tp.agefrom, tp.parent_ticket_type, tp.ticket_type_label, tp.ageto, tp.group_type_ticket, tp.start_date, tp.end_date, tp.timezone,round( tp.hotelNetPrice + ( tp.hotelNetPrice * tp.ticket_tax_value/100 ) ) as tp_distributor_fees,tp.hotelCommission as tp_dist_commission,clc.channel_level_commission_id,clc.channel_id AS price_list_id,clc.ticket_id AS clc_product_id,clc.merchant_admin_id AS clc_market_merchan_id,clc.merchant_admin_name AS clc_market_merchant_name,clc.ticket_list_price AS clc_list_price,clc.ticket_new_price AS clc_ticket_new_price,          ROUND(IF(clc.is_discount_in_percent = 0, clc.ticket_discount, clc.ticket_new_price * clc.ticket_discount / 100), 2) AS clc_discount,clc.ticket_gross_price AS clc_sale_price,clc.ticket_tax_value AS clc_product_tax,clc.ticket_net_price AS clc_net_price,clc.hotel_prepaid_commission_percentage as clc_dist_commission,clc.is_adjust_pricing as clc_is_adjust_pricing,clc.hotel_commission_net_price as clc_hotel_commission_net_price,clc.hotel_commission_gross_price as clc_hotel_commission_gross_price,clc.is_hotel_prepaid_commission_percentage as clc_is_hotel_prepaid_commission_percentage,clc.is_resale_percentage as clc_museum_percentage,clc.commission_on_sale_price as clc_commission_on_sale_price,clc.ticket_net_price as clc_ticket_net_price,clc.currency as clc_currency_code,clc.ticketpriceschedule_id as clc_tps_id,clc.museum_gross_commission + clc.merchant_gross_commission AS clc_purchase,sum(ctd.ticket_gross_price) AS ctd_purchase,clc.subtotal_gross_amount AS clc_margin,clc.subtotal_net_amount AS clc_net_reseller_payout,clc.catalog_id,(CASE WHEN mc.is_combi = 2 THEN 'Combi Product' ELSE 'Single Product' END) AS clc_product_option,clc.ticket_type AS clc_product_type,clc.subtotal_gross_amount * clc.hotel_prepaid_commission_percentage / 100 AS clc_distributor_fees from modeventcontent mc LEFT JOIN template_level_tickets tlt on mc.mec_id = tlt.ticket_id and tlt.template_id = '1449' and tlt.catalog_id = '0' LEFT JOIN ticketpriceschedule tp on tp.ticket_id = mc.mec_id and tp.deleted = '0' and tp.default_listing = '1' and ( (tp.start_date <= '1640715804' AND tp.end_date >= '1640715804') OR (tp.start_date > '1640715804')) LEFT JOIN qr_codes qc on mc.cod_id = qc.cod_id LEFT JOIN channel_level_commission clc on mc.mec_id=clc.ticket_id and clc.deleted = '0' and clc.catalog_id = '0'                     and clc.channel_id =  '1018'  and tp.id = clc.ticketpriceschedule_id AND clc.resale_currency_level = (select max(clc2.resale_currency_level) FROM channel_level_commission clc2 WHERE clc2.channel_id = '1018' AND clc2.ticket_id = clc.ticket_id AND clc2.deleted = 0 AND clc2.ticketpriceschedule_id = clc.ticketpriceschedule_id) LEFT JOIN cluster_tickets_detail ctd ON tp.ticket_id = ctd.main_ticket_id and tp.id = ctd.main_ticket_price_schedule_id where mc.deleted = '0' and tp.deleted = '0' and mc.parent_ticket_id = '0' and (mc.reseller_id = '794' || tlt.template_id = '1449') and mc.product_visibility = '1'   and (mc.postingEventTitle like '%1546223%' or mc.mec_id like '%1546223%' or mc.museum_name like '%1546223%') and mc.mec_id in (1546223) GROUP BY mc.mec_id HAVING publish_catalog_main = 0  order by totalClaim Desc, created_on Desc,tp.start_date asc,start_date Desc"
println("LOG QUERY"+price_query)

//DataBase Connection
globalConnection = CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.getGlobalConnection'()
direct_pending_popup_data= CustomKeywords.'com.katalon.plugin.keyword.connection.DatabaseKeywords.executeQuery'(globalConnection, price_query)


db_tps_list_price=  CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(direct_pending_popup_data, 'ticketPrice')
tps_list_price=db_tps_list_price.getAt(0)
float final_tps_db_list_price=Float.parseFloat(tps_list_price);
double tps_list_price_db = (double) Math.round(final_tps_db_list_price * 100) / 100
println("DATABASE LIST PRICE IN TPS TABLE : "+tps_list_price_db)


db_tps_sale_price=  CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(direct_pending_popup_data, 'tp_salesprice')
tps_sale_price=db_tps_sale_price.getAt(0)
float final_tps_db_sale_price=Float.parseFloat(tps_sale_price);
double tps_sale_price_db = (double) Math.round(final_tps_db_sale_price * 100) / 100
println("DATABASE SALE PRICE IN TPS TABLE : "+tps_sale_price_db)



db_tps_purchase_price=  CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(direct_pending_popup_data, 'tp_purchase')
tps_purchase_price=db_tps_purchase_price.getAt(0)
float final_tps_db_purchase_price=Float.parseFloat(tps_purchase_price);
double tps_purchase_price_db = (double) Math.round(final_tps_db_purchase_price * 100) / 100
println("DATABASE PURCHASE PRICE IN TPS TABLE : "+tps_purchase_price_db)



db_tps_discount=  CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(direct_pending_popup_data, 'tp_discount')
tps_discount=db_tps_discount.getAt(0)
float final_tps_db_discount=Float.parseFloat(tps_discount);
double tps_discount_db = (double) Math.round(final_tps_db_discount * 100) / 100
println("DATABASE DISCOUNT IN TPS TABLE : "+tps_discount_db)


double tps_margin_db=tps_sale_price_db-tps_purchase_price_db
println("DATABASE MARGIN IN TPS TABLE : "+tps_margin_db)


if(list_price==tps_list_price_db && sale_price==tps_sale_price_db && margin==tps_margin_db && discount==tps_discount_db && purchase_price==tps_purchase_price_db )
	{
		println("PRICES CAME FROM TPS")
	}
	else {




//-------------------------------------------------



db_clc_list_price=  CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(direct_pending_popup_data, 'clc_list_price')
clc_list_price=db_clc_list_price.getAt(0)
float final_clc_db_list_price=Float.parseFloat(clc_list_price);
double clc_list_price_db = (double) Math.round(final_clc_db_list_price * 100) / 100
println("DATABASE LIST PRICE IN CLC TABLE : "+clc_list_price_db)


db_clc_sale_price=  CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(direct_pending_popup_data, 'clc_sale_price')
clc_sale_price=db_clc_sale_price.getAt(0)
float final_clc_db_sale_price=Float.parseFloat(clc_sale_price);
double clc_sale_price_db = (double) Math.round(final_clc_db_sale_price * 100) / 100
println("DATABASE SALE PRICE IN CLC TABLE : "+clc_sale_price_db)


db_clc_purchase_price=  CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(direct_pending_popup_data, 'clc_purchase')
clc_purchase_price=db_clc_purchase_price.getAt(0)
float final_clc_db_purchase_price=Float.parseFloat(clc_purchase_price);
double clc_purchase_price_db = (double) Math.round(final_clc_db_purchase_price * 100) / 100
println("DATABASE PURCHASE PRICE IN CLC TABLE : "+clc_purchase_price_db)


db_clc_discount=  CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(direct_pending_popup_data, 'clc_discount')
clc_discount=db_clc_discount.getAt(0)
float final_clc_db_discount=Float.parseFloat(clc_discount);
double clc_discount_db = (double) Math.round(final_clc_db_discount * 100) / 100
println("DATABASE DISCOUNT IN CLC TABLE : "+clc_discount_db)



db_clc_distributor_fee=  CustomKeywords.'com.katalon.plugin.keyword.connection.ResultSetKeywords.getListCellValue'(direct_pending_popup_data, 'clc_hotel_commission_gross_price')
clc_distributor_fee=db_clc_distributor_fee.getAt(0)
float final_clc_db_distributor_fee=Float.parseFloat(clc_distributor_fee);
double clc_distributor_fee_db = (double) Math.round(final_clc_db_distributor_fee * 100) / 100
println("DATABASE DISTRIBUTOR FEE IN CLC TABLE : "+clc_distributor_fee_db)


double clc_margin_db_final=clc_sale_price_db-clc_purchase_price_db-clc_distributor_fee_db
double clc_margin_db = (double) Math.round(clc_margin_db_final * 100) / 100
println("DATABASE MARGIN IN CLC TABLE : "+clc_margin_db)

if (list_price==clc_list_price_db && sale_price==clc_sale_price_db && margin==clc_margin_db && discount==clc_discount_db && purchase_price==clc_purchase_price_db && distributor_fee==clc_distributor_fee_db)
	{
		println("PRICES CAME FROM CLC")
	}
	else
	{
		println("SOMETHING WENT WRONG WITH PRICES")
	}

	}


