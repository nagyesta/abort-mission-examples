plugins {
    id("java")
    id("com.github.nagyesta.abort-mission-gradle-plugin") version "4.1.3"
}

group = "com.github.nagyesta.abort-mission.examples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("com.github.admlvntv:WeatherAPIcomLibrary:0.1.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    testImplementation("io.cucumber:cucumber-picocontainer:7.13.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testImplementation("org.junit.vintage:junit-vintage-engine:5.9.3")
    // Add Booster to integrate Abort-Mission
    testImplementation("com.github.nagyesta.abort-mission.boosters:abort.booster-cucumber-jvm:4.2.0")
}

// Configure Abort-Mission plugin
abortMission {
    version = "4.2.0"
    relaxedValidation = true
}

tasks.test {
    //define output file
    outputs.file(file("$buildDir/reports/abort-mission/abort-mission-report.json"))
    // Discover and execute JUnit Jupiter-based tests
    useJUnitPlatform()
    systemProperty("API_KEY", project.ext.properties.computeIfAbsent("apiKey") { "-" })
}

