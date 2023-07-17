package com.example.ezhcm.repostiory;

import com.example.ezhcm.model.person.CrmPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmPersonRepository extends JpaRepository<CrmPerson,Long> {
}
