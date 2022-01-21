import sbt._

object Dependencies {

  private val gatlingVersion = "3.4.2"

  val test = Seq(
    "com.typesafe"          % "config"                    % "1.3.1"         % Test,
    "uk.gov.hmrc"          %% "performance-test-runner"   % "5.2.0"         % Test,
    "io.gatling"            % "gatling-test-framework"    % gatlingVersion  % Test,
    "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion  % Test,
    "org.mongodb.scala"    %% "mongo-scala-driver"        % "2.6.0"         % Test,
    "org.scalaj"           %% "scalaj-http"               % "2.4.2"         % Test
  )
}
