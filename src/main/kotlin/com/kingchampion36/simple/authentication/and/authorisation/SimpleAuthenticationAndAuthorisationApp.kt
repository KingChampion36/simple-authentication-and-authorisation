package com.kingchampion36.simple.authentication.and.authorisation

import com.kingchampion36.simple.authentication.and.authorisation.authorisation.SimplePermissionEvaluator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.ldap.core.support.BaseLdapPathContextSource
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory

@SpringBootApplication
@EnableMethodSecurity
@EnableCaching
class SimpleAuthenticationAndAuthorisationApp {
  @Bean
  fun contextSourceFactoryBean(): EmbeddedLdapServerContextSourceFactoryBean {
    val contextSourceFactoryBean = fromEmbeddedLdapServer()
    contextSourceFactoryBean.setPort(0)
    return contextSourceFactoryBean
  }

  @Bean
  fun authenticationManager(contextSource: BaseLdapPathContextSource): AuthenticationManager {
    val factory = LdapBindAuthenticationManagerFactory(contextSource)
    factory.setUserSearchFilter("(uid={0})")
    factory.setUserSearchBase("ou=people")
    return factory.createAuthenticationManager()
  }

  @Bean
  fun methodSecurityExpressionHandler(
    simplePermissionEvaluator: SimplePermissionEvaluator
  ): MethodSecurityExpressionHandler = DefaultMethodSecurityExpressionHandler().also { it.setPermissionEvaluator(simplePermissionEvaluator) }

}

fun main(args: Array<String>) {
  runApplication<SimpleAuthenticationAndAuthorisationApp>(*args)
}
