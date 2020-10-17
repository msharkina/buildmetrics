# Buildmetrics Gradle Plugin
* A gradlew plugin to collect total build time and tasks build time.

### Plugin deployment:
By running  `./gradlew publishToMavenLocal` plugin is deployed to the local maven repository. 
By default  `~/.m2/repository`

### Apply plugin:
In project `build.gradle.kts`  sdd maven artifact dependency to the script classpath: 

`classpath("com.mshark.plugin:buildmetrics:0.1.0-SNAPSHOT")`

Applying a plugin in app’s `build.gradle.kts` will look like: 
```
plugins {
id("buildtime-plugin")
}
```
