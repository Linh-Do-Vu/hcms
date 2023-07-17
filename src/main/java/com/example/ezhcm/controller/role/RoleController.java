package com.example.ezhcm.controller.role;

import com.example.ezhcm.service.role.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "role")
public class RoleController {
    private final IRoleService role ;

    public RoleController(IRoleService role) {
        this.role = role;
    }
    @GetMapping("list-role")
    public ResponseEntity<?> getListRoles() {
        return new ResponseEntity<>(role.findAll(), HttpStatus.OK);
    }
}
