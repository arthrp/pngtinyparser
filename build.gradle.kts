plugins {
    kotlin("jvm") version "1.9.21"
    `maven-publish`
}

group = "org.arthrp"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

}

publishing {
    publications {
        create("mavenJava", MavenPublication::class) {
            from(components["java"])

            groupId = "com.arthrp"
            artifactId = "pngtinyparser"
            version = "0.1.0"
        }
    }

    repositories {
        mavenLocal()
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        afterSuite( KotlinClosure2({ desc: TestDescriptor, result: TestResult ->
            if (desc.parent == null) { // will match the outermost suite
                println("Result: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} passed, ${result.failedTestCount} failed, ${result.skippedTestCount} skipped)")
            }
        })
        )
    }
}
kotlin {
    jvmToolchain(17)
}