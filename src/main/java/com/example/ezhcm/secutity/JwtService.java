package com.example.ezhcm.secutity;

import com.example.ezhcm.model.UserPrinciple;
import com.example.ezhcm.service.role.IRoleService;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "123456789987654321123456789987654321123456789";
    private static final long EXPIRE_TIME = 86400000000L;
    private static final String SECRET_KEY_REFRESH_TOKEN ="1234512389987654321123456789987654321123456789";
private final IRoleService roleService ;

    public JwtService(IRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Tạo mã token
     * @paramauthentication
     * @return mã token
     */
    public String generateTokenLogin(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        String role = userPrincipal.getRoles().toString();
        String result1 = role.substring(1, role.length() - 1);
        Long result = Long.valueOf(result1);
//        String nameRole = roleService.findById(Long.valueOf(result)).get().getName() ;


        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .claim("roles", result)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Kiểm tra tính hợp lệ của token
     * @paramauthToken : token
     * @return
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token -> Message: "+ e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token -> Message: "+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token -> Message: "+ e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty -> Message: "+ e.getMessage());
        }
        return false;
    }
    public boolean validateRefreshToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY_REFRESH_TOKEN).parseClaimsJws(authToken).getBody();
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token -> Message: "+ e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token -> Message: "+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token -> Message: "+ e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty -> Message: "+ e.getMessage());
        }
        return false;
    }
    /**
     * Lấy username từ 1 token
     * @paramtoken : là 1 token
     * @return :
     */

    public String generateRefreshTokenLogin(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME * 5000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY_REFRESH_TOKEN)
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        String userName = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }
}
