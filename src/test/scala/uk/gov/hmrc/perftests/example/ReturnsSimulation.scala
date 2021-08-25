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

  setup("returns", "Returns Journey") withRequests(
    goToAuthLoginPage,
    upFrontAuthLogin,
    getStartReturn,
    postStartReturn,
    getSoldGoodsFromNi,
    postSoldGoodsFromNi,
    getCountryOfConsumptionFromNi(1),
    postCountryOfConsumptionFromNi(1,"ES"),
    getVatRatesFromNi(1),
    postVatRatesFromNi(1, "21.0"),
    getSalesAtVatRateFromNi(1,1),
    postSalesAtVatRateFromNi(1,1),
    getCheckSalesFromNi(1),
    postCheckSalesFromNi(1),
    getAddSalesFromNi,
    postAddSalesFromNi(true),
    getCountryOfConsumptionFromNi(2),
    postCountryOfConsumptionFromNi(2,"FR"),
    getVatRatesFromNi(2),
    postVatRatesFromNi(2, "20.0"),
    getSalesAtVatRateFromNi(2,1),
    postSalesAtVatRateFromNi(2,1),
    getCheckSalesFromNi(2),
    postCheckSalesFromNi(2),
    getAddSalesFromNi,
    postAddSalesFromNi(false)
  )

  runSimulation()
}
