package com.example.ezhcm.service.dep_employee;

import com.example.ezhcm.dto.Employee;
import com.example.ezhcm.model.dep.DepEmployee;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Optional;

public interface IDepEmployeeService extends IService<DepEmployee,Long> {
    @Override
    Optional<DepEmployee> findById(Long aLong);

    @Override
    List<DepEmployee> findAll();

    @Override
    DepEmployee save(DepEmployee depEmployee);

    @Override
    void delete(Long aLong);
    DepEmployee createEmployee (DepEmployee employee) ;
    List<Employee> getEmployeeDTOList() ;
}
