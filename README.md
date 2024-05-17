# one-stop-shop-returns-performance-tests
Performance test suite for the `one-stop-shop-returns-frontend`, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.

## Local setup - MongoDB and Service Manager

Run at least 4.0 with a replica set: `docker run --restart unless-stopped -d -p 27017-27019:27017-27019 --name mongo4 mongo:4.0 --replSet rs0` 
Connect to said replica set: `docker exec -it mongo4 mongo` 
When that console is there: `rs.initiate()` 
You then should be running 4.0 with a replica set. You may have to re-run the rs.initiate() after you've restarted

sm --start ONE_STOP_SHOP_ALL -r

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
