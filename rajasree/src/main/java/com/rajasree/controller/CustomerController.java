package com.rajasree.controller;


import com.rajasree.dto.ApiResponse;
import com.rajasree.entities.Customer;
import com.rajasree.repo.CustomerRepo;
import com.rajasree.service.CustomerService;
import com.rajasree.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private CustomerRepo customerRepo;

    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, LocalDateTime> otpTimestamp = new HashMap<>();


    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(
            @RequestPart("customer") Customer customer,
            @RequestPart("image") MultipartFile image) {
        try {
            customerService.registerCustomer(customer, image);
            return ResponseEntity.ok("Customer registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during registration");
        }
    }



    @PostMapping("/login")
    public String login(@RequestParam String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber);
        if (customer == null) {
            return "Customer not found";
        }

        //String otp = otpService.generateOTP();
        //otpService.sendOTP(mobileNumber, otp);
//
        //// Store OTP and timestamp temporarily in memory
        //otpStorage.put(mobileNumber, otp);
        //otpTimestamp.put(mobileNumber, LocalDateTime.now());
        //System.out.println(otp);

        String otp = otpService.generateOTP();
        otpService.logOTP(mobileNumber, otp); // Log OTP instead of sending it

        // Store OTP and timestamp temporarily in memory
        otpStorage.put(mobileNumber, otp);
        otpTimestamp.put(mobileNumber, LocalDateTime.now());
        System.out.println(otp);

        return "OTP sent to " + mobileNumber;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String mobileNumber, @RequestParam String otp) {
        String storedOtp = otpStorage.get(mobileNumber);
        LocalDateTime sentTime = otpTimestamp.get(mobileNumber);

        if (storedOtp == null) {
            return ResponseEntity.status(400).body("OTP not sent or expired!");
        }

        // Verify OTP and check expiry
        if (otp.equals(storedOtp)) {
            if (sentTime.plusMinutes(5).isAfter(LocalDateTime.now())) {
                otpStorage.remove(mobileNumber);
                otpTimestamp.remove(mobileNumber);

                // Fetch the customer object and return it
                Customer customer = customerRepo.findByMobileNumber(mobileNumber);
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.status(400).body("OTP has expired!");
            }
        } else {
            return ResponseEntity.status(400).body("Invalid OTP!");
        }
    }

}
