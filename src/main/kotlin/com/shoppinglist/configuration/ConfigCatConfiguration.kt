package com.shoppinglist.configuration

import com.configcat.ConfigCatClient
import com.configcat.autoPoll
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.time.Duration.Companion.seconds

@Configuration
@ConfigurationPropertiesScan
class ConfigCatConfiguration {
    @Value("\${configcat.sdkKey}")
    private lateinit var configKatSdkKey: String

    @Bean
    fun configCatClient() = ConfigCatClient(configKatSdkKey) {
        pollingMode = autoPoll {
            pollingInterval = 60.seconds
        }
    }
}
