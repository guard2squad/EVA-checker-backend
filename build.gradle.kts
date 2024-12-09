import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.3.3" apply false
    id("io.spring.dependency-management") version "1.1.6" apply false
}

subprojects {
    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    group = "com.g2s"
    version = "0.0.1-SNAPSHOT"

    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        "implementation"("org.springframework.boot:spring-boot-starter")
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        "implementation"("org.jetbrains.kotlin:kotlin-reflect")
        "implementation"("com.fasterxml.jackson.module:jackson-module-kotlin")
        "implementation"("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
        "testImplementation"("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "com.vaadin.external.google", module = "android-json")
        }
        "implementation"("org.springframework.data:spring-data-commons")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":web") {
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    jar.enabled = false
    bootJar.enabled = true

    dependencies {
        "implementation"(project(":domain"))
        "runtimeOnly"(project(":infra"))
        "implementation"("org.springframework.boot:spring-boot-starter-web")
        "implementation"("org.springframework.boot:spring-boot-starter-security:3.3.0")
        "implementation"("io.jsonwebtoken:jjwt:0.9.1")
        "implementation"("com.sun.xml.bind:jaxb-impl:4.0.1")
        "implementation"("com.sun.xml.bind:jaxb-core:4.0.1")
        "implementation"("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    }
}

project(":infra") {
    dependencies {
        "implementation"(project(":domain"))
        "implementation"("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.15.3")
        "implementation"("org.springframework.data:spring-data-mongodb:4.1.8")
        "implementation"("org.mongodb:mongodb-driver-kotlin-sync:4.11.0")
    }
}

project(":domain") {
    dependencies {
    }
}
