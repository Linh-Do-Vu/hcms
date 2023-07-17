package com.example.ezhcm.service.account;

import com.example.ezhcm.dto.login.AccountAndEmployeeDTO;
import com.example.ezhcm.model.CoreUserAccount;

import java.util.List;

public interface IAccountDTOService {
AccountAndEmployeeDTO createUserAndEmployee (AccountAndEmployeeDTO accountAndEmployeeDTO) ;
Boolean changePassword (String oldPassword,String newPassWord) ;
    List<CoreUserAccount> getListCoreUserAcc () ;
    void updateUserAndEmployee (AccountAndEmployeeDTO accountAndEmployeeDTO) ;

}
