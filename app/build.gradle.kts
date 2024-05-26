plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    annotationProcessor(libs.avaje.inject.processor)
    annotationProcessor(libs.avaje.http.processor)
    annotationProcessor(libs.avaje.jsonb.processor)
    annotationProcessor(libs.jdbi.processor)
    annotationProcessor(libs.jstachio.processor)
    implementation(libs.avaje.inject)
    implementation(libs.avaje.http)
    implementation(libs.avaje.jsonb)
    implementation(libs.avaje.config)
    implementation(libs.javalin)
    implementation(libs.bundles.jdbi)
    runtimeOnly(libs.logevents)
    implementation(libs.sqlite)
    implementation(libs.liquibase)
    implementation(libs.slf4j)
    implementation(libs.jstachio.compile)

    testAnnotationProcessor(libs.avaje.inject.processor)
    testImplementation(libs.avaje.inject.test)
    testImplementation(libs.avaje.http.client)
    testImplementation("org.assertj:assertj-core:3.26.0")
    testImplementation("org.jsoup:jsoup:1.17.2")

}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.10.0")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("org.ethelred.minecraft.events.Main")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<JavaExec>().configureEach {
    args("--enable-preview")
}

tasks.withType<Test>().configureEach {
    jvmArgs("--enable-preview")
}
