package com.example.ezhcm.controller.department;

import com.example.ezhcm.service.dep_department.IDepDepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "departments")
public class DepartmentController {
    private final IDepDepartmentService departmentService ;

    public DepartmentController(IDepDepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping("/all")
    public ResponseEntity <?> getListDepartment(){
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK );
    }
}
