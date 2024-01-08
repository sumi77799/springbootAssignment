package com.employee.assignment.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee.assignment.Model.Employee;
import com.employee.assignment.Service.EmployeeService;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Entry Level
    @PostMapping("add")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("getall")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        return employeeService.deleteEmployeeById(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployeeById(id, updatedEmployee);
    }

    // Intermediate
    @GetMapping("manager/{employeeId}/{level}")
    public ResponseEntity<String> getNthLevelManager(@PathVariable String employeeId, @PathVariable int level) {
        return employeeService.getNthLevelManager(employeeId, level);
    }

    @GetMapping("allEmployees")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "employeeName") String sortBy) {
        return employeeService.getAllEmployees(page, size, sortBy);
    }

    // Advanced Level

}
