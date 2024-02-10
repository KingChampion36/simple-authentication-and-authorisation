package com.kingchampion36.simple.authentication.and.authorisation.repository

import com.kingchampion36.simple.authentication.and.authorisation.model.Employee
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class LocalEmployeeRepository : EmployeeRepository {

  private val cache = ConcurrentHashMap<Int, Employee>()

  @PreAuthorize("hasPermission(#employee, 'MUTATE')")
  override fun save(employee: Employee) {
    cache[employee.id] = employee
  }

  @PreAuthorize("hasPermission(#employeeId, 'MUTATE')")
  override fun delete(employeeId: Int) {
    cache.remove(employeeId)
  }

  @PreAuthorize("hasPermission(#employeeId, 'READ')")
  override fun get(employeeId: Int) = cache[employeeId]

  override fun getAll() = cache.values.toList()
}
