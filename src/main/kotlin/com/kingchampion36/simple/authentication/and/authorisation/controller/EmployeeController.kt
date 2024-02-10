package com.kingchampion36.simple.authentication.and.authorisation.controller

import com.kingchampion36.simple.authentication.and.authorisation.model.Employee
import com.kingchampion36.simple.authentication.and.authorisation.service.EmployeeDataService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController(private val employeeDataService: EmployeeDataService) {

  @PostMapping("/v1/employee")
  fun postEmployee(@RequestBody employee: Employee): Employee {
    employeeDataService.save(employee)
    return employee
  }

  @PutMapping("/v1/employee")
  fun updateEmployee(@RequestBody employee: Employee): Employee {
    employeeDataService.update(employee)
    return employee
  }

  @GetMapping("/v1/employee/ids/{id}")
  fun getEmployee(@PathVariable id: Int): Employee {
    return employeeDataService.get(id)
  }

  @GetMapping("/v1/employees")
  fun getAllEmployees(): List<Employee> {
    return employeeDataService.getAll()
  }

  @DeleteMapping("/v1/employee/ids/{id}")
  fun deleteEmployee(@PathVariable id: Int): Employee {
    return employeeDataService.get(id)
  }
}
