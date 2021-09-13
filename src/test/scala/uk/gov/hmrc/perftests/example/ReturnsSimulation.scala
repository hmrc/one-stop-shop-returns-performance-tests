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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.example.ReturnsRequests._

class ReturnsSimulation extends PerformanceTestRunner {

  setup("returns", "Returns Journey") withRequests (
    goToAuthLoginPage,
    upFrontAuthLogin,
    getHomePage,
    getStartReturn,
    postStartReturn,
    getSoldGoodsFromNi,
    postSoldGoodsFromNi,
    getCountryOfConsumptionFromNi(1),
    postCountryOfConsumptionFromNi(1, "ES"),
    getVatRatesFromNi(1),
    postVatRatesFromNi(1, "21.0"),
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
    postVatRatesFromNi(2, "20.0"),
    getNetValueOfSalesFromNi(2, 1),
    postNetValueOfSalesFromNi(2, 1),
    getVatOnSalesFromNi(2, 1),
    postVatOnSalesFromNi(2, 1),
    getCheckSalesFromNi(2),
    postCheckSalesFromNi(2),
    getAddSalesFromNi,
    postAddSalesFromNi(false),
    getSoldGoodsFromEu,
    postSoldGoodsFromEu,
    getCountryOfSaleFromEu(1),
    postCountryOfSaleFromEu(1, "ES"),
    getCountryOfConsumptionFromEu(1, 1),
    postCountryOfConsumptionFromEu(1, 1, "FR"),
    getVatRatesFromEu(1, 1),
    postVatRatesFromEu(1, 1, "21.0"),
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
    postVatRatesFromEu(1, 2, "21.0"),
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
    postCountryOfSaleFromEu(2, "MT"),
    getCountryOfConsumptionFromEu(2, 1),
    postCountryOfConsumptionFromEu(2, 1, "IT"),
    getVatRatesFromEu(2, 1),
    postVatRatesFromEu(2, 1, "18.0"),
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
    getCheckYourAnswers,
    postCheckYourAnswers,
    getReturnSubmitted,
    getHomePage,
    getPastReturns,
    getSubmittedReturn
  )

  runSimulation()
}
