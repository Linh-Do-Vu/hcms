package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.dep.DepEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepEmployeeRepository extends JpaRepository<DepEmployee,Long> {
}
