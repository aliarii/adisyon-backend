package com.adisyon.adisyon_backend.Services.Employee;

import java.util.List;

import com.adisyon.adisyon_backend.Dto.Request.Employee.CreateEmployeeDto;
import com.adisyon.adisyon_backend.Dto.Request.Employee.DeleteEmployeeDto;
import com.adisyon.adisyon_backend.Dto.Request.Employee.UpdateEmployeeDto;
import com.adisyon.adisyon_backend.Entities.Employee;

public interface EmployeeService {
    public List<Employee> getAllEmployees();

    public Employee getEmployeeById(Long id);

    public Employee createEmployee(CreateEmployeeDto employeeDto);

    public Employee updateEmployee(UpdateEmployeeDto employeeDto);

    public void deleteEmployee(DeleteEmployeeDto employeeDto);
}
