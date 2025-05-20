/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.returns

import io.gatling.core.Predef._
import io.gatling.core.session.Expression
import io.gatling.http.Predef._
import uk.gov.hmrc.performance.conf.ServicesConfiguration

import java.time.LocalDate

object ReturnsRequests extends ServicesConfiguration {

  val baseUrl: String     = baseUrlFor("one-stop-shop-returns-frontend")
  val route: String       = "/pay-vat-on-goods-sold-to-eu/northern-ireland-returns-payments"
  val homepageUrl: String = baseUrl + "/pay-vat-on-goods-sold-to-eu/northern-ireland-returns-payments/your-account"
  val fullUrl: String     = baseUrl + route

  val loginUrl = baseUrlFor("auth-login-stub")

  val twoYearsAgo = LocalDate.now().minusYears(2).getYear.toString

  def inputSelectorByName(name: String): Expression[String] = s"input[name='$name']"

  def goToAuthLoginPage =
    http("Go to Auth login page")
      .get(loginUrl + s"/auth-login-stub/gg-sign-in")
      .check(status.in(200, 303))

  def upFrontAuthLoginStrategicOff =
    http("Enter Auth login credentials ")
      .post(loginUrl + s"/auth-login-stub/gg-sign-in")
      .formParam("authorityId", "")
      .formParam("gatewayToken", "")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .formParam("email", "user@test.com")
      .formParam("credentialRole", "User")
      .formParam("redirectionUrl", homepageUrl)
      .formParam("enrolment[0].name", "HMRC-MTD-VAT")
      .formParam("enrolment[0].taxIdentifier[0].name", "VRN")
      .formParam("enrolment[0].taxIdentifier[0].value", "${multipleReturnsVrn}")
      .formParam("enrolment[0].state", "Activated")
      .formParam("enrolment[1].name", "HMRC-OSS-ORG")
      .formParam("enrolment[1].taxIdentifier[0].name", "VRN")
      .formParam("enrolment[1].taxIdentifier[0].value", "${multipleReturnsVrn}")
      .formParam("enrolment[1].state", "Activated")
      .check(status.in(200, 303))
      .check(headerRegex("Set-Cookie", """mdtp=(.*)""").saveAs("mdtpCookie"))

  def upFrontAuthLoginStrategicOn =
    http("Enter Auth login credentials ")
      .post(loginUrl + s"/auth-login-stub/gg-sign-in")
      .formParam("authorityId", "")
      .formParam("gatewayToken", "")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .formParam("email", "user@test.com")
      .formParam("credentialRole", "User")
      .formParam("redirectionUrl", homepageUrl)
      .formParam("enrolment[0].name", "HMRC-MTD-VAT")
      .formParam("enrolment[0].taxIdentifier[0].name", "VRN")
      .formParam("enrolment[0].taxIdentifier[0].value", "${singleReturnVrn}")
      .formParam("enrolment[0].state", "Activated")
      .formParam("enrolment[1].name", "HMRC-OSS-ORG")
      .formParam("enrolment[1].taxIdentifier[0].name", "VRN")
      .formParam("enrolment[1].taxIdentifier[0].value", "${singleReturnVrn}")
      .formParam("enrolment[1].state", "Activated")
      .check(status.in(200, 303))
      .check(headerRegex("Set-Cookie", """mdtp=(.*)""").saveAs("mdtpCookie"))

  def getHomePage =
    http("Get Home Page")
      .get(homepageUrl)
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(status.in(200))

