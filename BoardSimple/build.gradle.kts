plugins {
	java
	id("org.springframework.boot") version "2.7.14"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.study"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// 타임리프
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	// 검증
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// 웹
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.3.1")
	testImplementation("junit:junit:4.13.1")

	// 롬복
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// 마이바티스
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.3.1")

	// jwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// mysql
	implementation("mysql:mysql-connector-java:8.0.28")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
