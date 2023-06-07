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
https://www.baeldung.com/kotlin/spring-boot-testing
## Application test

When you are creating application test, you load the service
and the main idea is to test the service in a end to end way.

You could use FreeSpec.

The key in this class of tests is the notation @SpringBootTest which
allow to load the context and the service.

The application test, by using cucumber, we will call them "acceptance tests". But,
the concept is the same, the only difference is we are using cucumber for the tests.

You can see it in the Cucumber configuration explaination.

## MockK, kotest and assertions
https://gradlehero.com/how-to-exclude-gradle-dependencies/
https://www.baeldung.com/kotlin/kotest

We will need mocking capabilities for our tests and Mockito is the default mocking service in
Spring boot. The recomendation is to exclude the mockito-core dependency.

You can see the dependencies in "External libraries" where you can see:
- mockito-core
- mockito-junit-jupiter

testImplementation 'org.springframework.boot:spring-boot-starter-test'

```
dependencies {
    //testImplementation 'io.kotest:kotest-runner-junit5-jvm:5.6.2'  <-- not neccesary
    testImplementation 'io.mockk:mockk:1.13.5'
    testImplementation 'io.kotest:kotest-runner-junit5:5.6.2'
    testImplementation 'io.kotest:kotest-assertions-core:$version'
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    
    testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude group: 'org.mockito', module: 'mockito-core'   
        exclude group: 'org.mockito', module: 'mockito-junit-jupiter'     
    }
    testImplementation 'com.ninja-squad:springmockk:4.0.2'
    testImplementation 'org.assertj:assertj-core:3.24.2'
    ...
     
}
```
Every library has a reason:
* io.mockk (https://mockk.io/). This is the mocking library for Kotlin. Here you have all the documentation.
  * mockk. In the maven repository, the last version is the 1.13.5 at this moment
* com.ninja-squad (https://github.com/Ninja-Squad/springmockk). This is the spring support for mockk
  * springmockk:3.0.1. With this library you could have MockBean and Spybewan
* io.kotest:(https://kotest.io/docs/quickstart/) This is the test framework for kotlin
  - kotest-runner-junit5-jvm:5.6.2 ==> compatibility with junit 5
  - kotest-runner-junit5: 5.6.2 ==>  (the same) compatibility with junit you need it to run the tests with junit
NOTE: The kotest-runner-junit5 install the following dependencies:
    - i.kotest:kotest-assertions-api-jvm
    - o.kotest:kotest-assertions-core-jvm
    - io.kotest:kotest-shared-jvm
    - io.kotest:kotest-common-jvm
    - io.kotest:kotest-extensions-jvm
    - io.kotest:kotest-framework-api-jvm
    - io.kotest:kotest-framework-concurrency-jvm
    - io.kotest:kotest-framework-discovery-jvm
    - io.kotest:kotest-framework-engine-jvm
    - io.kotest:kotest-runner-junit5-jvm
    - 
But when you install the kotest-runner-junit-jvm, the following dependencies are installed:
    - i.kotest:kotest-assertions-api-jvm
    - io.kotest:kotest-assertions-core-jvm
    - io.kotest:kotest-shared-jvm
    - io.kotest:kotest-common-jvm
    - io.kotest:kotest-extensions-jvm
    - io.kotest:kotest-framework-api-jvm
    - io.kotest:kotest-framework-concurrency-jvm
    - io.kotest:kotest-framework-discovery-jvm
    - io.kotest:kotest-framework-engine-jvm
    - io.kotest:kotest-runner-junit5-jvm
In both cases, we have installed the same dependencies, so we should include only one of them
  * org.assertj
    * assertj-core: https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html
      * With assertjcore we have better assertion.
      * Kotest have some assertions

Regarding io.kotest.extensions:kotest-extensions-spring, you need to take into account in the last
versions of spring you will need it for FreeSpecs tests. In other way, they won't able to load the context.
https://stackoverflow.com/questions/53277045/how-does-kotlintest-test-spring-boot-application

### Differences in the assertions with assertJ and kotests

Certain different aspects. If you don't want to user assertj, you could remove the dependency
https://kotest.io/docs/assertions/assertions.html

```
   @Test
    fun it_should_be_true_example_classic_springboot_appTest() {
        assertThat(true).isTrue() // AssertJ
        true shouldBe true // kotest assertions
    }
```
### The problem with the database

After creating a dummy entity and maybe because we are using flyway, when the context loads, spring boot miss
a database. With the objective to make it works, we configure it with a H2 database. We will see if we want to 
change it in the future.

To do this, we should configure the application.yml with:

``` typical configuration
spring.datasource.driver-class-name= org.h2.Driver
spring.datasource.url=jdbc:h2:mem:localhost;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username= admin
spring.datasource.password=
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create
```
and you only have to include the following dependency. The driver for h2 in the build.gradle

```
    implementation 'com.h2database:h2'
```

These sentences are very important for JPA: 
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create

In the example we are using H2, and the table will be in memory. So, in this way, the scheme will be 
self-created depending on the entities


You have more information here: https://stackoverflow.com/questions/51221777/failed-to-configure-a-datasource-url-attribute-is-not-specified-and-no-embedd
### The problem of the login

Well, you have configurated spring boot with security, so, you will need to login to access to the api. You will see, when
you access to http://localhost:8080, that you will see a screen to login.
https://spring.io/guides/gs/securing-web/
https://www.baeldung.com/spring-security-login
https://reflectoring.io/spring-security/

if we remove the following library
```
implementation 'org.springframework.boot:spring-boot-starter-security'
```
you won't have the problem, but also you won't have security. In this way you could access to the
http://localhost:8080/api/dummy and will receive an empty json, as part of the exmaple.

But if you activate the security, you will need to do a basic configuration.
```
spring.security.user.name = admin
spring.security.user.password = admin
``` 
In your tests, you will need to user for example :   @WithMockUser(value = "spring")

## Controller tests
If you have taken into account the previous configurations, you only will have to take into account:
1. You can double the service, by using an stub. You can use MockkBean instead MockBean, because
   you are not using already mockito
2. For the security, you could use @WithMockUser, and you will able to call the endpoint
3. with mockmvc you will call the controller, testing only the part of the call

``` kotlin
@WebMvcTest
@ContextConfiguration(classes = [(ShoppingListBackendKotApplication::class)])
class ExampleControllerTestClassicSpringBoot(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var dummyService: DummyService

    @WithMockUser(value = "spring")
    @Test
    fun this_is_an_example_with_mvc() {
        every {dummyService.get()} returns DummyEntity()
        mockMvc.perform(get("http://localhost:8080//api/dummy")).
            andExpect(status().isOk)
    }
}
```
For FreeSpecs it is the same thing, but be careful with the configuration of dependencies. See the previous
recommendations.

## JPA Tests

There are some tips to take into account:
- We use EnableAutoconfiguration and ComponentScan, because there is an
  error in intellij, where in spite of working, the error with the autowire
  and the respository is something that only can be solved with this notations.
- Review the configuration of the H2, because in other case it is not going to work.
- The context configuration is neccesary to work.
@DataJpaTest
``` kotlin
@DataJpaTest
@ContextConfiguration(classes = [(ShoppingListBackendKotApplication::class)])
@ComponentScan("com.shoppinglist.application.dummy")
@EnableAutoConfiguration
class ExampleJPAIntegrationTestClassicSpring {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var repository: DummyRepository

    @Test
    fun dummyExampleOfIntegrationTestWithDB() {
        val dummyEntity = DummyEntity()
        entityManager.persist(dummyEntity)
        entityManager.flush()
        val theSameDummyEntity = this.repository.findAll().toList();
        assertThat(theSameDummyEntity.size).isEqualTo(1)
        assertThat(theSameDummyEntity[0]).isEqualTo(dummyEntity)
    }
}

```
# ktlintFormat and detekt

## ktlint
https://github.com/jeremymailen/kotlinter-gradle

You only have to add the plugin
```gradle
plugins {
    ...
    id 'org.jmailen.kotlinter' version '3.14.0'
}
```
and you will able to execute:

```
.\gradlew lintkotlin  <-- it will only report the format problems 
.\gradle formatKotlin <- it will change the format
```

In addition, you could create a hoock for Git. Adding the following
in the build.gradle
```gradle
tasks.named('check') {
    dependsOn 'installKotlinterPrePushHook'
}
```
This hook, will execute the lintKotlin and if there are errors, formatKotlin.

the problem I have found with this approach is if you execute .\gradlew test, it has stopped
working.
## ktlint - another way to configure your project
A new dependency in the build.gradle

```
...
configurations {
    ktlint
}

...

dependencies {
runtimeOnly 'com.pinterest.ktlint:ktlint-core:0.49.1'

..
}
...
task ktlint(type: JavaExec, group: "verification") {
    description = "Check Kotlin code style."
    classpath = configurations.ktlint
    main = "com.pinterest.ktlint.Main"
    args "src/**/*.kt"
}

task ktlintFormat(type: JavaExec, group: "formatting") {
    description = "Fix Kotlin code style deviations."
    classpath = configurations.ktlint
    main = "com.pinterest.ktlint.Main"
    args "-F", "src/**/*.kt"
}
```
And with this, you could execute : "gradlew ktlint" and "gradlew ktlintFormat"

With java > 16 you are going to have some problems and you should change certain things
in the ktlintFormat to work properly:

``` gradle
...
task ktlintFormat(type: JavaExec, group: "verification") {
jvmArgs "--add-opens", "java.base/java.lang=ALL-UNNAMED"
description = "Fix Kotlin code style deviations."
classpath = configurations.ktlint
mainClass = "com.pinterest.ktlint.Main"
args "-F", "src/**/*.kt"
}
```

In addition, you could add a pre-commit:

```
task installGitHooks(type: Copy, group: "git-hooks") {
    from new File(rootProject.rootDir, 'scripts/pre-commit')
    into { new File(rootProject.rootDir, '.git/hooks')}
    fileMode 0775
}
check.dependsOn installGitHooks
...
<Inmediatly after the task of ktlint, include>
check.dependsOn ktlint
```

In addition, you will need a folder with a file with the following:
scripts/pre-commit
``` bash
#!/bin/sh
echo "▶️ Running pre-commit checks..."

./gradlew ktlint
EXIT_CODE=$?

if [ ${EXIT_CODE} -ne 0 ]; then
    echo "😵 Pre Commit checks failed. Please fix the above issues before committing"
    exit ${EXIT_CODE}
else
    echo "✅ Pre Commit checks passed, no problems found"
    exit 0
fi
```

NOTE: The only problem that I have found it you need to install the pre-commit using
        .\gradlew installGitHooks

## Detekt
https://github.com/detekt/detekt
Detekt is code analysis for kotlin. I supose we will use it for sonarqube.
With this we look for code smells.

This is the website:
https://detekt.dev/
https://detekt.dev/docs/intro/
https://hofstede-matheus.medium.com/improve-code-quality-with-ktlint-detekt-and-git-hooks-d173722594e4
https://plugins.gradle.org/plugin/io.gitlab.arturbosch.detekt

Configuramos el .build
```
plugins {
    ..
    id 'org.jetbrains.kotlin.jvm' version '1.8.21' <-- make a look to the version
    id 'org.jetbrains.kotlin.plugin.spring' version '1.8.21' <-- make a look to the version
    ...
    id "io.gitlab.arturbosch.detekt" version "1.23.0"
}

...
dependencies {
    implementation 'io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.0'
    ...
    
}


detekt {
    config = files("$projectDir/config/detekt/detekt.yml")
    input = files("src/main/java", "src/main/kotlin")
}
```
And you will need to create a empty file in config/detekt/detekt.yml

You will create the rules here

You have more information in: https://detekt.dev/docs/introduction/configurations/

You can execute with:

```
.\gradlew detekt

```

# Cucumber with spring configuration
1) You need to create the first dummy feature in 
    src/test/resources/cucumber/features/dummy.feature

``` gherkin
Feature: Dummy Feature

  Scenario: Dummy Scenario
    Given a dummy given
    When a dummy thing happens
    Then a dummy result happens too
```

2) We need to create the steps and the runner to work with junit
```kotlin
package com.shoppinglist.acceptance

package atm.account.steps

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

//https://github.com/jecklgamis/cucumber-jvm-kotlin-example/blob/main/src/test/kotlin/runner/ExampleFeatureTest.kt

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["classpath:cucumber/features/dummy.feature"],
    glue = ["classpath:com/shoppinglist/acceptance"],
    plugin = ["pretty", "json:target/jsonReports/acceptance.json", "html:target/cucumber/html", "html:target/cucumber/acceptance.html"]
)
class CucumberAppTestRunner
```

We need some dependencies in our .gradle
```gherkin    
    testImplementation 'io.cucumber:cucumber-java:7.11.1'
    testImplementation 'io.cucumber:cucumber-junit:7.11.1'
    testImplementation 'org.junit.vintage:junit-vintage-engine:5.9.2'
    testImplementation 'io.cucumber:cucumber-junit-platform-engine:7.11.1'
```
https://github.com/jecklgamis/cucumber-jvm-kotlin-example/blob/main/src/test/kotlin/runner/ExampleFeatureTest.kt

Take into account, cucumber runs with junit 4, and we need compatibility with junit 5,so
we will need the junit.vintaje and the plarform-engine

In addition, you will ne to take into account the cucumber test, (Aceptance test), will be
very similar to the application test, so we should configure them for this.

```
package com.shoppinglist.acceptance

import com.shoppinglist.application.ShoppingListBackendKotApplication
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest


@CucumberContextConfiguration
@SpringBootTest(classes = [(ShoppingListBackendKotApplication::class)])
class CucumberSpringContextConfiguration {
}
```

# Jacoco coverage
For this, you will need to include code in your build.gradle

https://medium.com/@ranjeetsinha/jacoco-with-kotlin-dsl-f1f067e42cd0
https://docs.gradle.org/current/userguide/jacoco_plugin.html

``` gradle
..
plugins {
  ...
    id 'jacoco'
}
...

jacocoTestReport {
    dependsOn test
    executionData tasks.withType(Test).findAll { it.state.executed }
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.collect {
            fileTree(dir: it, exclude: [])
        }))
    }
    reports {
        xml.setEnabled(true)
        html.setEnabled(true)
        html.destination file("${buildDir}/jacocoHtml")
    }
}

jacocoTestCoverageVerification {
    dependsOn test
    executionData tasks.withType(Test).findAll { it.state.executed }
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.collect {
            fileTree(dir: it, exclude: [])
        }))
    }
    violationRules {
        rule {
            limit {
                counter = 'INSTRUCTION'
                minimum = 0.8
            }
            limit {
                counter = 'BRANCH'
                minimum = 0.8
            }
            limit {
                counter = 'LINE'
                minimum = 0.8
            }
            limit {
                counter = 'METHOD'
                minimum = 0.8
            }
            limit {
                counter = 'CLASS'
                minimum = 0.8
            }
        }
    }
}
```

And now, you could execute:
```ssh
.\gradlew test --> the coverage will be generated in the build/jacoco folder
.\gradle jacocoTestReport --> the reports will be generated in html in the build/jacocoHTML
.\gradlew jacocoTestCoverageVerification --> you could use some quality gates.
```


# Sonarqube
You will need to create a sonar-project properties

```
sonar.projectKey=danielvillahermosadominguez_shopping_list_backend_kot
sonar.organization=danielvillahermosadominguez

sonar.projectName=shopping_list_backend_kot
sonar.projectVersion=latest

sonar.sources=src/main/kotlin
sonar.tests=src/test/kotlin
sonar.language=java
sonar.sourceEncoding=UTF-8

sonar.coverage.jacoco.xmlReportPaths= build/reports/jacoco/test/jacocoTestReport.xml
sonar.junit.reportsPath=build/test-results/test/TEST-*.xml
#sonar.coverage.exclusions=src/main/kotlin

```

And if you want to include it in github actions

## Github actions with sonarqube


# Docker image

We are going to use for docker, a base with jdk 18. This will be:
https://hub.docker.com/layers/library/openjdk/18-jdk-alpine3.14/images/sha256-4f88ea57de45e1da177f4ed66ab5cfcffedc499cc0500600219fd56921b92db3

So we need to create a file "Dockerfile" where we can write the following:

```dockerfile
FROM openjdk:18-jdk-alpine3.14

WORKDIR /app

COPY . /app

RUN ./gradlew build

EXPOSE 8080

CMD sh start.sh
```

We are going to do for this example, the same we would do in our local. We will see other beter way to
do it in the future.

As we see we base our docker image in jdk-19 and we copy all the folder to this image (code, binaries, everything. This is not
the best way to do it, but for our first docker image will be enough)

We will expose the 8080 port because our service, will use the port 8080 and we will want to use
it when we run the image

An we have other script which will be executed when the docker container will start. This script do
the following:

```sh

#!/bin/bash

  
# Start the second process
./gradlew bootRun
  
# Wait for any process to exit
wait -n
  
# Exit with status of process that exited first
exit $?
```

As we would execute in local, we execute with bootRun the spring application. Starting the service
Remember that you will need to have the docker daemon running. In windows, it is to have the docker desktop
running.

To do it, better, you will need to see: https://spring.io/guides/topicals/spring-boot-docker/

And now the idea is:
1) Create the image : "docker build ." and you will receive the hash of the image

