package com.example.ezhcm.dto.login;

import com.example.ezhcm.model.CoreUserAccount;
import com.example.ezhcm.model.dep.DepEmployee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountAndEmployeeDTO {
    private DepEmployee employee ;
    private CoreUserAccount userAccount ;
}
