
plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "ua.unsober"
version = "0.0.1-SNAPSHOT"
description = "backend"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    testImplementation("org.springframework.security:spring-security-test")
    compileOnly("org.projectlombok:lombok:1.18.40")
    annotationProcessor("org.projectlombok:lombok:1.18.40")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    implementation("org.apache.poi:poi:5.4.1")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("org.apache.logging.log4j:log4j-core:2.25.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("org.postgresql:postgresql:42.7.7")
    runtimeOnly("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.jsoup:jsoup:1.21.2")
    implementation("ua.unsober:unsober-starter-map:0.0.1-SNAPSHOT")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

fun loadEnv(): Map<String, String> {
    val envFile = file(".env")
    if (!envFile.exists()) return emptyMap()

    return envFile.readLines()
        .filter { it.isNotBlank() && !it.trim().startsWith("#") }.associate {
            val (key, value) = it.split("=", limit = 2)
            key.trim() to value.trim()
        }
}

openApi {
    apiDocsUrl.set("http://localhost:8080/api/v3/api-docs")
    outputDir.set(layout.buildDirectory.dir("openapi"))
    outputFileName.set("openapi.json")
    waitTimeInSeconds.set(30)

    customBootRun {
        environment.putAll(loadEnv())
    }
}
