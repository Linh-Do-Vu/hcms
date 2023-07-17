package com.example.ezhcm.service.auth;

import com.example.ezhcm.dto.login.User;
import com.example.ezhcm.dto.login.UserLogin;
import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.Constants;
import com.example.ezhcm.model.CoreUserAccount;
import com.example.ezhcm.model.Log;
import com.example.ezhcm.secutity.JwtResponse;
import com.example.ezhcm.secutity.JwtService;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import com.example.ezhcm.service.ref_item.IRefRefItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ICoreUserAccountService userAccountService;
    private final IRefRefItemService itemService ;
    @Override
    public JwtResponse login(UserLogin userLogin) {
        CoreUserAccount coreUserAccount = userAccountService.findCoreUserAccountByUsername(userLogin.getUsername());
        Long settingCountFail = Long.valueOf(itemService.findAllByReferenceId(Constants.COUNT_LOGIN_FALSE_REFERENCE_ITEM).get(0).getFullValue()) ;
        if (coreUserAccount.getFailedLogin() <= settingCountFail) {
            if (userAccountService.checkAccountExp(coreUserAccount)
            ) {
                try {
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    Log.info("User" + userLogin.getUsername() + "login");

                    String refreshToken = jwtService.generateRefreshTokenLogin(authentication);
                    String jwt = jwtService.generateTokenLogin(authentication);
                    coreUserAccount.setLastLogin(LocalDateTime.now().withNano(0));
                    User user = userAccountService.getUserDTO(coreUserAccount);
                    coreUserAccount.setFailedLogin(0L);
                    userAccountService.save(coreUserAccount);
                    return new JwtResponse(jwt, user, refreshToken);
                } catch (AuthenticationException e) {
                    Long failLoginOld = coreUserAccount.getFailedLogin();
                    coreUserAccount.setFailedLogin(failLoginOld + 1L);
                    userAccountService.save(coreUserAccount);
                    Log.warn("Password " + userLogin.getPassword() + " is incorrect");
                    throw new CustomException(ErrorCode.UNAUTHORIZED, "mật khâủ sai, vui lòng nhập lại");
                }
            } else
                throw new CustomException(ErrorCode.UNAUTHORIZED, " Tài khoản, mật khẩu bị hết hạn hoặc tài khoản đã bị khóa,vui lòng liên hệ với admin");
        } else
            coreUserAccount.setStatus(false);
        userAccountService.save(coreUserAccount);
        throw new CustomException(ErrorCode.UNAUTHORIZED, "Tài khoản bị khóa do đăng nhập sai quá " +settingCountFail+ " lần");
    }

    @Override
    public JwtResponse refreshToken(String refreshToken) {
        try {
            boolean rest = jwtService.validateRefreshToken(refreshToken);
            if (rest) {
                Authentication authentication = userAccountService.getAuthentication();
                CoreUserAccount account = userAccountService.getUserLogging();
                User user = userAccountService.getUserDTO(account);
                String newAccessToken = jwtService.generateTokenLogin(authentication);
                return new JwtResponse(newAccessToken, user, null);
            } else {
                throw new CustomException(ErrorCode.UNAUTHORIZED, "Mã token refresh bị hết hạn");
            }
        } catch (Exception ex) {
            throw new CustomException(ErrorCode.UNAUTHORIZED, "Mã refresh token không đúng");
        }
    }
}
