package com.adisyon.adisyon_backend.Controllers.Employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adisyon.adisyon_backend.Dto.Request.Employee.CreateEmployeeDto;
import com.adisyon.adisyon_backend.Dto.Request.Employee.DeleteEmployeeDto;
import com.adisyon.adisyon_backend.Dto.Request.Employee.UpdateEmployeeDto;
import com.adisyon.adisyon_backend.Entities.Employee;
import com.adisyon.adisyon_backend.Services.Employee.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody CreateEmployeeDto employeeDto) {
        Employee createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody UpdateEmployeeDto employeeDto) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeDto);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@RequestBody DeleteEmployeeDto employeeDto) {
        employeeService.deleteEmployee(employeeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
