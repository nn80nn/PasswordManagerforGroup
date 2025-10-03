plugins {
    kotlin("jvm") version "2.0.21"
    id("org.jetbrains.dokka") version "1.9.0"
    id("info.solidsoft.pitest") version "1.15.0"
    application
}

group = "nvi.safe"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}


tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.dir("dokka"))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
}

configurations.pitest {
    extendsFrom(configurations.testImplementation.get())
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("nvi.safe.MainKt")
}

pitest {
    junit5PluginVersion.set("1.2.1")
    targetClasses.set(listOf("nvi.safe.*"))
    targetTests.set(listOf("nvi.test.*", "nvi.safe.*"))
    outputFormats.set(listOf("HTML", "XML"))
    timestampedReports.set(false)
    threads.set(Runtime.getRuntime().availableProcessors())
    mutators.set(listOf("DEFAULTS"))
    verbose.set(true)
    timeoutConstInMillis.set(10000)
    mutationThreshold.set(50)
    coverageThreshold.set(50)
}