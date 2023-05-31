package com.shoppinglist.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

/*@Configuration
@ConfigurationProperties("environment")
@ConfigurationPropertiesScan
class AppConfiguration {

    var exampleVariable:String?
        @get:ConfigurationProperties("exampleVariable")
        get() {
            return exampleVariable
        }
        @ConfigurationProperties("exampleVariable")
        set(value: String?) {
            this.exampleVariable = value
        }
}*/
@Configuration
@ConfigurationPropertiesScan
class AppConfiguration(
    @Value("\${environment.exampleVariable}") private val exampleVariable: String?,
) {
    fun getExampleVariable(): String? = exampleVariable
}
