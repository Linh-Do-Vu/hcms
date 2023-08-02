package com.example.ezhcm.controller.dep;

import com.example.ezhcm.repostiory.CrmContactRepository;
import com.example.ezhcm.service.dep_employee.IDepEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("employee")
@RequiredArgsConstructor
public class DepEmployeeController {
    private  final IDepEmployeeService employeeService ;
    private  final CrmContactRepository repository ;
    @GetMapping("/all")
    public ResponseEntity<?> getListEmployee () {
        return new ResponseEntity<>(employeeService.getEmployeeDTOList(), HttpStatus.OK) ;
    }

}
