package com.rajasree.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "customer_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @NotNull(message = "customer name is required")
    @Column(name = "customer_name")
    private String customerName;


    @Column(name = "guardian_name")
    private String guardianName;

    @Past(message = "Date of Birth should be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Min(value = 0, message = "Age must be a positive number")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    @Column(name = "age")
    private int age;

    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhar number must be 12 digits")
    @Column(name = "aadhar_number", unique = true)
    private String aadharNumber;

    @NotNull(message = "Mobile number is required")
    @Pattern(regexp = "^\\+91[789]\\d{9}$", message = "Mobile number must be a valid Indian number in the format +91XXXXXXXXXX, where X is a digit")
    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;


    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotNull(message = "City is required")
    @Column(name = "city")
    private String City;

    @Min(value = 100000, message = "Pincode must be at least 6 digits")
    @Max(value = 999999, message = "Pincode must be at most 6 digits")
    @Column(name = "pincode")
    private int pincode;

    @Column(name = "group_name")
    private String groupName;

    @Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "PAN number must be valid")
    @Column(name = "pan_number")
    private String panNumber;

    @NotNull(message = "Primary address is required")
    @Column(name = "primary_address")
    private String primaryAddress;

    @Column(name = "secondary_address")
    private String secondaryAddress;

    @NotNull(message = "Nominee name is required")
    @Column(name = "nominee_name")
    private String nomineeName;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "empId")
    private Employee employee;


}
