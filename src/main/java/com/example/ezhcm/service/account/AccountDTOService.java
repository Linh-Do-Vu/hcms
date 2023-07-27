package com.example.ezhcm.service.account;

import com.example.ezhcm.dto.login.AccountAndEmployeeDTO;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.CoreUserAccount;
import com.example.ezhcm.model.dep.DepEmployee;
import com.example.ezhcm.service.auto_pk_support.IAutoPkSupportService;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import com.example.ezhcm.service.dep_employee.IDepEmployeeService;
import com.example.ezhcm.service.ref_item.IRefRefItemService;
import com.example.ezhcm.service.role.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDTOService implements IAccountDTOService {
    private final IDepEmployeeService employeeService;
    private final ICoreUserAccountService coreUserAccountService;
    private final PasswordEncoder passwordEncoder;
    private final IRefRefItemService itemService;

    @Override
    @Transient
    public AccountAndEmployeeDTO createUserAndEmployee(AccountAndEmployeeDTO accountAndEmployeeDTO) {
        DepEmployee employee = accountAndEmployeeDTO.getEmployee();
        DepEmployee depEmployee = employeeService.createEmployee(employee);
        Long employeeID = depEmployee.getEmployeeId();

        CoreUserAccount userAccount = accountAndEmployeeDTO.getUserAccount();
        userAccount.setEmployeeId(employeeID);
        String password = userAccount.getPassword();
        userAccount.setPassword(passwordEncoder.encode(password));
        CoreUserAccount userAccountNew = coreUserAccountService.createCoreUserAccount(userAccount, employeeID);
        accountAndEmployeeDTO.setEmployee(depEmployee);
        accountAndEmployeeDTO.setUserAccount(userAccountNew);
        return accountAndEmployeeDTO;
    }

    @Override
    public Boolean changePassword(String oldPassword, String newPassword) {
        CoreUserAccount account = coreUserAccountService.getUserLogging();
        String pass = account.getPassword();
        boolean matches = passwordEncoder.matches(oldPassword, pass);
        if (matches) {
            account.setPassword(passwordEncoder.encode(newPassword));
            coreUserAccountService.save(account);
            return true;
        } else return false;
    }

    @Override
    public List<CoreUserAccount> getListCoreUserAcc() {
        List<CoreUserAccount> coreUserAccounts = coreUserAccountService.findAll();
        coreUserAccounts.stream().forEach(coreUserAccount -> coreUserAccount.setPassword(null));
        return coreUserAccounts;
    }

    @Override
    public void updateUserAndEmployee(AccountAndEmployeeDTO accountAndEmployeeDTO) {
        DepEmployee employee = accountAndEmployeeDTO.getEmployee();
        Long idEmployee = employee.getEmployeeId();
        employee.setStatus(true);
        employeeService.save(employee);

        CoreUserAccount userAccount = accountAndEmployeeDTO.getUserAccount();
        String password = userAccount.getPassword();
        CoreUserAccount userAccountOld = coreUserAccountService.findById(userAccount.getUserAccountId()).get();
        if (password != null) {
            userAccount.setPassword(passwordEncoder.encode(password));
        } else {
            String oldPass = userAccountOld.getPassword();
            userAccount.setPassword(oldPass);
        }
        userAccount.setEmployeeId(idEmployee);
        LocalDateTime lastLogin = userAccountOld.getLastLogin();
        userAccount.setLastLogin(lastLogin);
        LocalDateTime createDate = userAccountOld.getCreationDate();
        userAccount.setCreationDate(createDate);
        if (userAccount.isStatus() && userAccount.getFailedLogin() >=  Long.valueOf(itemService.findAllByReferenceId(Constants.COUNT_LOGIN_FALSE_REFERENCE_ITEM).get(0).getFullValue())) {
            userAccount.setFailedLogin(0L);
        }
        coreUserAccountService.save(userAccount);
    }
}
