package com.rajasree.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_employee_reference")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerEmployeeReference {

    @Id
    @Column(name = "employee_reference_id")
    private int employeeReferenceId;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private Customer customer;

    @OneToOne
    @MapsId // This ensures employeeReferenceId is both the PK and FK to Employee
    @JoinColumn(name = "employee_reference_id", referencedColumnName = "empId")
    private Employee employee;
}
