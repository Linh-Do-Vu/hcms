package com.example.ezhcm.model.dep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dep_employee")
public class DepEmployee {
    @Id
    @Column(name = "employeeid")
    private Long employeeId;

    @Column(name = "refitemid",nullable = false)
    private Long refItemId;

    @Column(name = "departmentid",nullable = false)
    private Long departmentId;

    @Column(name = "status",nullable = false)
    private boolean status;

    @Column(name = "phonenum1",nullable = false)
    private String phoneNum1;

    @Column(name = "phonenum2")
    private String phoneNum2;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "firstname",nullable = false)
    private String firstName;

    @Column(name = "lastname",nullable = false)
    private String lastName;

}