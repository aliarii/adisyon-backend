package com.adisyon.adisyon_backend.Services.Employee;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adisyon.adisyon_backend.Dto.Request.Employee.CreateEmployeeDto;
import com.adisyon.adisyon_backend.Dto.Request.Employee.DeleteEmployeeDto;
import com.adisyon.adisyon_backend.Dto.Request.Employee.UpdateEmployeeDto;
import com.adisyon.adisyon_backend.Entities.Employee;
import com.adisyon.adisyon_backend.Entities.USER_ROLE;
import com.adisyon.adisyon_backend.Exception.BusinessException;
import com.adisyon.adisyon_backend.Exception.NotFoundException;
import com.adisyon.adisyon_backend.Repositories.Employee.EmployeeRepository;
import com.adisyon.adisyon_backend.Repositories.User.UserRepository;
import com.adisyon.adisyon_backend.Services.Company.CompanyService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        Employee employee = unwrapEmployee(employeeRepository.findById(id), id);
        return employee;
    }

    public Employee createEmployee(CreateEmployeeDto employeeDto) {
        checkUserEmail(employeeDto.getUserName() + "@adisyon.com");

        Employee newEmployee = new Employee();
        newEmployee.setUserName(employeeDto.getUserName());
        newEmployee.setFullName(employeeDto.getFullName());
        newEmployee.setEmail(employeeDto.getUserName() + "@adisyon.com");
        newEmployee.setPassword(employeeDto.getPassword());
        newEmployee.setRole(USER_ROLE.ROLE_EMPLOYEE);
        newEmployee.setIsActive(true);
        newEmployee.setCreatedDate(new Date());

        newEmployee.setCompany(companyService.getCompanyById(employeeDto.getCompanyId()));

        return employeeRepository.save(newEmployee);
    }

    public Employee updateEmployee(UpdateEmployeeDto employeeDto) {

        Employee existingEmployee = getEmployeeById(employeeDto.getId());
        existingEmployee.setIsActive(false);
        employeeRepository.save(existingEmployee);

        Employee newEmployee = new Employee();
        newEmployee.setCompany(existingEmployee.getCompany());
        newEmployee.setUserName(
                employeeDto.getUserName() != null ? employeeDto.getUserName() : existingEmployee.getUserName());
        newEmployee.setFullName(
                employeeDto.getFullName() != null ? employeeDto.getFullName() : existingEmployee.getFullName());
        newEmployee.setEmail(employeeDto.getUserName() != null ? employeeDto.getUserName() + "@adisyon.com"
                : existingEmployee.getEmail());
        newEmployee.setPassword(
                employeeDto.getPassword() != null ? employeeDto.getPassword() : existingEmployee.getPassword());
        newEmployee.setRole(USER_ROLE.ROLE_EMPLOYEE);
        newEmployee.setIsActive(true);
        newEmployee.setCreatedDate(existingEmployee.getCreatedDate());
        newEmployee.setUpdatedDate(new Date());

        return employeeRepository.save(newEmployee);
    }

    public void deleteEmployee(DeleteEmployeeDto employeeDto) {

        Employee employee = getEmployeeById(employeeDto.getId());
        checkIfEmployeeActive(employee);
        employee.setIsActive(false);
        employee.setUpdatedDate(new Date());
        employeeRepository.save(employee);
    }

    private void checkUserEmail(String userEmail) {
        boolean ifExists = userRepository.findUserByEmail(userEmail) != null ? true : false;
        if (ifExists) {
            throw new BusinessException("Email already exist!");
        }
    }

    private void checkIfEmployeeActive(Employee employee) {
        if (employee.getIsActive() == false) {
            throw new BusinessException("Already disabled!");
        }
    }

    static Employee unwrapEmployee(Optional<Employee> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new NotFoundException(id.toString());
    }
}