if we execute "docker images" we will see a new image with "repostitory" = <none> and "TAG" = <none> and
with a image id.

You could run this image by using: "docker run -d -p 8080:8080 <image id>"

Another useful commands
```sh
# delete an image
docker image rm <has>
docker run image sh --> entras en la imagen
docker exec -it <id> sh --> entrar en la imagen con sh
docker run -d image <-- without blocking the terminal
docker ps --> containers
docker run -p 8080:8080 -it 43a68be47c92 sh <-- entrar con sh y mapeo de puerto
docker system prune --> remove all the containes and images which are nos being used
docker stop <id> --> for containers
docker start <id> --> for containers
```
2) We could build it with a tag (we can do it instead of 1. )

docker build -t shoppinglist_backend_kotlin .

3) We could run the docker image and test it is working, by accessing to the url:
    https://localhost:8080
   docker run -p 8080:8080 -d shoppinglist_backend_kotlin
You will see the login of Spring Boot

4) The next step is to include it in github actions and deploy in the container. In our case, 
   we will do it in Azure, in a container register.

``` 
...
- name: Docker Login
      uses: docker/login-action@v2.1.0
      with:
          registry: ${{secrets.ACR_ENDPOINT}}
          username: ${{ secrets.ACR_USERNAME}}
          password: ${{ secrets.ACR_PASSWORD}}
    - name: Build and push docker image
      uses: docker/build-push-action@v4.0.0
      with:
          context: .
          push: true
          tags: ${{ secrets.ACR_ENDPOINT }}
..

```

