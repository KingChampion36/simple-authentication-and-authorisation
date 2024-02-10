package com.kingchampion36.simple.authentication.and.authorisation.service

import com.kingchampion36.simple.authentication.and.authorisation.model.Employee

interface EmployeeDataService {
  fun save(employee: Employee)
  fun update(employee: Employee)
  fun get(employeeId: Int): Employee
  fun delete(employeeId: Int)
  fun getAll(): List<Employee>
}
