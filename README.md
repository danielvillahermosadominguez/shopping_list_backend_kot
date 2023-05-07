# shopping_list_backend_kot
Backend shopping list

# Spring boot configuration
https://spring.io/guides/tutorials/spring-boot-kotlin/

For the spring boot configuration we could create it with https://start.spring.io  or with intellij.
Once, we are going to create the project with the following parameters:
- gradle with groovy
- kotlin 
and some libraries as for example:
- Spring web
- security
- graphql
- ...
You will see the dependencies in  the build.gradle file
``` groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-graphql'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'com.fasterxml.jackson.module:jackson-module-kotlin'
    implementation 'org.flywaydb:flyway-core'
    implementation 'org.jetbrains.kotlin:kotlin-reflect'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework:spring-webflux'
    testImplementation 'org.springframework.graphql:spring-graphql-test'
    testImplementation 'org.springframework.security:spring-security-test'
}
```

We are using the JDK 18. So, we would need to see the compatibility matrix for gradle:

https://docs.gradle.org/current/userguide/compatibility.html

our gradle version should be the 7.5. We can see it in:
```
gradle --version
------------------------------------------------------------
Gradle 7.5.1
------------------------------------------------------------

Build time:   2022-08-05 21:17:56 UTC
Revision:     d1daa0cbf1a0103000b71484e1dbfe096e095918

Kotlin:       1.6.21
Groovy:       3.0.10
Ant:          Apache Ant(TM) version 1.10.11 compiled on July 10 2021
JVM:          18.0.1.1 (Oracle Corporation 18.0.1.1+2-6)
OS:           Windows 11 10.0 amd64
```
You could have a different version of gradle installed in the project.

```
gradlew --version
------------------------------------------------------------
Gradle 7.5.1
------------------------------------------------------------

Build time:   2022-08-05 21:17:56 UTC
Revision:     d1daa0cbf1a0103000b71484e1dbfe096e095918

Kotlin:       1.6.21
Groovy:       3.0.10
Ant:          Apache Ant(TM) version 1.10.11 compiled on July 10 2021
JVM:          18.0.1.1 (Oracle Corporation 18.0.1.1+2-6)
OS:           Windows 11 10.0 amd64
```
if you want to change it, you will need to change the configuration file .\gradle\gradle-wrapper.properties
and a new version will be downloaded.
```
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-7.5.1-bin.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```
Also, you should change the build.gradle in the following

```
group = 'com.shoppinglist'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '18' <== 
....
tasks.withType(KotlinCompile) {
    kotlinOptions {
        freeCompilerArgs = ['-Xjsr305=strict']
        jvmTarget = '18' <==
    }
}
...
```
# Spring tests - some basic examples

With Spring we will have several types of tests.
- Unit tests
- Integration tests
- Controller tests
  https://www.baeldung.com/kotlin/spring-boot-testing
- 
## First unit test without application context

We can create the first unit test with junit 5, jupiter. it is the default
testing library with spring boot. You will se you don't have this reference in the
gradle.build file.

``` kotlin
package com.shoppinglist.unit

import org.junit.jupiter.api.Test

class ExampleUnitTestJunit {
    @Test
    fun `it should be true` () {
        assert(true);
    }
}

You could see the version of junit you are using in the project, in the
part of "External libraries". In our cases we have the version 5.9.2
* junit-jupiter
* junit-jupiter-api
* junit-jupiter-engine
* junit-jupiter-params
* junit-platform-commins:1.9.2
* junit-platform-engine: 1.9.2

and mockito, but we won't use it.

```
But in our case, we want to use for unit tests other library:
(in gradle.build)
```
dependencies {
 ...
    testImplementation 'io.mockk:mockk:1.13.4'
    testImplementation 'org.assertj:assertj-core:3.24.2'
    testImplementation 'org.jetbrains.kotlin:kotlin-test'
    testImplementation 'io.kotest:kotest-runner-junit5:5.6.1'
}
```
we will include the kotest-runer-junit 5 and for mocking we will use
mockk. Also, we want to use mockk

With kotest-runner-junit5 we could use the FreeSpec tests

NOTE: if you want to see the test working in the terminal, you should
add a new plugin:

```
plugins {
 ...
    // Apply the application plugin to add support for building a CLI application in Java.
    id 'com.adarshr.test-logger' version '3.2.0'
}
...
tasks.named('test') {
    testlogger {
        useJUnitPlatform()
    }
}
```
You have more information about it here:
https://plugins.gradle.org/plugin/com.adarshr.test-logger
You could customize the output.

There are more ways to configure it, but it is the simpler. More information here:
https://stackoverflow.com/questions/3963708/gradle-how-to-display-test-results-in-the-console-in-real-time

```
gradlew clean test <- execute with information every time (in other case, don't show the test if there is not changes)
gradlew test -i  <- execute with more information. Usually irrelevant

```
NOTE: Some tests, moreover, the default test could load the context and services.
It is because they use the notation @SpringBootTest in the test. But this kind of unit
test doesn't load anything and they are very fast to run.

## First unit test with context
https://www.baeldung.com/spring-5-junit-config
https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ContextConfiguration.html

Some unit tests will need to load the application context. You can use the notation:
@SpringJUnitConfig in the class or @ContextConfiguration

## Integration tests with @SpringBootTest
with the @SpringBootTest you can load more things

## Controller tests
``` java
@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
public class EmployeeRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository repository;

    // write test cases here
}
```
## JPA Tests
@DataJpaTest

# ktlintFormat and detekt

# Cucumber with spring configuration

# Mokkt example

# Wiremock

# Sonarqube

# linter

# graphql

# security

# migrations

# SQLite

# Postgree

# NoSQL

