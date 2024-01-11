package com.example.ezhcm.controller.department;

import com.example.ezhcm.exception.CustomException;
import com.example.ezhcm.exception.ErrorCode;
import com.example.ezhcm.model.dep.DepDepartment;
import com.example.ezhcm.service.dep_department.IDepDepartmentService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "departments")
@Configuration
public class DepartmentController {
    private final IDepDepartmentService departmentService;

    public DepartmentController(IDepDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getListDepartment(@RequestParam(value = "active", required = false) Boolean active) {
        if (active == null) {
            return new ResponseEntity<>(departmentService.findAllIsActive(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createDepartment(@RequestBody DepDepartment depDepartment) {
        return new ResponseEntity<>(departmentService.createDepartment(depDepartment), HttpStatus.OK);
    }

    @PutMapping("/{departmentId}")
    @PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
    public ResponseEntity<?> editDepartment(@RequestBody DepDepartment depDepartment, @PathVariable(value = "departmentId") Long departmentId) {
        depDepartment.setDepartmentId(departmentId);
        return new ResponseEntity<>(departmentService.save(depDepartment), HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    @PreAuthorize("hasAnyAuthority('1') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getDepartmentByDepartmentId(@PathVariable(value = "departmentId") Long departmentId) {
        return new ResponseEntity<>(departmentService.findById(departmentId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "Không tìm thấy phòng ban")), HttpStatus.OK);
    }
}