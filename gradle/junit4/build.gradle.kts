plugins {
    id("java")
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
    testImplementation("junit:junit:4.13.1")
    // Add Booster to integrate Abort-Mission
    testImplementation("com.github.nagyesta.abort-mission.boosters:abort.booster-junit4-experimental:4.2.0")
}

tasks.test {
    // Discover and execute JUnit4-based tests
    useJUnit()
    systemProperty("API_KEY", project.ext.properties.computeIfAbsent("apiKey") { "-" })
}