  def getStartReturn(period: String) =
    http("Get Start Return page")
      .get(fullUrl + s"/$period/start")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postStartReturn(period: String) =
    http("Post Start Returns")
      .post(fullUrl + s"/$period/start")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(200, 303))

  def getSoldGoodsFromNi(period: String) =
    http("Get Sold Goods From Ni page")
      .get(fullUrl + s"/$period/sales-from-northern-ireland")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postSoldGoodsFromNi(period: String, answer: Boolean) =
    http("Post Sold Goods From Ni Country")
      .post(fullUrl + s"/$period/sales-from-northern-ireland")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def getCountryOfConsumptionFromNi(period: String, index: Int) =
    http("Get Country Of Consumption From NI page")
      .get(fullUrl + s"/$period/eu-country-from-northern-ireland/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCountryOfConsumptionFromNi(period: String, index: Int, countryCode: String) =
    http("Post Country Of Consumption From NI Country")
      .post(fullUrl + s"/$period/eu-country-from-northern-ireland/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", countryCode)
      .check(status.in(200, 303))

  def getVatRatesFromNi(period: String, index: Int) =
    http("Get Vat Rates From NI page")
      .get(fullUrl + s"/$period/eu-vat-rates-from-northern-ireland/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatRatesFromNi(period: String, index: Int, vatRate: String) =
    http("Post Vat Rates From NI Country")
      .post(fullUrl + s"/$period/eu-vat-rates-from-northern-ireland/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value[0]", vatRate)
      .check(status.in(200, 303))

  def getNetValueOfSalesFromNi(period: String, countryIndex: Int, vatRateIndex: Int) =
    http("Get Net Value of Sales From NI page")
      .get(fullUrl + s"/$period/eu-sales-from-northern-ireland/$countryIndex/$vatRateIndex")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postNetValueOfSalesFromNi(period: String, countryIndex: Int, vatRateIndex: Int) =
    http("Post Net Value of Sales From NI Country")
      .post(fullUrl + s"/$period/eu-sales-from-northern-ireland/$countryIndex/$vatRateIndex")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "50000")
      .check(status.in(200, 303))

  def getVatOnSalesFromNi(period: String, countryIndex: Int, vatRateIndex: Int) =
    http("Get VAT on Sales From NI page")
      .get(fullUrl + s"/$period/vat-on-sales-from-northern-ireland/$countryIndex/$vatRateIndex")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatOnSalesFromNi(period: String, countryIndex: Int, vatRateIndex: Int) =
    http("Post VAT on Sales From NI Country")
      .post(fullUrl + s"/$period/vat-on-sales-from-northern-ireland/$countryIndex/$vatRateIndex")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("choice", "standard")
      .check(status.in(200, 303))

  def getCheckSalesFromNi(period: String, index: Int) =
    http("Get Check Sales From NI page")
      .get(fullUrl + s"/$period/check-sales-from-northern-ireland/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCheckSalesFromNi(period: String, index: Int) =
    http("Post Check Sales From NI Country")
      .post(fullUrl + s"/$period/check-sales-from-northern-ireland/$index?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.in(200, 303))

  def getAddSalesFromNi(period: String) =
    http("Get Add Sales From NI page")
      .get(fullUrl + s"/$period/add-sales-from-northern-ireland")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postAddSalesFromNi(period: String, answer: Boolean) =
    http("Post Add Sales From NI Country")
      .post(fullUrl + s"/$period/add-sales-from-northern-ireland?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def getSoldGoodsFromEu(period: String) =
    http("Get Sold Goods From EU page")
      .get(fullUrl + s"/$period/sales-from-eu")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postSoldGoodsFromEu(period: String, answer: Boolean) =
    http("Post Sold Goods From EU")
      .post(fullUrl + s"/$period/sales-from-eu")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def getCountryOfSaleFromEu(period: String, index: Int) =
    http("Get Country Of Sale From EU page")
      .get(fullUrl + s"/$period/eu-country-sold-from/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCountryOfSaleFromEu(period: String, index: Int, countryCode: String) =
    http("Post Country Of Sale From EU")
      .post(fullUrl + s"/$period/eu-country-sold-from/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", countryCode)
      .check(status.in(200, 303))

  def getCountryOfConsumptionFromEu(period: String, countryFrom: Int, countryTo: Int) =
    http("Get Country Of Consumption From EU page")
      .get(fullUrl + s"/$period/eu-country-sold-to/$countryFrom/$countryTo")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCountryOfConsumptionFromEu(period: String, countryFrom: Int, countryTo: Int, countryCode: String) =
    http("Post Country Of Consumption From EU")
      .post(fullUrl + s"/$period/eu-country-sold-to/$countryFrom/$countryTo")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", countryCode)
      .check(status.in(200, 303))

  def getVatRatesFromEu(period: String, countryFrom: Int, countryTo: Int) =
    http("Get Vat Rates From EU page")
      .get(fullUrl + s"/$period/eu-vat-rates-from-eu/$countryFrom/$countryTo")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatRatesFromEu(period: String, countryFrom: Int, countryTo: Int, vatRate: String) =
    http("Post Vat Rates From EU")
      .post(fullUrl + s"/$period/eu-vat-rates-from-eu/$countryFrom/$countryTo")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value[0]", vatRate)
      .check(status.in(200, 303))

  def getNetValueOfSalesFromEu(period: String, countryFromIndex: Int, countryToIndex: Int, vatRateIndex: Int) =
    http("Get Net Value of Sales From EU page")
      .get(fullUrl + s"/$period/eu-sales-from-eu/$countryFromIndex/$countryToIndex/$vatRateIndex")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postNetValueOfSalesFromEu(period: String, countryFromIndex: Int, countryToIndex: Int, vatRateIndex: Int) =
    http("Post Net Value of Sales From EU Country")
      .post(fullUrl + s"/$period/eu-sales-from-eu/$countryFromIndex/$countryToIndex/$vatRateIndex")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", "50000")
      .check(status.in(200, 303))

  def getVatOnSalesFromEu(period: String, countryFromIndex: Int, countryToIndex: Int, vatRateIndex: Int) =
    http("Get VAT on Sales From EU page")
      .get(fullUrl + s"/$period/vat-on-sales-from-eu/$countryFromIndex/$countryToIndex/$vatRateIndex")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatOnSalesFromEu(period: String, countryFromIndex: Int, countryToIndex: Int, vatRateIndex: Int) =
    http("Post VAT on Sales From EU Country")
      .post(fullUrl + s"/$period/vat-on-sales-from-eu/$countryFromIndex/$countryToIndex/$vatRateIndex")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("choice", "standard")
      .check(status.in(200, 303))

  def getCheckSalesToEu(period: String, countryFrom: Int, countryTo: Int) =
    http("Get Check Sales To EU page")
      .get(fullUrl + s"/$period/check-sales-from-eu/$countryFrom/$countryTo")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCheckSalesToEu(period: String, countryFrom: Int, countryTo: Int) =
    http("Post Check Sales To EU")
      .post(fullUrl + s"/$period/check-sales-from-eu/$countryFrom/$countryTo?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.in(200, 303))

  def getAddSalesToEu(period: String, index: Int) =
    http("Get Add Sales To EU page")
      .get(fullUrl + s"/$period/add-sales-from-eu-to-eu/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postAddSalesToEu(period: String, index: Int, answer: Boolean) =
    http("Post Add Sales To EU")
      .post(fullUrl + s"/$period/add-sales-from-eu-to-eu/$index?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def getAddSalesFromEu(period: String) =
    http("Get Add Sales From EU page")
      .get(fullUrl + s"/$period/add-sales-from-eu")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postAddSalesFromEu(period: String, answer: Boolean) =
    http("Post Add Sales From EU")
      .post(fullUrl + s"/$period/add-sales-from-eu?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def getCheckYourAnswers(period: String) =
    http("Get Check Your Answers page")
      .get(fullUrl + s"/$period/check-your-answers")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCheckYourAnswers(period: String) =
    http("Post Check Your Answers page")
      .post(fullUrl + s"/$period/check-your-answers?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.in(200, 303))

  def getReturnSubmitted(period: String) =
    http("Get Return Submitted page")
      .get(fullUrl + s"/$period/return-submitted")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(status.in(200))

  def getPastReturns =
    http("Get Past Returns page")
      .get(fullUrl + "/past-returns")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(status.in(200))

  def getSubmittedReturn =
    http("Get Submitted Return page")
      .get(fullUrl + s"/past-returns/$twoYearsAgo-Q3")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(status.in(200))

  def getCorrectPreviousReturn =
    http("Get Correct Previous Return page")
      .get(fullUrl + s"/$twoYearsAgo-Q4/correct-previous-return")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCorrectPreviousReturn =
    http("Post Correct Previous Return")
      .post(fullUrl + s"/$twoYearsAgo-Q4/correct-previous-return")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(200, 303))

  def getCorrectionReturnSinglePeriod =
    http("Get Correction Return Single Period page")
      .get(fullUrl + s"/$twoYearsAgo-Q4/correction-return-single-period/1")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCorrectionReturnSinglePeriod =
    http("Post Correction Return Single Period")
      .post(fullUrl + s"/$twoYearsAgo-Q4/correction-return-single-period/1")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(200, 303))

  def getCorrectionCountry =
    http("Get Correction Country page")
      .get(fullUrl + s"/$twoYearsAgo-Q4/correction-country/1/1")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCorrectionCountry(countryCode: String) =
    http("Post Correction Country")
      .post(fullUrl + s"/$twoYearsAgo-Q4/correction-country/1/1")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", countryCode)
      .check(status.in(200, 303))

  def getUndeclaredCountryCorrection =
    http("Get Undeclared Country Correction page")
      .get(fullUrl + s"/$twoYearsAgo-Q4/add-new-country/1/1")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postUndeclaredCountryCorrection =
    http("Post Undeclared Country Correction")
      .post(fullUrl + s"/$twoYearsAgo-Q4/add-new-country/1/1")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(200, 303))

  def getCountryVatCorrection =
    http("Get Country VAT Correction page")
      .get(fullUrl + s"/$twoYearsAgo-Q4/country-vat-correction-amount/1/1?undeclaredCountry=true")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCountryVatCorrection(vatAmount: String) =
    http("Post Country VAT Correction")
      .post(fullUrl + s"/$twoYearsAgo-Q4/country-vat-correction-amount/1/1?undeclaredCountry=true")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", vatAmount)
      .check(status.in(200, 303))

  def getVatPayableConfirm =
    http("Get VAT Payable Confirm page")
      .get(fullUrl + s"/$twoYearsAgo-Q4/vat-payable-confirm/1/1")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatPayableConfirm =
    http("Post VAT Payable Confirm")
      .post(fullUrl + s"/$twoYearsAgo-Q4/vat-payable-confirm/1/1")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(200, 303))

  def getVatPayableCheck =
    http("Get VAT Payable Check page")
      .get(fullUrl + s"/$twoYearsAgo-Q4/vat-payable-check/1/1")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(status.in(200))

  def getVatCorrectionsList =
    http("Get VAT Corrections List page")
      .get(fullUrl + s"/$twoYearsAgo-Q4/vat-correction-list/1")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatCorrectionsList =
    http("Post VAT Corrections List")
      .post(fullUrl + s"/$twoYearsAgo-Q4/vat-correction-list/1?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", false)
      .check(status.in(200, 303))

  def getVatCorrectionPeriods =
    http("Get VAT Period Corrections List page")
      .get(fullUrl + s"/$twoYearsAgo-Q4/vat-correction-periods")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatCorrectionPeriods =
    http("Post VAT Period Corrections List")
      .post(fullUrl + s"/$twoYearsAgo-Q4/vat-correction-periods?incompletePromptShown=false")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.in(200, 303))
}