You will need to create a continaer register in azure or reuse one of them

You will need to create theses secrets:
- ACR_ENDPOINT => shoppinglistregister.azurecr.io/shoppinglistbackend:latest
- ACR_USERNAME => You can find it in the register, in access keys
- ACR_PASSWORD => You can find it in the register, in access keys

5) Pull the image in your local and execute it.

You will need to log in azure for this:

az login
az acr login --name shoppinglistregister.azurecr.io
docker pull shoppinglistregister.azurecr.io/shoppinglistbackend:latest
docker run -d -p 8080:8080 shoppinglistregister.azurecr.io/shoppinglistbackend:latest

test it is running in https://localhost:8080

:-) and that's all

# Deployment in webApp Azure

1) You need to create the webservice in azure
2) You need to create the credentials to access to azure:
   https://learn.microsoft.com/en-us/azure/developer/github/connect-from-azure?tabs=azure-portal%2Clinux
   If you have one, you could use it.
   If you need get it, you can use:
```
az ad sp create-for-rbac --name "cicd2" --role contributor --scopes /subscriptions/5f983a34-8f24-40dc-978e-35d5a138adf9/resourceGroups/appshoppinglistapi_group  --sdk-auth
```
You need to store the json, it will be the AZ_CREDENTIALS. 

3) In github actions create a job with the deployment
``` yaml
  deploy:
    runs-on: ubuntu-latest
    needs:  test-and-build    
    permissions:
      id-token: write
      contents: read
    steps:      
      - name: 'Login via Azure CLI'
        uses: azure/login@v1
        with:
          creds: ${{ secrets.AZ_CREDENTIALS }}              
      - uses: azure/webapps-deploy@v2
        with:
          app-name: ${{ secrets.AZURE_APP_SERVICE_NAME }} 
          images: ${{ secrets.ACR_ENDPOINT }}
      - name: Azure logout
        run:
          az logout
```
And you need to create this secrets in actions secrets (github):
 * AZ_CREDENTIALS = json we have created
 * AZURE_APP_SERVICE_NAME = appshoppinglistapi
 * ACR_ENDPOINT = shoppinglistregister.azurecr.io/shoppinglistbackend:latest

