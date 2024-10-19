package com.rajasree.service;

import com.rajasree.entities.Customer;
import com.rajasree.entities.Employee;
import com.rajasree.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private EmployeeService employeeService;



    public Customer registerCustomer(Customer customer, MultipartFile image) throws IOException {
        if (customerRepo.existsByAadharNumber(customer.getAadharNumber())) {
            throw new IllegalArgumentException("Aadhar number already exists");
        }
        if (customerRepo.existsByMobileNumber(customer.getMobileNumber())) {
            throw new IllegalArgumentException("Mobile number already exists");
        }
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (customer.getEmployee() != null && customer.getEmployee().getEmpId() > 0) {
            Employee employee = employeeService.findEmployeeById(customer.getEmployee().getEmpId());
            if (employee != null) {
                customer.setEmployee(employee); // Set the employee in the customer
            } else {
                throw new IllegalArgumentException("Employee not found");
            }
        }

        if (image != null && !image.isEmpty()) {
            customer.setImageData(image.getBytes());
        }

        return customerRepo.save(customer);
    }



}
