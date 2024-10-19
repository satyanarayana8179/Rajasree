package com.rajasree.service;

import com.rajasree.entities.Employee;
import com.rajasree.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public Employee findEmployeeById(int empId) {
        Optional<Employee> employee = employeeRepo.findById(empId);
        return employee.orElse(null);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    public Optional<Employee> getEmployeeByReferenceId(int referenceId) {
        return employeeRepo.findById(referenceId);
    }

}
