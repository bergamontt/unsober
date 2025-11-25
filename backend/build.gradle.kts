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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-cache")

    implementation("org.mapstruct:mapstruct:${Versions.mapstruct}")
    implementation("org.apache.poi:poi:${Versions.poi}")
    implementation("org.apache.poi:poi-ooxml:${Versions.poiOoxml}")
    implementation("org.apache.logging.log4j:log4j-core:${Versions.log4j}")
    implementation("org.jsoup:jsoup:${Versions.jsoup}")
    implementation("ua.unsober:unsober-starter-map:${Versions.unsoberStarer}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Versions.openapi}")

    compileOnly("org.projectlombok:lombok:${Versions.lombok}")

    annotationProcessor("org.projectlombok:lombok:${Versions.lombok}")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:${Versions.lombokMapstruct}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${Versions.mapstruct}")

    runtimeOnly("org.postgresql:postgresql:${Versions.postgresql}")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
