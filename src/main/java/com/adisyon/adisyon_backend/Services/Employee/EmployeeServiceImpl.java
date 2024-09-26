package com.adisyon.adisyon_backend.Services.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Entities.Employee;
import com.adisyon.adisyon_backend.Exception.NotFoundException;
import com.adisyon.adisyon_backend.Repositories.Employee.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        // return employeeRepository.findById(id).orElseThrow();
        Employee employee = unwrapEmployee(employeeRepository.findById(id), id);
        return employee;
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        // Employee employee = employeeRepository.findById(id)
        // .orElseThrow(/* () -> new ResourceNotFoundException("Employee not found with
        // id " + id) */);
        Employee employee = unwrapEmployee(employeeRepository.findById(id), id);
        employee.setUser(employeeDetails.getUser());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        // Employee employee = employeeRepository.findById(id)
        // .orElseThrow(/* () -> new ResourceNotFoundException("Employee not found with
        // id " + id) */);
        Employee employee = unwrapEmployee(employeeRepository.findById(id), id);
        employeeRepository.delete(employee);
    }

    static Employee unwrapEmployee(Optional<Employee> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new NotFoundException(id.toString());
    }
}