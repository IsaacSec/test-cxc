package com.isaac.testcxc.repositories;

import com.isaac.testcxc.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
