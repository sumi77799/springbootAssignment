package com.employee.assignment.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.employee.assignment.DaoLayer.EmployeeDao;
import com.employee.assignment.Model.Employee;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    EmailService emailService;

    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        try {
            employeeDao.save(employee);
            String manager = employee.getReportsTo();
            String managerEmail = findEmailIdByEmployeeName(manager);
            sendEmailToLevel1Manager(managerEmail, employee);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Not Unique Employee", HttpStatus.ALREADY_REPORTED);
    }

    private void sendEmailToLevel1Manager(String managerEmail, Employee employee) {
        String message = buildEmailContent(employee);
        String subject = "New Employee is added";
        emailService.sendSimpleEmail(managerEmail, subject, message);
    }

    private String buildEmailContent(Employee employee) {
        return String.format("%s will now work under you. Mobile number is %s and email is %s",
                employee.getEmployeeName(), employee.getPhoneNumber(), employee.getEmail());
    }

    public String findEmailIdByEmployeeName(String employeeName) {
        Employee employee = employeeDao.findByEmployeeName(employeeName);
        if (employee != null) {
            return employee.getEmail();
        } else {
            return null;
        }
    }

    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeDao.findAll();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching employees", e);
        }
    }

    public ResponseEntity<String> deleteEmployeeById(String id) {
        try {
            employeeDao.deleteById(id);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error deleting employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateEmployeeById(String id, Employee updatedEmployee) {
        try {
            if (employeeDao.existsById(id)) {
                updatedEmployee.setId(id);
                employeeDao.save(updatedEmployee);
                return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error updating employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getNthLevelManager(String employeeId, int level) {
        try {
            Employee employee = employeeDao.findById(employeeId).orElse(null);
            if (employee != null) {
                String managerId = employee.getReportsTo();
                for (int i = 1; i < level; i++) {
                    if (managerId == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    employee = employeeDao.getEmployeeByEmployeeName(managerId);
                    System.out.println(employee);
                    if (employee == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    managerId = employee.getReportsTo();
                }
                return new ResponseEntity<>(managerId, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Page<Employee>> getAllEmployees(int page, int size, String sortBy) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<Employee> employeesPage = employeeDao.findAll(pageable);
            return new ResponseEntity<>(employeesPage, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
