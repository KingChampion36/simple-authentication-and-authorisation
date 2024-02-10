package com.kingchampion36.simple.authentication.and.authorisation.authorisation

import com.kingchampion36.simple.authentication.and.authorisation.enums.Operation
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class SimplePermissionEvaluator : PermissionEvaluator {
  override fun hasPermission(authentication: Authentication, targetDomainObject: Any, permission: Any): Boolean {
    val user = authentication.name
    if (permission is String) {
      return when (permission) {
        Operation.MUTATE.name -> {
          when (user) {
            "admin" -> true
            else -> false
          }
        }
        Operation.READ.name -> true
        else -> false
      }
    }
    return false
  }

  override fun hasPermission(authentication: Authentication, targetId: Serializable, targetType: String, permission: Any) = false
}
