plugins {
    id("java")
    id("com.github.nagyesta.abort-mission-gradle-plugin") version "4.1.43"
}

group = "com.github.nagyesta.abort-mission.examples"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("com.github.admlvntv:WeatherAPIcomLibrary:0.1.0") {
        exclude("com.google.guava", "guava")
    }
    implementation("com.fasterxml.jackson.core:jackson-core:2.16.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    testImplementation("org.testng:testng:7.8.0")
    // HINT: Add Booster to integrate Abort-Mission
    testImplementation("com.github.nagyesta.abort-mission.boosters:abort.booster-testng:4.2.99")
}

// HINT: Configure Abort-Mission plugin
abortMission {
    version = "4.2.0"
}

tasks.test {
    // Define output file
    outputs.file(file("$buildDir/reports/abort-mission/abort-mission-report.json"))
    useTestNG()
    // Pass the API key if you have one provided
    systemProperty("API_KEY", project.ext.properties.computeIfAbsent("apiKey") { "-" })
}

