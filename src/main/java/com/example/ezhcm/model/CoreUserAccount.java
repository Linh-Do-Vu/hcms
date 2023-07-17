package com.example.ezhcm.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.intellij.lang.annotations.Pattern;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "core_useraccount")
public class CoreUserAccount {
    @Id
    @Column(name = "useraccountid")
    private Long userAccountId;

    @Column(name = "employeeid",nullable = false)

    private Long employeeId;

    @Column(name = "username",nullable = false)
    @Pattern(value = "^[a-zA-Z0-9_-]{3,25}$")
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "status",nullable = false)
    private boolean status;

    @Column(name = "creationdate",nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "lastlogin",nullable = false)
    private LocalDateTime lastLogin;

    @Column(name = "settingid")
    private Long coreSetting;

    @Column(name = "pwdexpdate")
    private LocalDateTime pwdExpDate;

    @Column(name = "expirydate")
    private LocalDateTime expiryDate;

    @Column(name = "roleid",nullable = false)
    private Long role ;

   @Column(name = "failedlogin",nullable = false)
    private Long failedLogin  ;

    @com.fasterxml.jackson.annotation.JsonIgnore
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()) ) ;
        return authorities;
    }
}
