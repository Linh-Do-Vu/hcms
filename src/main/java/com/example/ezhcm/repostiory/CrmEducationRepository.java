package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.person.CrmEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmEducationRepository extends JpaRepository<CrmEducation,Long> {
    List<CrmEducation> findAllByPersonID (Long personId) ;
}
