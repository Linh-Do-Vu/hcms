package com.example.ezhcm.secutity;

import com.example.ezhcm.dto.login.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private User user;
    private String refreshToken;


    public JwtResponse(String accessToken, User user, String refreshToken) {
        this.token = accessToken;
        this.user = user;
        this.refreshToken = refreshToken;

    }
}