With all of this, if you open https://appshoppinglistapi.azurewebsites.net/login,
you will see the login and password request.
# Environment 
You need to include this, in the application.properties
```
spring.config.import=optional:file:.env[.properties]
```
https://www.baeldung.com/kotlin/read-env-variables
``` java
val env = System.getenv("HOME")
assertNotNull(env)
```
NOTE: I haven't gotten to use it with a .env file. It is not working.
https://www.baeldung.com/spring-boot-properties-env-variables
https://stackoverflow.com/questions/73053852/spring-boot-env-variables-in-application-properties

Although you could try to use it:
https://github.com/paulschwarz/spring-dotenv
``` gradle
implementation 'me.paulschwarz:spring-dotenv:2.3.0'
configurations.testImplementation.exclude(group: 'me.paulschwarz', module: 'spring-dotenv')
```

With this, the best approach are the following cases:
1) Be sure your app is going to scan the packages. For example, if we use
   other folder for the class which will contain the configuration we should:
``` gradle
@SpringBootApplication(scanBasePackages = ["com.shoppinglist.application", "com.shoppinglist.configuration"])
class ShoppingListBackendKotApplication

fun main(args: Array<String>) {
    runApplication<ShoppingListBackendKotApplication>(*args)
}
```

2) You will need to create a configuration class
```gradle
@Configuration
@ConfigurationPropertiesScan
class AppConfiguration(
    @Value("\${environment.exampleVariable}") private val exampleVariable: String?,
) {
    fun getExampleVariable():String? = exampleVariable
}
```

