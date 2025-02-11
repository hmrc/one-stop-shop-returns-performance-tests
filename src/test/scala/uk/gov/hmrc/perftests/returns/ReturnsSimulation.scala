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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.returns.ReturnsRequests._
import utility.Client.clearAll

class ReturnsSimulation extends PerformanceTestRunner {

  val returnsBaseUrl: String = baseUrlFor("one-stop-shop-returns-frontend")

  before {
    println("Clearing the performance tests accounts from the database")
    clearAll(s"$returnsBaseUrl/pay-vat-on-goods-sold-to-eu/northern-ireland-returns-payments/test-only/delete-accounts")
  }

  setup("multipleReturns", "Two Sequential Returns Journey - Strategic Returns off") withRequests (
    goToAuthLoginPage,
    upFrontAuthLoginStrategicOff,
    getHomePage,
    getStartReturn("2021-Q3"),
    postStartReturn("2021-Q3"),
    getSoldGoodsFromNi("2021-Q3"),
    postSoldGoodsFromNi("2021-Q3", true),
    getCountryOfConsumptionFromNi("2021-Q3", 1),
    postCountryOfConsumptionFromNi("2021-Q3", 1, "ES"),
    getVatRatesFromNi("2021-Q3", 1),
    postVatRatesFromNi("2021-Q3", 1, "21"),
    getNetValueOfSalesFromNi("2021-Q3", 1, 1),
    postNetValueOfSalesFromNi("2021-Q3", 1, 1),
    getVatOnSalesFromNi("2021-Q3", 1, 1),
    postVatOnSalesFromNi("2021-Q3", 1, 1),
    getCheckSalesFromNi("2021-Q3", 1),
    postCheckSalesFromNi("2021-Q3", 1),
    getAddSalesFromNi("2021-Q3"),
    postAddSalesFromNi("2021-Q3", true),
    getCountryOfConsumptionFromNi("2021-Q3", 2),
    postCountryOfConsumptionFromNi("2021-Q3", 2, "FR"),
    getVatRatesFromNi("2021-Q3", 2),
    postVatRatesFromNi("2021-Q3", 2, "20"),
    getNetValueOfSalesFromNi("2021-Q3", 2, 1),
    postNetValueOfSalesFromNi("2021-Q3", 2, 1),
    getVatOnSalesFromNi("2021-Q3", 2, 1),
    postVatOnSalesFromNi("2021-Q3", 2, 1),
    getCheckSalesFromNi("2021-Q3", 2),
    postCheckSalesFromNi("2021-Q3", 2),
    getAddSalesFromNi("2021-Q3"),
    postAddSalesFromNi("2021-Q3", false),
    getSoldGoodsFromEu("2021-Q3"),
    postSoldGoodsFromEu("2021-Q3", true),
    getCountryOfSaleFromEu("2021-Q3", 1),
    postCountryOfSaleFromEu("2021-Q3", 1, "ES"),
    getCountryOfConsumptionFromEu("2021-Q3", 1, 1),
    postCountryOfConsumptionFromEu("2021-Q3", 1, 1, "FR"),
    getVatRatesFromEu("2021-Q3", 1, 1),
    postVatRatesFromEu("2021-Q3", 1, 1, "20"),
    getNetValueOfSalesFromEu("2021-Q3", 1, 1, 1),
    postNetValueOfSalesFromEu("2021-Q3", 1, 1, 1),
    getVatOnSalesFromEu("2021-Q3", 1, 1, 1),
    postVatOnSalesFromEu("2021-Q3", 1, 1, 1),
    getCheckSalesToEu("2021-Q3", 1, 1),
    postCheckSalesToEu("2021-Q3", 1, 1),
    getAddSalesToEu("2021-Q3", 1),
    postAddSalesToEu("2021-Q3", 1, true),
    getCountryOfConsumptionFromEu("2021-Q3", 1, 2),
    postCountryOfConsumptionFromEu("2021-Q3", 1, 2, "LU"),
    getVatRatesFromEu("2021-Q3", 1, 2),
    postVatRatesFromEu("2021-Q3", 1, 2, "17"),
    getNetValueOfSalesFromEu("2021-Q3", 1, 2, 1),
    postNetValueOfSalesFromEu("2021-Q3", 1, 2, 1),
    getVatOnSalesFromEu("2021-Q3", 1, 2, 1),
    postVatOnSalesFromEu("2021-Q3", 1, 2, 1),
    getCheckSalesToEu("2021-Q3", 1, 2),
    postCheckSalesToEu("2021-Q3", 1, 2),
    getAddSalesToEu("2021-Q3", 1),
    postAddSalesToEu("2021-Q3", 1, false),
    getAddSalesFromEu("2021-Q3"),
    postAddSalesFromEu("2021-Q3", true),
    getCountryOfSaleFromEu("2021-Q3", 2),
    postCountryOfSaleFromEu("2021-Q3", 2, "CZ"),
    getCountryOfConsumptionFromEu("2021-Q3", 2, 1),
    postCountryOfConsumptionFromEu("2021-Q3", 2, 1, "IT"),
    getVatRatesFromEu("2021-Q3", 2, 1),
    postVatRatesFromEu("2021-Q3", 2, 1, "22"),
    getNetValueOfSalesFromEu("2021-Q3", 2, 1, 1),
    postNetValueOfSalesFromEu("2021-Q3", 2, 1, 1),
    getVatOnSalesFromEu("2021-Q3", 2, 1, 1),
    postVatOnSalesFromEu("2021-Q3", 2, 1, 1),
    getCheckSalesToEu("2021-Q3", 2, 1),
    postCheckSalesToEu("2021-Q3", 2, 1),
    getAddSalesToEu("2021-Q3", 2),
    postAddSalesToEu("2021-Q3", 2, false),
    getAddSalesFromEu("2021-Q3"),
    postAddSalesFromEu("2021-Q3", false),
    getCheckYourAnswers("2021-Q3"),
    postCheckYourAnswers("2021-Q3"),
    getReturnSubmitted("2021-Q3"),
    getHomePage,
    getPastReturns,
    getSubmittedReturn,
    getHomePage,
    getStartReturn("2021-Q4"),
    postStartReturn("2021-Q4"),
    getSoldGoodsFromNi("2021-Q4"),
    postSoldGoodsFromNi("2021-Q4", false),
    getSoldGoodsFromEu("2021-Q4"),
    postSoldGoodsFromEu("2021-Q4", false),
    getCorrectPreviousReturn,
    postCorrectPreviousReturn,
    getCorrectionReturnSinglePeriod,
    postCorrectionReturnSinglePeriod,
    getCorrectionCountry,
    postCorrectionCountry("SE"),
    getUndeclaredCountryCorrection,
    postUndeclaredCountryCorrection,
    getCountryVatCorrection,
    postCountryVatCorrection("5000"),
    getVatPayableConfirm,
    postVatPayableConfirm,
    getVatPayableCheck,
    getVatCorrectionsList,
    postVatCorrectionsList,
    getVatCorrectionPeriods,
    postVatCorrectionPeriods,
    getCheckYourAnswers("2021-Q4"),
    postCheckYourAnswers("2021-Q4"),
    getReturnSubmitted("2021-Q4")
  )

