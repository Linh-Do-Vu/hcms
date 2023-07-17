package com.example.ezhcm.secutity;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.CoreUserAccount;
import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private ICoreUserAccountService iCoreUserAccountService;


    /**
     * Đây là nơi request được nhận là token sẽ được lấy ra
     * Từ đó kiểm tra tính  hợp lệ của tokent
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // lấy token
            String jwt = getJwtFromRequest(request);
            if (jwt != null && jwtService.validateJwtToken(jwt)) {
                // lây username từ token
                String username = jwtService.getUserNameFromJwtToken(jwt);
                // lấy ra 1 đối tượng UserDetails từ username thông qua service
                UserDetails userDetails = iCoreUserAccountService.loadUserByUsername(username);
                CoreUserAccount account = iCoreUserAccountService.findCoreUserAccountByUsername(username);
                if (iCoreUserAccountService.checkAccountExp(account)) {

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else throw new CustomException(ErrorCode.UNAUTHORIZED,"Tài khoản và mật khẩu bị hết hạn hoặc tài khoản đã bị khóa");
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}