3) You should get the variables in your application.properties
```
...
environment.exampleVariable=${EXAMPLE_VARIABLE}
..
```
4) where you .env file contains
```
EXAMPLE_VARIABLE=Example
```
In addition, you will need to take into account, if you have the configuration in a folder
out of the main folder, you will need to scan these packages for the autowire.

Other solution is to have this structure of packages:
```
com.shoppinglist
    +application
        +dummy
            ....
        + configuration
            AppConfiguration
        ShoppingListBackendKotApplication.kt
```
But if you have other folder out of the application, you will need:

``
@SpringBootApplication(scanBasePackages = ["com.shoppinglist.application", "com.shoppinglist.configuration"])
class ShoppingListBackendKotApplication
``

And at the end, to access to this properties

```
 @Autowired
  private val appConfiguration: AppConfiguration? = null
```

# Wiremock
## Without the support of junit or spring configuration
https://www.baeldung.com/introduction-to-wiremock
https://github.com/wiremock/wiremock

Basicly, to start with wiremock you need to include this dependency:
```
testImplementation 'com.github.tomakehurst:wiremock:2.27.2'
```

```
WireMockServer wireMockServer = new WireMockServer(String host, int port);

```

Seems there is some no compatibility with spring boot:
https://groups.google.com/g/wiremock-user/c/knTaDyBY70Q

