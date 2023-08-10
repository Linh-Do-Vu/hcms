package com.example.ezhcm.controller.security;

import com.example.ezhcm.dto.login.UserLogin;
import com.example.ezhcm.model.CoreUserAccount;
import com.example.ezhcm.service.account.IAccountDTOService;
import com.example.ezhcm.service.auth.AuthService;
import com.example.ezhcm.service.auth.IAuthService;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import com.example.ezhcm.service.person_doc_contact.PersonDocumentAndContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("users")
@RequiredArgsConstructor
public class AuthController {

        private final ICoreUserAccountService userAccountService;
    private final IAuthService authService;
    private final IAccountDTOService accountDTOService;
    private final PersonDocumentAndContactService personDocumentAndContactService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) {
return new ResponseEntity<>(authService.login(userLogin),HttpStatus.OK) ;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("token") String refreshToken) {
        return new ResponseEntity<>(authService.refreshToken(refreshToken),HttpStatus.OK) ;
    }

    @GetMapping("check-username-exist")
    public ResponseEntity<?> checkUsername(@RequestParam(value = "username") String username) {
        CoreUserAccount coreUserAccount = userAccountService.findCoreUserAccountByUsername(username);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @PutMapping("/change-password")
    public ResponseEntity<?>changePasswordUser(@RequestParam(value = "oldPassword") String oldPassword,
                                               @RequestParam(value = "newPassword" ) String newPassword) {
        return new ResponseEntity<>(accountDTOService.changePassword(oldPassword,newPassword),HttpStatus.OK) ;
    }
    @GetMapping("/{userId}")
    ResponseEntity<?> getUserAndEmployeeDetail(@PathVariable(value = "userId") Long idUser) {
        return new ResponseEntity<>(personDocumentAndContactService.getEmployeeAndUserDTO(idUser), HttpStatus.OK);
    }
}


