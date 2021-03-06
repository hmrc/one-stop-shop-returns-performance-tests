# one-stop-shop-returns-performance-tests
Performance test suite for the `one-stop-shop-returns-frontend`, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.

## Local setup - MongoDB and Service Manager

Run at least 4.0 with a replica set: `docker run --restart unless-stopped -d -p 27017-27019:27017-27019 --name mongo4 mongo:4.0 --replSet rs0` 
Connect to said replica set: `docker exec -it mongo4 mongo` 
When that console is there: `rs.initiate()` 
You then should be running 4.0 with a replica set. You may have to re-run the rs.initiate() after you've restarted

sm --start ONE_STOP_SHOP_ALL -r

## Running Locally

### Amend one-stop-shop-returns-frontend application.conf and run from terminal

Amend the section for one-stop-shop-registration in application.conf to:
```
one-stop-shop-registration {
    protocol = http
    host     = localhost
    port     = 10203
    basePath = "one-stop-shop-registration-stub"
}
```
This will use the registrations stub to check registrations exist for the users in the returns service,
instead of having to populate the database prior to the performance test.

Use "sm --stop ONE_STOP_SHOP_RETURNS_FRONTEND" and then run the service using:
```
"sbt run -Dapplication.router=testOnlyDoNotUseInAppConf.Routes" in the terminal
```

### Amend and run testOnly version of one-stop-shop-returns from terminal

#### Amend config

As with Returns backend we also need to use the registration stub as it pulls registration info when submitting to core

Amend the section for one-stop-shop-registration in application.conf to:
```
one-stop-shop-registration {
    protocol = http
    host     = localhost
    port     = 10203
    basePath = "one-stop-shop-registration-stub"
}
```

#### Run and use test-only routes

In order to clear down the performance test accounts prior to each run. We need to use the test-only endpoint
in one-stop-shop-returns. 

Use "sm --stop ONE_STOP_SHOP_RETURNS" to stop the service in service manager then in the terminal run:
```
sbt run -Dapplication.router=testOnlyDoNotUseInAppConf.Routes
```
## Running on Staging - app-config-staging
app-config-staging has been set up to use the testOnly routes so that service manager will run the equivalent of
"sbt run -Dapplication.router=testOnlyDoNotUseInAppConf.Routes" on staging.


## Running the tests

### Smoke test

It might be useful to try the journey with one user to check that everything works fine before running the full performance test
```
sbt -Dperftest.runSmokeTest=true -DrunLocal=true gatling:test
```

#### Running the performance test
```
sbt -DrunLocal=true gatling:test
```
### Run the example test against staging environment

#### Smoke test
```
sbt -Dperftest.runSmokeTest=true -DrunLocal=false gatling:test
```

#### Run the performance test on staging

To run a full performance test against staging environment, see Jenkins [https://performance.tools.staging.tax.service.gov.uk/job/one-stop-shop-returns-performance-tests/](https://performance.tools.staging.tax.service.gov.uk/job/one-stop-shop-returns-performance-tests/)

### Scalafmt
 This repository uses [Scalafmt](https://scalameta.org/scalafmt/), a code formatter for Scala. The formatting rules configured for this repository are defined within [.scalafmt.conf](.scalafmt.conf).

 To apply formatting to this repository using the configured rules in [.scalafmt.conf](.scalafmt.conf) execute:

 ```
 sbt scalafmtAll
 ```

 To check files have been formatted as expected execute:

 ```
 sbt scalafmtCheckAll scalafmtSbtCheck
 ```

[Visit the official Scalafmt documentation to view a complete list of tasks which can be run.](https://scalameta.org/scalafmt/docs/installation.html#task-keys)
