/*
 * Copyright 2022 HM Revenue & Customs
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

package utility

import scalaj.http.{Http, HttpResponse}

object Client {

  val eventuallyTimeout = 120000
  val eventuallyPoll    = 5000
  val connTimeoutMs     = 120000
  val readTimeoutMs     = 120000

  def clearAll(url: String): HttpResponse[String] = {
    val request = Http(url)
      .method("GET")
      .timeout(connTimeoutMs = connTimeoutMs, readTimeoutMs = readTimeoutMs)
      .asString
    request
  }

}
