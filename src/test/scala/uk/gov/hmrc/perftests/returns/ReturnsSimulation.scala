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

  setup("returns", "Returns Journey") withRequests (
    goToAuthLoginPage,
    upFrontAuthLogin,
    getHomePage,
    getStartReturn("2021-Q3"),
    postStartReturn("2021-Q3"),
    getSoldGoodsFromNi("2021-Q3"),
    postSoldGoodsFromNi("2021-Q3", true),
    getCountryOfConsumptionFromNi(1),
    postCountryOfConsumptionFromNi(1, "ES"),
    getVatRatesFromNi(1),
    postVatRatesFromNi(1, "21"),
    getNetValueOfSalesFromNi(1, 1),
    postNetValueOfSalesFromNi(1, 1),
    getVatOnSalesFromNi(1, 1),
    postVatOnSalesFromNi(1, 1),
    getCheckSalesFromNi(1),
    postCheckSalesFromNi(1),
    getAddSalesFromNi,
    postAddSalesFromNi(true),
    getCountryOfConsumptionFromNi(2),
    postCountryOfConsumptionFromNi(2, "FR"),
    getVatRatesFromNi(2),
    postVatRatesFromNi(2, "20"),
    getNetValueOfSalesFromNi(2, 1),
    postNetValueOfSalesFromNi(2, 1),
    getVatOnSalesFromNi(2, 1),
    postVatOnSalesFromNi(2, 1),
    getCheckSalesFromNi(2),
    postCheckSalesFromNi(2),
    getAddSalesFromNi,
    postAddSalesFromNi(false),
    getSoldGoodsFromEu("2021-Q3"),
    postSoldGoodsFromEu("2021-Q3", true),
    getCountryOfSaleFromEu(1),
    postCountryOfSaleFromEu(1, "ES"),
    getCountryOfConsumptionFromEu(1, 1),
    postCountryOfConsumptionFromEu(1, 1, "FR"),
    getVatRatesFromEu(1, 1),
    postVatRatesFromEu(1, 1, "20"),
    getNetValueOfSalesFromEu(1, 1, 1),
    postNetValueOfSalesFromEu(1, 1, 1),
    getVatOnSalesFromEu(1, 1, 1),
    postVatOnSalesFromEu(1, 1, 1),
    getCheckSalesToEu(1, 1),
    postCheckSalesToEu(1, 1),
    getAddSalesToEu(1),
    postAddSalesToEu(1, true),
    getCountryOfConsumptionFromEu(1, 2),
    postCountryOfConsumptionFromEu(1, 2, "LU"),
    getVatRatesFromEu(1, 2),
    postVatRatesFromEu(1, 2, "17"),
    getNetValueOfSalesFromEu(1, 2, 1),
    postNetValueOfSalesFromEu(1, 2, 1),
    getVatOnSalesFromEu(1, 2, 1),
    postVatOnSalesFromEu(1, 2, 1),
    getCheckSalesToEu(1, 2),
    postCheckSalesToEu(1, 2),
    getAddSalesToEu(1),
    postAddSalesToEu(1, false),
    getAddSalesFromEu,
    postAddSalesFromEu(true),
    getCountryOfSaleFromEu(2),
    postCountryOfSaleFromEu(2, "CZ"),
    getCountryOfConsumptionFromEu(2, 1),
    postCountryOfConsumptionFromEu(2, 1, "IT"),
    getVatRatesFromEu(2, 1),
    postVatRatesFromEu(2, 1, "22"),
    getNetValueOfSalesFromEu(2, 1, 1),
    postNetValueOfSalesFromEu(2, 1, 1),
    getVatOnSalesFromEu(2, 1, 1),
    postVatOnSalesFromEu(2, 1, 1),
    getCheckSalesToEu(2, 1),
    postCheckSalesToEu(2, 1),
    getAddSalesToEu(2),
    postAddSalesToEu(2, false),
    getAddSalesFromEu,
    postAddSalesFromEu(false),
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

  runSimulation()
}
