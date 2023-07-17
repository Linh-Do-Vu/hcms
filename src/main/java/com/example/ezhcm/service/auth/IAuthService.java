package com.example.ezhcm.service.auth;

import com.example.ezhcm.dto.login.UserLogin;
import com.example.ezhcm.secutity.JwtResponse;

public interface IAuthService {
    JwtResponse login (UserLogin userLogin) ;
    JwtResponse refreshToken (String refreshToken) ;

}
