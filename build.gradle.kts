import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import javax.xml.ws.Endpoint.publish

plugins {
    // It applies the Java Library plugin,
    // adds the gradleApi() dependency to the api configuration
    // and performs validation of plugin metadata during jar task execution
    id("java-gradle-plugin")
    // Kotlin plugins for Gradle
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("maven-publish")
}


configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }
}

repositories {
    mavenCentral()
}

dependencies {
}

group = "com.mshark.plugin"
version = "0.1-SNAPSHOT"
gradlePlugin {
    plugins {
        create("buildmetricsGradlePlugin") {
            id = "buildmetrics"
            displayName = "Build metrics gradle plugin."
            description = "Collects build metrics and writes them to a file."
            implementationClass = "com.mshark.buildmetrics.BuildMetricsPlugin"
        }
    }
}

