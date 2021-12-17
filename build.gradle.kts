plugins {
	id("java")
	id("java-library")
	id("jacoco")
	id("org.sonarqube") version "2.8"
	id("com.adarshr.test-logger") version "2.0.0"
	id("de.jansauer.printcoverage") version "2.0.0"
}

val libraryVersion : String by project
val repoKey: String by project

val author: String by project

group = "com.celadonsea"
version = "0.0.1"

apply(plugin = "java")
apply(plugin = "jacoco")
apply(plugin = "maven-publish")
apply(plugin = "de.jansauer.printcoverage")

java {
	targetCompatibility = JavaVersion.VERSION_11
	sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation("org.slf4j:slf4j-api:1.7.32")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.0")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.printCoverage)
}

tasks.jacocoTestReport {
	reports {
		xml.isEnabled = true
		csv.isEnabled = false
	}
	classDirectories.setFrom(sourceSets.main.get().output.asFileTree.matching {
		exclude("**/*Configuration.class")
		exclude("**/*Resource.class")
		exclude("**/*Exception.class")
		exclude("**/*Builder.class")
	})
}

printcoverage {
	coverageType.set("INSTRUCTION")
}

tasks.withType<org.gradle.jvm.tasks.Jar> {
	manifest {
		attributes["Created-By"] = author
		attributes["Implementation-Version"] = project.version
		attributes["Build-Revision"] = project.version
	}
}

configure<PublishingExtension> {
	repositories {
		maven {
		}
	}
}

val archives by configurations

configure<PublishingExtension> {
	publications {
		create<MavenPublication>("maven") {
			setArtifacts(archives.artifacts)
		}
	}
}
