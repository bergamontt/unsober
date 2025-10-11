plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    `java-library`
    `maven-publish`
}

group = "ua.unsober"
version = "0.0.1-SNAPSHOT"
description = "spring-boot-starter-map-query"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.5.6")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("org.springframework.boot:spring-boot-autoconfigure")
    compileOnly("org.projectlombok:lombok:1.18.40")
    annotationProcessor("org.projectlombok:lombok:1.18.40")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "ua.unsober"
            artifactId = "spring-boot-starter-map-query"
            version = "0.0.1-SNAPSHOT"

            from(components["java"])
        }
    }
}