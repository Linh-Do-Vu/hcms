package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.person.CrmEducationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmEducationTypeRepository extends JpaRepository<CrmEducationType,Long > {
}
