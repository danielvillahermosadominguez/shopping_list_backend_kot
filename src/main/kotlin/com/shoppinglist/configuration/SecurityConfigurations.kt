package com.shoppinglist.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.Arrays

@EnableWebSecurity
@Configuration
class SecurityConfigurations {
    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): InMemoryUserDetailsManager? {
        val user: UserDetails = User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build()
        val admin: UserDetails = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("USER", "ADMIN")
            .build()
        return InMemoryUserDetailsManager(user, admin)
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity?): SecurityFilterChain? {
        http?.authorizeRequests()?.anyRequest()?.authenticated()?.and()?.httpBasic()
        return http?.build()

        http
            ?.csrf()
            ?.disable()
        return http?.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
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
