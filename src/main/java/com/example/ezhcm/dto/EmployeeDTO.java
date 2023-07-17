package com.example.ezhcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long employeeId;
    private boolean status;
    private String phoneNum1;
    private String phoneNum2;
    private String email;
    private String firstName;
    private String lastName;
}
