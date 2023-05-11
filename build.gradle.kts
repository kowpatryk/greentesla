import org.gradle.api.JavaVersion.VERSION_17

plugins {
	java
	id("io.freefair.lombok") version "8.0.1"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.springframework.boot") version "3.0.6"
}

group = "com.maracuya"

java.sourceCompatibility = VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")

	testImplementation(platform("io.rest-assured:rest-assured-bom:5.3.0"))

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.rest-assured:rest-assured")
	testImplementation("io.rest-assured:json-path")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootJar {
	archiveFileName.set("application.jar")
}
