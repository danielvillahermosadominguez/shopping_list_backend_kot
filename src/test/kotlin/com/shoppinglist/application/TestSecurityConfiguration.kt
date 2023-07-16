package com.shoppinglist.application

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.Arrays

@EnableWebSecurity
@TestConfiguration
class TestSecurityConfiguration {
    @Bean
    @Primary
    @Throws(Exception::class)
    fun filterChainForTest(http: HttpSecurity?): SecurityFilterChain? {
        http?.csrf()?.disable()
        return http?.build()
    }

    @Bean
    @Primary
    fun corsConfigurationSourceForTest(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = Arrays.asList("*")
        configuration.allowedMethods =
            Arrays.asList("POST", "PUT", "GET", "OPTIONS", "DELETE", "PATCH") // or simply "*"
        configuration.allowedHeaders = Arrays.asList("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
