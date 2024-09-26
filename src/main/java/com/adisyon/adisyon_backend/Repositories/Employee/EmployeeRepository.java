package com.adisyon.adisyon_backend.Repositories.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adisyon.adisyon_backend.Entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
