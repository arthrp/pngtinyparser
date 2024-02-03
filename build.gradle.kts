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
}
kotlin {
    jvmToolchain(17)
}