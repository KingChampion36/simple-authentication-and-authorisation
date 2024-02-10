package com.kingchampion36.simple.authentication.and.authorisation.service

import com.kingchampion36.simple.authentication.and.authorisation.exceptions.ResourceConflictException
import com.kingchampion36.simple.authentication.and.authorisation.exceptions.ResourceNotFoundException
import com.kingchampion36.simple.authentication.and.authorisation.model.Employee
import com.kingchampion36.simple.authentication.and.authorisation.repository.EmployeeRepository
import org.springframework.stereotype.Service

@Service
class LocalEmployeeDataService(private val employeeRepository: EmployeeRepository) : EmployeeDataService {
  override fun save(employee: Employee) {
    if (employeeRepository.get(employee.id) != null) {
      throw ResourceConflictException("Employee with id ${employee.id} already exists")
    }
    employeeRepository.save(employee)
  }

  override fun update(employee: Employee) {
    if (employeeRepository.get(employee.id) == null) {
      throw ResourceNotFoundException("Employee with id ${employee.id} doesn't exist")
    }
    employeeRepository.save(employee)
  }

  override fun get(employeeId: Int): Employee {
    if (employeeRepository.get(employeeId) == null) {
      throw ResourceNotFoundException("Employee with id $employeeId doesn't exist")
    }
    return employeeRepository.get(employeeId)!!
  }

  override fun delete(employeeId: Int) {
    if (employeeRepository.get(employeeId) == null) {
      throw ResourceNotFoundException("Employee with id $employeeId doesn't exist")
    }
    employeeRepository.delete(employeeId)
  }

  override fun getAll() = employeeRepository.getAll()
}
