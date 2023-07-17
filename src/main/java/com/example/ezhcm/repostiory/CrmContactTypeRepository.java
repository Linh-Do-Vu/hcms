package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.person.CrmContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmContactTypeRepository extends JpaRepository<CrmContactType,Long> {
}
