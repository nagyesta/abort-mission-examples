![Abort-Mission](../../.github/assets/Abort-Mission-logo_export_transparent_640.png)

[![GitHub license](https://img.shields.io/github/license/nagyesta/abort-mission-examples?color=informational)](https://raw.githubusercontent.com/nagyesta/abort-mission-examples/main/LICENSE)
[![Java version](https://img.shields.io/badge/Java%20version-17-yellow?logo=java)](https://img.shields.io/badge/Java%20version-17-yellow?logo=java)
[![JavaCI](https://img.shields.io/github/actions/workflow/status/nagyesta/abort-mission-examples/build.yml?logo=github&branch=main)](https://github.com/nagyesta/abort-mission-examples/actions/workflows/build.yml)

[![last_commit](https://img.shields.io/github/last-commit/nagyesta/abort-mission-examples?logo=git)](https://img.shields.io/github/last-commit/nagyesta/abort-mission-examples?logo=git)
[![wiki](https://img.shields.io/badge/See-Wiki-informational)](https://github.com/nagyesta/abort-mission/wiki)

This folder shows how you can integrate Abort-Mission with your existing project while using TestNG.
Please make sure to read the generic overview in the repository root for more information about the example.

### Points of interest

- [Gradle configuration](build.gradle.kts) adding the necessary dependencies
- [MissionOutlineDefinition](src/test/java/com/github/nagyesta/abortmission/examples/MissionOutlineDefinition.java) defining the matchers and how they should abort execution
- [WeatherRepositoryTest](src/test/java/com/github/nagyesta/abortmission/examples/WeatherRepositoryTest.java) adding the necessary annotation and listener

### Running this example

```bash
# Run the tests and generate the report (even after they failed)
./gradlew clean build -P apiKey=none
```

Once executed, check [build/reports/abort-mission/abort-mission-report.html](build/reports/abort-mission/abort-mission-report.html) to see the generated report.

### More information

[Booster home](https://github.com/nagyesta/abort-mission/tree/main/boosters/booster-testng)
