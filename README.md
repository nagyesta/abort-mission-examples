![Abort-Mission](.github/assets/Abort-Mission-logo_export_transparent_640.png)

[![GitHub license](https://img.shields.io/github/license/nagyesta/abort-mission-examples?color=informational)](https://raw.githubusercontent.com/nagyesta/abort-mission-examples/main/LICENSE)
[![Java version](https://img.shields.io/badge/Java%20version-17-yellow?logo=java)](https://img.shields.io/badge/Java%20version-17-yellow?logo=java)
[![JavaCI](https://img.shields.io/github/actions/workflow/status/nagyesta/abort-mission-examples/build.yml?logo=github&branch=main)](https://github.com/nagyesta/abort-mission-examples/actions/workflows/build.yml)

[![last_commit](https://img.shields.io/github/last-commit/nagyesta/abort-mission-examples?logo=git)](https://img.shields.io/github/last-commit/nagyesta/abort-mission-examples?logo=git)
[![wiki](https://img.shields.io/badge/See-Wiki-informational)](https://github.com/nagyesta/abort-mission/wiki)

Abort-Mission is a lightweight Java library providing flexible test abortion support for test groups to allow fast failures. 
This repository is demonstrating how it can be integrated while using various frameworks and tools.

### About the examples

This repository contains made-up examples, which are created just to show a proof of concept. There is no reasonable 
expectation about it to show Abort-Mission as the only solution to these problems.

All examples show the same scenario, dealing with a very simple Weather API integration that requires an API key.
If the API key is not provided, we know, that many of our tests will send requests to the API and fail. As we would like 
to avoid sending unnecessary requests as well as waiting pointlessly for the test failures to happen, we have integrated 
Abort-Mission to tell our test framework of choice to stop executing the tests which require an API key in case it was 
not provided or was not working during the first call.

In any of the examples, the key integration points are commented with a message starting with `HINT:` such as:

```java
// HINT: Add Abort-Mission annotation
```

or

```html
<!-- HINT: Add Abort-Mission dependency -->
```

In order to see these integration points more easily, a list of them is added to the readme of each module.

It is recommended to execute the examples either without an API key or using an invalid key in order to see how
Abort-Mission behaves, then look for the `reports/abort-mission/abort-mission-report.html` under the build directory
to see the generated report.

### Contents

- Gradle
  - [Cucumber JVM](gradle/cucumber)
  - [JUnit 4](gradle/junit4)
  - [JUnit Jupiter](gradle/jupiter)
  - [TestNG](gradle/testng)
- Maven
  - [JUnit Jupiter](maven/mvn-jupiter)