You need to use the version Beta to avoid these problems:
```
 testImplementation 'com.github.tomakehurst:wiremock:3.0.0-beta-8'
```


https://howtodoinjava.com/spring-boot2/resttemplate/resttemplate-get-example/

You will need to create a simple test class and include:
```
wireMockServer = WireMockServer(<port>) -> to create the instance of mockserver
wireMockServer.start()
wireMockServer.stop()
wireMockServer!!.resetRequests()
wireMockServer!!.verify(1, getRequestedFor(urlEqualTo("/dummyIntegration")))
```

You have an example in Test->Integration->ExampleWiremockWithoutSupportOfFrameworks

## with kotest extension
https://wiremock.org/docs/solutions/kotlin/
https://github.com/kotest/kotest-extensions-wiremock

## with spring boot
https://wiremock.org/docs/solutions/spring-boot/
https://medium.com/cuddle-ai/testing-spring-boot-application-using-wiremock-and-junit-5-d514a47ab931

# Swagger
https://www.tutorialspoint.com/spring_boot/spring_boot_enabling_swagger2.htm
https://www.baeldung.com/kotlin/swagger-spring-rest-api
https://levelup.gitconnected.com/adding-swagger-to-kotlin-spring-48b930e03ed5

You will need to include:
```
implementation('org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.3')
```

