package com.example.ezhcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoreUserAccountDTO {
    private Long userAccountId;
    private String username;
    private boolean status;
    private LocalDateTime creationDate;
    private LocalDateTime lastLogin;
    private LocalDateTime pwdExpDate;
    private LocalDateTime expiryDate;
    private Long role ;
}
