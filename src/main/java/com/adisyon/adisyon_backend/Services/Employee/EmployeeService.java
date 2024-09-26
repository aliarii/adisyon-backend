package com.adisyon.adisyon_backend.Services.Employee;

import java.util.List;

import com.adisyon.adisyon_backend.Entities.Employee;

public interface EmployeeService {
    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(Long id);

    public Employee createEmployee(Employee employee);

    public Employee updateEmployee(Long id, Employee employeeDetails);

    public void deleteEmployee(Long id);
}
