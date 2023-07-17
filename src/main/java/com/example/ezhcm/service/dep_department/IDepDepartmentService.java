package com.example.ezhcm.service.dep_department;

import com.example.ezhcm.model.dep.DepDepartment;
import com.example.ezhcm.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IDepDepartmentService extends IService<DepDepartment, Long> {
    @Override
    List<DepDepartment> findAll();

    @Override
    DepDepartment save(DepDepartment depDepartment);

    @Override
    void delete(Long aLong);

    @Override
    Optional<DepDepartment> findById(Long aLong);


    Map<Long,List<DepDepartment>>  buildHierarchy(List<DepDepartment> departments) ;
    List<Long> getChildDepartments(Long parentDepartmentId,Map<Long, List<DepDepartment>>  departmentHierarchy) ;
    Long getDepartmentId () ;
}