  setup("singleReturn", "Single Return with Corrections Journey - Strategic Returns on") withRequests (
    goToAuthLoginPage,
    upFrontAuthLoginStrategicOn,
    getHomePage,
    getStartReturn("2021-Q4"),
    postStartReturn("2021-Q4"),
    getSoldGoodsFromNi("2021-Q4"),
    postSoldGoodsFromNi("2021-Q4", true),
    getCountryOfConsumptionFromNi("2021-Q4", 1),
    postCountryOfConsumptionFromNi("2021-Q4", 1, "ES"),
    getVatRatesFromNi("2021-Q4", 1),
    postVatRatesFromNi("2021-Q4", 1, "21"),
    getNetValueOfSalesFromNi("2021-Q4", 1, 1),
    postNetValueOfSalesFromNi("2021-Q4", 1, 1),
    getVatOnSalesFromNi("2021-Q4", 1, 1),
    postVatOnSalesFromNi("2021-Q4", 1, 1),
    getCheckSalesFromNi("2021-Q4", 1),
    postCheckSalesFromNi("2021-Q4", 1),
    getAddSalesFromNi("2021-Q4"),
    postAddSalesFromNi("2021-Q4", true),
    getCountryOfConsumptionFromNi("2021-Q4", 2),
    postCountryOfConsumptionFromNi("2021-Q4", 2, "FR"),
    getVatRatesFromNi("2021-Q4", 2),
    postVatRatesFromNi("2021-Q4", 2, "20"),
    getNetValueOfSalesFromNi("2021-Q4", 2, 1),
    postNetValueOfSalesFromNi("2021-Q4", 2, 1),
    getVatOnSalesFromNi("2021-Q4", 2, 1),
    postVatOnSalesFromNi("2021-Q4", 2, 1),
    getCheckSalesFromNi("2021-Q4", 2),
    postCheckSalesFromNi("2021-Q4", 2),
    getAddSalesFromNi("2021-Q4"),
    postAddSalesFromNi("2021-Q4", false),
    getSoldGoodsFromEu("2021-Q4"),
    postSoldGoodsFromEu("2021-Q4", true),
    getCountryOfSaleFromEu("2021-Q4", 1),
    postCountryOfSaleFromEu("2021-Q4", 1, "ES"),
    getCountryOfConsumptionFromEu("2021-Q4", 1, 1),
    postCountryOfConsumptionFromEu("2021-Q4", 1, 1, "FR"),
    getVatRatesFromEu("2021-Q4", 1, 1),
    postVatRatesFromEu("2021-Q4", 1, 1, "20"),
    getNetValueOfSalesFromEu("2021-Q4", 1, 1, 1),
    postNetValueOfSalesFromEu("2021-Q4", 1, 1, 1),
    getVatOnSalesFromEu("2021-Q4", 1, 1, 1),
    postVatOnSalesFromEu("2021-Q4", 1, 1, 1),
    getCheckSalesToEu("2021-Q4", 1, 1),
    postCheckSalesToEu("2021-Q4", 1, 1),
    getAddSalesToEu("2021-Q4", 1),
    postAddSalesToEu("2021-Q4", 1, true),
    getCountryOfConsumptionFromEu("2021-Q4", 1, 2),
    postCountryOfConsumptionFromEu("2021-Q4", 1, 2, "LU"),
    getVatRatesFromEu("2021-Q4", 1, 2),
    postVatRatesFromEu("2021-Q4", 1, 2, "17"),
    getNetValueOfSalesFromEu("2021-Q4", 1, 2, 1),
    postNetValueOfSalesFromEu("2021-Q4", 1, 2, 1),
    getVatOnSalesFromEu("2021-Q4", 1, 2, 1),
    postVatOnSalesFromEu("2021-Q4", 1, 2, 1),
    getCheckSalesToEu("2021-Q4", 1, 2),
    postCheckSalesToEu("2021-Q4", 1, 2),
    getAddSalesToEu("2021-Q4", 1),
    postAddSalesToEu("2021-Q4", 1, false),
    getAddSalesFromEu("2021-Q4"),
    postAddSalesFromEu("2021-Q4", true),
    getCountryOfSaleFromEu("2021-Q4", 2),
    postCountryOfSaleFromEu("2021-Q4", 2, "CZ"),
    getCountryOfConsumptionFromEu("2021-Q4", 2, 1),
    postCountryOfConsumptionFromEu("2021-Q4", 2, 1, "IT"),
    getVatRatesFromEu("2021-Q4", 2, 1),
    postVatRatesFromEu("2021-Q4", 2, 1, "22"),
    getNetValueOfSalesFromEu("2021-Q4", 2, 1, 1),
    postNetValueOfSalesFromEu("2021-Q4", 2, 1, 1),
    getVatOnSalesFromEu("2021-Q4", 2, 1, 1),
    postVatOnSalesFromEu("2021-Q4", 2, 1, 1),
    getCheckSalesToEu("2021-Q4", 2, 1),
    postCheckSalesToEu("2021-Q4", 2, 1),
    getAddSalesToEu("2021-Q4", 2),
    postAddSalesToEu("2021-Q4", 2, false),
    getAddSalesFromEu("2021-Q4"),
    postAddSalesFromEu("2021-Q4", false),
    getCorrectPreviousReturn,
    postCorrectPreviousReturn,
    getCorrectionReturnSinglePeriod,
    postCorrectionReturnSinglePeriod,
    getCorrectionCountry,
    postCorrectionCountry("SE"),
    getUndeclaredCountryCorrection,
    postUndeclaredCountryCorrection,
    getCountryVatCorrection,
    postCountryVatCorrection("5000"),
    getVatPayableConfirm,
    postVatPayableConfirm,
    getVatPayableCheck,
    getVatCorrectionsList,
    postVatCorrectionsList,
    getVatCorrectionPeriods,
    postVatCorrectionPeriods,
    getCheckYourAnswers("2021-Q4"),
    postCheckYourAnswers("2021-Q4"),
    getReturnSubmitted("2021-Q4"),
    getHomePage,
    getPastReturns,
    getSubmittedReturn
  )

  runSimulation()
}
