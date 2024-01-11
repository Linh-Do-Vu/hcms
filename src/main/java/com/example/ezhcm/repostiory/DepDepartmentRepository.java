package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.dep.DepDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepDepartmentRepository extends JpaRepository<DepDepartment,Long> {
    @Query(nativeQuery = true,value = "select * from dep_department where dep_department.is_active = 1")
    List<DepDepartment> getDepDepartmentByActiveIsTrue ();

}
