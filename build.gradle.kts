plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.anmi."
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
configurations{
	configureEach {
		exclude("org.springframework.boot","spring-boot-starter-logging")
		exclude("commons-logging","commons-logging")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<Copy>("unpack") {
	dependsOn(tasks.bootJar)
	from(zipTree(tasks.bootJar.get().outputs.files.singleFile))
	into("build/libs")
}