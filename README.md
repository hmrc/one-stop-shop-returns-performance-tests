**This is a template README.md.  Be sure to update this with project specific content that describes your performance test project.**

# one-stop-shop-returns-performance-tests
Performance test suite for the `one-stop-shop-returns-frontend`, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.

## Running Locally - Amend one-stop-shop-returns-frontend application.conf

Remove any returns for performance test VRNs in MongoDB.

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

## Running on Staging - Run Mongo Query to clear the database of performance test users
Run the following mongo-query to delete previous returns for the performance test users:
```
use one-stop-shop-returns
db.returns.deleteMany({vrn:{$regex: /^1110/ }})
db.returns.find({vrn:{$regex: /^1110/ }}).pretty()
```


## Running the tests

Prior to executing the tests ensure you have:

* Docker - to start mongo container
* Installed/configured service manager

Run the following command to start the services locally:
```
docker run --rm -d --name mongo -d -p 27017:27017 mongo:4.0

sm --start PLATFORM_EXAMPLE_UI_TESTS -r --wait 100
```

Using the `--wait 100` argument ensures a health check is run on all the services started as part of the profile. `100` refers to the given number of seconds to wait for services to pass health checks.

## Logging

The template uses [logback.xml](src/test/resources) to configure log levels. The default log level is *WARN*. This can be updated to use a lower level for example *TRACE* to view the requests sent and responses received during the test.

#### Smoke test

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

#### Run the performance test

To run a full performance test against staging environment, implement a job builder and run the test **only** from Jenkins.

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