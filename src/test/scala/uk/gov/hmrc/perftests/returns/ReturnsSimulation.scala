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

import io.gatling.core.Predef.pause
import io.gatling.core.action.builder.ActionBuilder
import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.returns.ReturnsRequests._

import java.time.LocalDate
import scala.concurrent.duration.{Duration, SECONDS}

class ReturnsSimulation extends PerformanceTestRunner {

  val returnsBaseUrl: String = baseUrlFor("one-stop-shop-returns-frontend")

  val twoYearsAgo = LocalDate.now().minusYears(2).getYear.toString

  val pauseBuilder: ActionBuilder = pause(Duration(5, SECONDS)).actionBuilders.head

  setup("singleReturn", "Single Return with Corrections Journey") withRequests (
    goToAuthLoginPage,
    upFrontAuthLoginStrategicOn,
    getHomePage,
    getStartReturn(s"$twoYearsAgo-Q4"),
    postStartReturn(s"$twoYearsAgo-Q4"),
    getWantToUploadFile(s"$twoYearsAgo-Q4"),
    postWantToUploadFile(s"$twoYearsAgo-Q4", false),
    getSoldGoodsFromNi(s"$twoYearsAgo-Q4"),
    postSoldGoodsFromNi(s"$twoYearsAgo-Q4", true),
    getCountryOfConsumptionFromNi(s"$twoYearsAgo-Q4", 1),
    postCountryOfConsumptionFromNi(s"$twoYearsAgo-Q4", 1, "ES"),
    getVatRatesFromNi(s"$twoYearsAgo-Q4", 1),
    postVatRatesFromNi(s"$twoYearsAgo-Q4", 1, "21"),
    getNetValueOfSalesFromNi(s"$twoYearsAgo-Q4", 1, 1),
    postNetValueOfSalesFromNi(s"$twoYearsAgo-Q4", 1, 1),
    getVatOnSalesFromNi(s"$twoYearsAgo-Q4", 1, 1),
    postVatOnSalesFromNi(s"$twoYearsAgo-Q4", 1, 1),
    getCheckSalesFromNi(s"$twoYearsAgo-Q4", 1),
    postCheckSalesFromNi(s"$twoYearsAgo-Q4", 1),
    getAddSalesFromNi(s"$twoYearsAgo-Q4"),
    postAddSalesFromNi(s"$twoYearsAgo-Q4", true),
    getCountryOfConsumptionFromNi(s"$twoYearsAgo-Q4", 2),
    postCountryOfConsumptionFromNi(s"$twoYearsAgo-Q4", 2, "FR"),
    getVatRatesFromNi(s"$twoYearsAgo-Q4", 2),
    postVatRatesFromNi(s"$twoYearsAgo-Q4", 2, "20"),
    getNetValueOfSalesFromNi(s"$twoYearsAgo-Q4", 2, 1),
    postNetValueOfSalesFromNi(s"$twoYearsAgo-Q4", 2, 1),
    getVatOnSalesFromNi(s"$twoYearsAgo-Q4", 2, 1),
    postVatOnSalesFromNi(s"$twoYearsAgo-Q4", 2, 1),
    getCheckSalesFromNi(s"$twoYearsAgo-Q4", 2),
    postCheckSalesFromNi(s"$twoYearsAgo-Q4", 2),
    getAddSalesFromNi(s"$twoYearsAgo-Q4"),
    postAddSalesFromNi(s"$twoYearsAgo-Q4", false),
    getSoldGoodsFromEu(s"$twoYearsAgo-Q4"),
    postSoldGoodsFromEu(s"$twoYearsAgo-Q4", true),
    getCountryOfSaleFromEu(s"$twoYearsAgo-Q4", 1),
    postCountryOfSaleFromEu(s"$twoYearsAgo-Q4", 1, "ES"),
    getCountryOfConsumptionFromEu(s"$twoYearsAgo-Q4", 1, 1),
    postCountryOfConsumptionFromEu(s"$twoYearsAgo-Q4", 1, 1, "FR"),
    getVatRatesFromEu(s"$twoYearsAgo-Q4", 1, 1),
    postVatRatesFromEu(s"$twoYearsAgo-Q4", 1, 1, "20"),
    getNetValueOfSalesFromEu(s"$twoYearsAgo-Q4", 1, 1, 1),
    postNetValueOfSalesFromEu(s"$twoYearsAgo-Q4", 1, 1, 1),
    getVatOnSalesFromEu(s"$twoYearsAgo-Q4", 1, 1, 1),
    postVatOnSalesFromEu(s"$twoYearsAgo-Q4", 1, 1, 1),
    getCheckSalesToEu(s"$twoYearsAgo-Q4", 1, 1),
    postCheckSalesToEu(s"$twoYearsAgo-Q4", 1, 1),
    getAddSalesToEu(s"$twoYearsAgo-Q4", 1),
    postAddSalesToEu(s"$twoYearsAgo-Q4", 1, true),
    getCountryOfConsumptionFromEu(s"$twoYearsAgo-Q4", 1, 2),
    postCountryOfConsumptionFromEu(s"$twoYearsAgo-Q4", 1, 2, "LU"),
    getVatRatesFromEu(s"$twoYearsAgo-Q4", 1, 2),
    postVatRatesFromEu(s"$twoYearsAgo-Q4", 1, 2, "17"),
    getNetValueOfSalesFromEu(s"$twoYearsAgo-Q4", 1, 2, 1),
    postNetValueOfSalesFromEu(s"$twoYearsAgo-Q4", 1, 2, 1),
    getVatOnSalesFromEu(s"$twoYearsAgo-Q4", 1, 2, 1),
    postVatOnSalesFromEu(s"$twoYearsAgo-Q4", 1, 2, 1),
    getCheckSalesToEu(s"$twoYearsAgo-Q4", 1, 2),
    postCheckSalesToEu(s"$twoYearsAgo-Q4", 1, 2),
    getAddSalesToEu(s"$twoYearsAgo-Q4", 1),
    postAddSalesToEu(s"$twoYearsAgo-Q4", 1, false),
    getAddSalesFromEu(s"$twoYearsAgo-Q4"),
    postAddSalesFromEu(s"$twoYearsAgo-Q4", true),
    getCountryOfSaleFromEu(s"$twoYearsAgo-Q4", 2),
    postCountryOfSaleFromEu(s"$twoYearsAgo-Q4", 2, "CZ"),
    getCountryOfConsumptionFromEu(s"$twoYearsAgo-Q4", 2, 1),
    postCountryOfConsumptionFromEu(s"$twoYearsAgo-Q4", 2, 1, "IT"),
    getVatRatesFromEu(s"$twoYearsAgo-Q4", 2, 1),
    postVatRatesFromEu(s"$twoYearsAgo-Q4", 2, 1, "22"),
    getNetValueOfSalesFromEu(s"$twoYearsAgo-Q4", 2, 1, 1),
    postNetValueOfSalesFromEu(s"$twoYearsAgo-Q4", 2, 1, 1),
    getVatOnSalesFromEu(s"$twoYearsAgo-Q4", 2, 1, 1),
    postVatOnSalesFromEu(s"$twoYearsAgo-Q4", 2, 1, 1),
    getCheckSalesToEu(s"$twoYearsAgo-Q4", 2, 1),
    postCheckSalesToEu(s"$twoYearsAgo-Q4", 2, 1),
    getAddSalesToEu(s"$twoYearsAgo-Q4", 2),
    postAddSalesToEu(s"$twoYearsAgo-Q4", 2, false),
    getAddSalesFromEu(s"$twoYearsAgo-Q4"),
    postAddSalesFromEu(s"$twoYearsAgo-Q4", false),
    getCorrectPreviousReturn,
    postCorrectPreviousReturn(true),
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
    getCheckYourAnswers(s"$twoYearsAgo-Q4"),
    postCheckYourAnswers(s"$twoYearsAgo-Q4"),
    getReturnSubmitted(s"$twoYearsAgo-Q4"),
    getHomePage,
    getPastReturns,
    getSubmittedReturn
  )

  setup("fileUpload", "Return using File Upload") withActions (
    goToAuthLoginPage,
    upFrontAuthLoginFileUpload,
    getHomePage,
    getStartReturn(s"$twoYearsAgo-Q4"),
    postStartReturn(s"$twoYearsAgo-Q4"),
    getWantToUploadFile(s"$twoYearsAgo-Q4"),
    postWantToUploadFile(s"$twoYearsAgo-Q4", answer = true),
    getFileUpload(s"$twoYearsAgo-Q4"),
    postFileUpload(s"$twoYearsAgo-Q4"),
    pauseBuilder,
    getFileUploaded,
    pauseBuilder,
    postFileUploaded(s"$twoYearsAgo-Q4"),
    getCorrectPreviousReturn,
    postCorrectPreviousReturn(false),
    getCheckYourAnswers(s"$twoYearsAgo-Q4"),
    postCheckYourAnswers(s"$twoYearsAgo-Q4"),
    getReturnSubmitted(s"$twoYearsAgo-Q4"),
  )

  runSimulation()
}
