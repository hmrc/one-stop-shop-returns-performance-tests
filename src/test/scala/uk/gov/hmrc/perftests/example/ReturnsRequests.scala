/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.perftests.example

import io.gatling.core.Predef._
import io.gatling.core.session.Expression
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object ReturnsRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("one-stop-shop-returns-frontend")
  val stubUrl: String = baseUrlFor("one-stop-shop-registration-stub")
  val route: String   = "/pay-vat-on-goods-sold-to-eu/northern-ireland-returns"
  val fullUrl: String = baseUrl + route

  val loginUrl = baseUrlFor("auth-login-stub")

  def inputSelectorByName(name: String): Expression[String] = s"input[name='$name']"

  def goToAuthLoginPage =
    http("Go to Auth login page")
      .get(loginUrl + s"/auth-login-stub/gg-sign-in")
      .check(status.in(200, 303))
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))

  def upFrontAuthLogin =
    http("Enter Auth login credentials ")
      .post(loginUrl + s"/auth-login-stub/gg-sign-in")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("authorityId", "")
      .formParam("gatewayToken", "")
      .formParam("credentialStrength", "strong")
      .formParam("confidenceLevel", "50")
      .formParam("affinityGroup", "Organisation")
      .formParam("email", "user@test.com")
      .formParam("credentialRole", "User")
      .formParam("redirectionUrl", fullUrl)
      .formParam("enrolment[0].name", "HMRC-MTD-VAT")
      .formParam("enrolment[0].taxIdentifier[0].name", "VRN")
      .formParam("enrolment[0].taxIdentifier[0].value", "${vrn}")
      .formParam("enrolment[0].state", "Activated")
      .check(status.in(200, 303))
      .check(headerRegex("Set-Cookie", """mdtp=(.*)""").saveAs("mdtpCookie"))

  def getHomePage =
    http("Get Home Page")
      .get(fullUrl)
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(status.in(200))

  def getStartReturn =
    http("Get Start Return page")
      .get(fullUrl + "/2021-Q3/startReturn")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postStartReturn =
    http("Post Start Returns")
      .post(fullUrl + "/2021-Q3/startReturn")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(200, 303))

  def getSoldGoodsFromNi =
    http("Get Sold Goods From Ni page")
      .get(fullUrl + "/2021-Q3/soldGoodsFromNi")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postSoldGoodsFromNi =
    http("Post Sold Goods From Ni Country")
      .post(fullUrl + "/2021-Q3/soldGoodsFromNi")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(200, 303))

  def getCountryOfConsumptionFromNi(index: Int) =
    http("Get Country Of Consumption From NI page")
      .get(fullUrl + s"/2021-Q3/countryOfConsumptionFromNi/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCountryOfConsumptionFromNi(index: Int, countryCode: String) =
    http("Post Country Of Consumption From NI Country")
      .post(fullUrl + s"/2021-Q3/countryOfConsumptionFromNi/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", countryCode)
      .check(status.in(200, 303))

  def getVatRatesFromNi(index: Int) =
    http("Get Vat Rates From NI page")
      .get(fullUrl + s"/2021-Q3/vatRatesFromNi/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatRatesFromNi(index: Int, vatRate: String) =
    http("Post Vat Rates From NI Country")
      .post(fullUrl + s"/2021-Q3/vatRatesFromNi/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value[0]", vatRate)
      .check(status.in(200, 303))

  def getSalesAtVatRateFromNi(countryIndex: Int, vatRateIndex: Int) =
    http("Get Sales at Vat Rate From NI page")
      .get(fullUrl + s"/2021-Q3/salesAtVatRateFromNi/$countryIndex/$vatRateIndex")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postSalesAtVatRateFromNi(countryIndex: Int, vatRateIndex: Int) =
    http("Post Sales at Vat Rate From NI Country")
      .post(fullUrl + s"/2021-Q3/salesAtVatRateFromNi/$countryIndex/$vatRateIndex")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("netValueOfSales", "50000")
      .formParam("vatOnSales", "10000")
      .check(status.in(200, 303))

  def getCheckSalesFromNi(index: Int) =
    http("Get Check Sales From NI page")
      .get(fullUrl + s"/2021-Q3/check-sales-from-ni/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCheckSalesFromNi(index: Int) =
    http("Post Check Sales From NI Country")
      .post(fullUrl + s"/2021-Q3/check-sales-from-ni/$index")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.in(200, 303))

  def getAddSalesFromNi =
    http("Get Add Sales From NI page")
      .get(fullUrl + "/2021-Q3/add-sales-from-ni")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postAddSalesFromNi(answer: Boolean) =
    http("Post Add Sales From NI Country")
      .post(fullUrl + "/2021-Q3/add-sales-from-ni")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def getSoldGoodsFromEu =
    http("Get Sold Goods From EU page")
      .get(fullUrl + "/2021-Q3/soldGoodsFromEu")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postSoldGoodsFromEu =
    http("Post Sold Goods From EU")
      .post(fullUrl + "/2021-Q3/soldGoodsFromEu")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", true)
      .check(status.in(200, 303))

  def getCountryOfSaleFromEu(index: Int) =
    http("Get Country Of Sale From EU page")
      .get(fullUrl + s"/2021-Q3/countryOfSaleFromEu/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCountryOfSaleFromEu(index: Int, countryCode: String) =
    http("Post Country Of Sale From EU")
      .post(fullUrl + s"/2021-Q3/countryOfSaleFromEu/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", countryCode)
      .check(status.in(200, 303))

  def getCountryOfConsumptionFromEu(countryFrom: Int, countryTo: Int) =
    http("Get Country Of Consumption From EU page")
      .get(fullUrl + s"/2021-Q3/countryOfConsumptionFromEu/$countryFrom/$countryTo")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCountryOfConsumptionFromEu(countryFrom: Int, countryTo: Int, countryCode: String) =
    http("Post Country Of Consumption From EU")
      .post(fullUrl + s"/2021-Q3/countryOfConsumptionFromEu/$countryFrom/$countryTo")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", countryCode)
      .check(status.in(200, 303))

  def getVatRatesFromEu(countryFrom: Int, countryTo: Int) =
    http("Get Vat Rates From EU page")
      .get(fullUrl + s"/2021-Q3/vatRatesFromEu/$countryFrom/$countryTo")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postVatRatesFromEu(countryFrom: Int, countryTo: Int, vatRate: String) =
    http("Post Vat Rates From EU")
      .post(fullUrl + s"/2021-Q3/vatRatesFromEu/$countryFrom/$countryTo")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value[0]", vatRate)
      .check(status.in(200, 303))

  def getSalesAtVatRateFromEu(countryFrom: Int, countryTo: Int, vatRateIndex: Int) =
    http("Get Sales at Vat Rate From EU page")
      .get(fullUrl + s"/2021-Q3/salesAtVatRateFromEu/$countryFrom/$countryTo/$vatRateIndex")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postSalesAtVatRateFromEu(countryFrom: Int, countryTo: Int, vatRateIndex: Int) =
    http("Post Sales at Vat Rate From EU")
      .post(fullUrl + s"/2021-Q3/salesAtVatRateFromEu/$countryFrom/$countryTo/$vatRateIndex")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("netValueOfSales", "60000")
      .formParam("vatOnSales", "15000")
      .check(status.in(200, 303))

  def getCheckSalesToEu(countryFrom: Int, countryTo: Int) =
    http("Get Check Sales To EU page")
      .get(fullUrl + s"/2021-Q3/check-sales-to-eu/$countryFrom/$countryTo")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCheckSalesToEu(countryFrom: Int, countryTo: Int) =
    http("Post Check Sales To EU")
      .post(fullUrl + s"/2021-Q3/check-sales-to-eu/$countryFrom/$countryTo")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.in(200, 303))

  def getAddSalesToEu(index: Int) =
    http("Get Add Sales To EU page")
      .get(fullUrl + s"/2021-Q3/add-sales-to-eu/$index")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postAddSalesToEu(index: Int, answer: Boolean) =
    http("Post Add Sales To EU")
      .post(fullUrl + s"/2021-Q3/add-sales-to-eu/$index")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def getAddSalesFromEu =
    http("Get Add Sales From EU page")
      .get(fullUrl + "/2021-Q3/add-sales-from-eu")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postAddSalesFromEu(answer: Boolean) =
    http("Post Add Sales From EU")
      .post(fullUrl + "/2021-Q3/add-sales-from-eu")
      .formParam("csrfToken", "${csrfToken}")
      .formParam("value", answer)
      .check(status.in(200, 303))

  def getCheckYourAnswers =
    http("Get Check Your Answers page")
      .get(fullUrl + "/2021-Q3/check-your-answers")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(css(inputSelectorByName("csrfToken"), "value").saveAs("csrfToken"))
      .check(status.in(200))

  def postCheckYourAnswers =
    http("Post Check Your Answers page")
      .post(fullUrl + "/2021-Q3/check-your-answers")
      .formParam("csrfToken", "${csrfToken}")
      .check(status.in(200, 303))

  def getReturnSubmitted =
    http("Get Return Submitted page")
      .get(fullUrl + "/2021-Q3/successful")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(status.in(200))

  def getPastReturns =
    http("Get Past Returns page")
      .get(fullUrl + "/past-returns")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(status.in(200))

  def getSubmittedReturn =
    http("Get Submitted Return page")
      .get(fullUrl + "/return/2021-Q3")
      .header("Cookie", "mdtp=${mdtpCookie}")
      .check(status.in(200))
}
