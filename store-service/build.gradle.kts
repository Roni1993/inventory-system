import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("kapt") version "1.7.10"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "com.github.roni1993"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

// add generated sources to classpath
java.sourceSets["main"].java {
	srcDir("build/generated")
}

extra["solaceSpringCloudVersion"] = "2.3.0"
extra["springCloudVersion"] = "2021.0.4"

dependencies {
	// web related libs
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.graphql-java:graphql-java-extended-scalars:18.1")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// event processing
	implementation("com.solace.spring.boot:solace-jms-spring-boot-starter")
	implementation("org.mapstruct:mapstruct:1.5.2.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")

	// DB related libs
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.vladmihalcea:hibernate-types-52:2.19.1")
	implementation("org.flywaydb:flyway-core")
	implementation("com.querydsl:querydsl-core")
	implementation("com.querydsl:querydsl-jpa")
	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
//	kapt("jakarta.annotation:jakarta.annotation-api")
//	kapt("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
	runtimeOnly("org.postgresql:postgresql")

	// Other libs
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("joda-time:joda-time:2.11.1")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Test Libs
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework:spring-webflux")
	testImplementation("org.springframework.graphql:spring-graphql-test")
}

dependencyManagement {
	imports {
		mavenBom("com.solace.spring.cloud:solace-spring-cloud-bom:${property("solaceSpringCloudVersion")}")
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
