package com.example.ezhcm.secutity;


import com.example.ezhcm.service.core_user_account.ICoreUserAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ICoreUserAccountService iCoreUserAccountService;

    public SecurityConfig(ICoreUserAccountService iCoreUserAccountService) {
        this.iCoreUserAccountService = iCoreUserAccountService;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    /**
     * Mã hóa mật khẩu, kiểu mã hóa là BCrypt với strength là 10;
     * @return : một chuỗi string mã hóa từ mật khẩu
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iCoreUserAccountService).passwordEncoder(passwordEncoder());
    }

    /**
     * Đây là nơi config của service bao gồm:
     * .antMatchers( "/api/login").permitAll() : đây là URL nơi tất các request có thẻ truy cập mà không bắt buộc thông qua service
     * .antMatchers( "/customers**","/api/hello").hasAnyRole("USER") : còn những URL sẽ chỉ có thể truy cập thông qua role tương ứng
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**");
        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());
        http.authorizeRequests()
                .antMatchers( "/users/login").permitAll()
//                .antMatchers( "/admin/**").hasAnyRole("ROLE_1","ROLE_ADMIN")
                .antMatchers( "/users/**/admin").hasAnyAuthority("1","ADMIN")
                .anyRequest().authenticated()
                .and().csrf().disable();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.cors().disable();
        http.cors().and();
    }
}
