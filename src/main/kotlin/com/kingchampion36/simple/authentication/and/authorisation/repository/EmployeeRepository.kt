package com.kingchampion36.simple.authentication.and.authorisation.repository

import com.kingchampion36.simple.authentication.and.authorisation.model.Employee

interface EmployeeRepository {
  fun save(employee: Employee)
  fun delete(employeeId: Int)
  fun get(employeeId: Int): Employee?
  fun getAll(): List<Employee>
}
