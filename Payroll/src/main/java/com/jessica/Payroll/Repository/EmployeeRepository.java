package com.jessica.Payroll.Repository;

import com.jessica.Payroll.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}