# Simple Authentication and Authorisation

## [LDAP Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/ldap.html)

LDAP (Lightweight Directory Access Protocol) is often used by organizations as a central repository for user information and as an authentication service. It can also be used to store the role information for application users.

### Setting up an Embedded LDAP Server

The first thing you need to do is to ensure that you have an LDAP Server to which to point your configuration. For simplicity, it is often best to start with an embedded LDAP Server. Spring Security supports using either:

- Embedded UnboundID Server
- Embedded ApacheDS Server

In this project, we expose [users.ldif](src/main/resources/users.ldif) as a classpath resource to initialize the embedded LDAP server with two users, user and admin, both of which have a password of password.

### Embedded UnboundID Server

If you wish to use UnboundID, specify the following dependencies:

```
<dependency>
  <groupId>com.unboundid</groupId>
  <artifactId>unboundid-ldapsdk</artifactId>
  <version>6.0.11</version>
</dependency>
```

You can then configure the Embedded LDAP Server using an EmbeddedLdapServerContextSourceFactoryBean. This will instruct Spring Security to start an in-memory LDAP server:

```kotlin
@Bean
fun contextSourceFactoryBean(): EmbeddedLdapServerContextSourceFactoryBean {
  val contextSourceFactoryBean = EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer()
  contextSourceFactoryBean.setPort(0)
  return contextSourceFactoryBean
}
```

### Authentication

Spring Security supplies two LdapAuthenticator implementations:

- Bind Authentication
- Password Authentication

### Using Bind Authentication

Bind Authentication is the most common mechanism for authenticating users with LDAP. In bind authentication, the user’s credentials (username and password) are submitted to the LDAP server, which authenticates them. The advantage to using bind authentication is that the user’s secrets (the password) do not need to be exposed to clients, which helps to protect them from leaking.

The following example shows bind authentication configuration:

```kotlin
@Bean
fun authenticationManager(contextSource: BaseLdapPathContextSource): AuthenticationManager {
    val factory = LdapBindAuthenticationManagerFactory(contextSource)
    factory.setUserSearchFilter("(uid={0})")
    factory.setUserSearchBase("ou=people")
    return factory.createAuthenticationManager()
}
```

## Implementing Authentication

See [SecurityConfig](src/main/kotlin/com/kingchampion36/simple/authentication/and/authorisation/authentication/SecurityConfig.kt)

## Implementing Authorisation

- See [SimplePermissionEvaluator](src/main/kotlin/com/kingchampion36/simple/authentication/and/authorisation/authorisation/SimplePermissionEvaluator.kt)
- Add the below bean:
   ```kotlin
     @Bean
     fun methodSecurityExpressionHandler(
       simplePermissionEvaluator: SimplePermissionEvaluator
     ): MethodSecurityExpressionHandler = DefaultMethodSecurityExpressionHandler().also { it.setPermissionEvaluator(simplePermissionEvaluator) }
   ```

# Build

```
mvn clean install
```

# Run

```
mvn spring-boot:run
```

## What does this application do?

This application has 5 APIs, and all APIs require LDAP authentication:
- Add an employee
- Update an employee
- Get an employee
- Get all employees
- Delete an employee

Only admin has permission to perform mutation on employee. All users have permission to get employee(s).
