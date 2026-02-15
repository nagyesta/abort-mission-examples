plugins {
    id("java")
    id("com.github.nagyesta.abort-mission-gradle-plugin") version "5.2.28"
}

group = "com.github.nagyesta.abort-mission.examples"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17

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
    implementation("com.fasterxml.jackson.core:jackson-core:2.21.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.21.0")
    testImplementation("io.cucumber:cucumber-picocontainer:7.34.2")
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.3")
    testImplementation("org.junit.vintage:junit-vintage-engine:6.0.3")
    testImplementation("org.junit.platform:junit-platform-engine:6.0.3")
    testImplementation("org.junit.platform:junit-platform-launcher:6.0.3")
    // HINT: Add Booster to integrate Abort-Mission
    testImplementation("com.github.nagyesta.abort-mission.boosters:abort.booster-cucumber-jvm:7.0.9")
}

// HINT: Configure Abort-Mission plugin
abortMission {
    relaxedValidation = true
}

tasks.test {
    // Define output file
    outputs.file(layout.buildDirectory.file("reports/abort-mission/abort-mission-report.json").get().asFile)
    useJUnitPlatform()
    // Pass the API key if you have one provided
    systemProperty("API_KEY", project.ext.properties.computeIfAbsent("apiKey") { "-" })
}

