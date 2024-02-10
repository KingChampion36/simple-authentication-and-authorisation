package com.kingchampion36.simple.authentication.and.authorisation.authentication

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.util.UrlPathHelper

@Configuration
@EnableWebSecurity
class SecurityConfig(
  @Value("\${security.urlPatterns:}") private val urlPatterns: Array<String>,
  @Value("\${security.sessionPolicy:NEVER}") private val sessionPolicy: String,
  @Value("\${security.allowedOrigins:*}") private val origins: String,
  @Value("\${security.allowedMethods:*}") private val methods: String,
  @Value("\${security.allowedHeaders:*}") private val headers: String
) {
  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    val matchers = urlPatterns.map { AntPathRequestMatcher(it) }.toTypedArray()
    http.authorizeHttpRequests { it.requestMatchers(*matchers).authenticated().anyRequest().permitAll() }
      .csrf { it.disable() }
      .cors { it.configurationSource(corsConfigurationSource()) }
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.valueOf(sessionPolicy)) }
    http.httpBasic { it.realmName("Simple Auth and Auth application") }
    return http.build()
  }

  private fun corsConfigurationSource() = UrlBasedCorsConfigurationSource().apply {
    val config = CorsConfiguration().apply {
      allowedMethods = methods.trimmedList()
      allowedOrigins = origins.trimmedList()
      allowedHeaders = headers.trimmedList()
    }
    setUrlPathHelper(
      UrlPathHelper().apply {
        setAlwaysUseFullPath(true)
      }
    )
    setCorsConfigurations(urlPatterns.associateWith { config })
  }

  private fun String.trimmedList(): MutableList<String> = split(",").map { it.trim() }.toMutableList()
}
