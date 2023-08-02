package com.example.ezhcm.controller.admin;

import com.example.ezhcm.dto.login.AccountAndEmployeeDTO;
import com.example.ezhcm.service.account.IAccountDTOService;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
//@Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableMethodSecurity
public class AdminController {
    private final IAccountDTOService accountDTOService;
    private final ICoreUserAccountService accountService;

    public AdminController(IAccountDTOService accountDTOService, ICoreUserAccountService accountService) {
        this.accountDTOService = accountDTOService;
        this.accountService = accountService;
    }

    @PostMapping("/admin")
//    @PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
//    @PreAuthorize("hasRole('ROLE_1') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createAccount(@RequestBody AccountAndEmployeeDTO accountAndEmployeeDTO) {
        AccountAndEmployeeDTO accountAndEmployeeDTO1 = accountDTOService.createUserAndEmployee(accountAndEmployeeDTO);
        return new ResponseEntity<>(accountAndEmployeeDTO1, HttpStatus.OK);
    }

    @GetMapping("/all/admin")
    public ResponseEntity<?> getListUserAccount() {
        return new ResponseEntity<>(accountDTOService.getListCoreUserAcc(), HttpStatus.OK);
    }

    @PutMapping("/admin")
    public ResponseEntity<?> updateAccount(@RequestBody AccountAndEmployeeDTO accountAndEmployeeDTO) {
     accountDTOService.updateUserAndEmployee(accountAndEmployeeDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
