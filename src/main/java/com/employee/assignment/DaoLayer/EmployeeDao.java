package com.employee.assignment.DaoLayer;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.employee.assignment.Model.Employee;

public interface EmployeeDao extends MongoRepository<Employee, String> {

    Employee getEmployeeByEmployeeName(String manager);

    Employee findByEmployeeName(String employeeName);
}
