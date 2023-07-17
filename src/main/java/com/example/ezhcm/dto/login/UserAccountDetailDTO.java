package com.example.ezhcm.dto.login;

import com.example.ezhcm.model.CoreUserAccount;
import com.example.ezhcm.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountDetailDTO {
    private CoreUserAccount coreUserAccount ;
    private Role role ;
}