And you will access with:  http://localhost:8080/swagger-ui.html

Also you can configurate it with a special configuration.

You could include in the application.properties:
```yaml
springdoc.swagger-ui.path=/swagger-ui.html
```
# Logs
https://www.baeldung.com/kotlin/kotlin-logging-library

We need to include:
```
implementation 'io.github.microutils:kotlin-logging-jvm:3.0.5'
```
create a file logback-spring.xml in main->kotlin->resources with the following code:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %replace(%msg){'Bearer [^ ]+', '*****'}%n</pattern>
        </encoder>
    </appender>

    <appender name="json" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.contrib.json.classic.JsonLayout">
            <jsonFormatter
                    class="ch.qos.logback.contrib.jackson.JacksonJsonFormatter">
                <!-- <prettyPrint>true</prettyPrint> -->
            </jsonFormatter>
            <timestampFormat>yyyy-MM-dd' 'HH:mm:ss.SSS</timestampFormat>
        </layout>
    </appender>

    <springProfile name="!production">
        <root name="logger" level="INFO">
            <appender-ref ref="CONSOLE" />
        </root>
    </springProfile>

    <springProfile name="production">
        <!-- just exemplary, change to what suits your needs in production usage -->
        <root name="logger" level="INFO">
            <appender-ref ref="CONSOLE" />
        </root>
    </springProfile>

    <logger name="com.shoppinglist" level="INFO"/>
</configuration>
```

You could create a helper:
```kotlin
fun makeLogger(func: () -> Unit): KLogger {
    func() // for test coverage
    return KotlinLogging.logger(func)
}

```

And call it in everywhere:
```kotlin
private val logger = makeLogger {}
    ...
class ShoppingListBackendKotApplication

fun main(args: Array<String>) {
    logger.info("STARTING APPLICATION....")
    runApplication<ShoppingListBackendKotApplication>(*args)
    logger.info("RUNNING APPLICATION....")
}

```
# graphql

# security

# migrations

# SQLite

# Postgree

# NoSQL

